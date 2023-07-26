package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.entity.*;
import com.example.tss.exception.ApplicationPlacingFailedException;
import com.example.tss.repository.*;
import com.example.tss.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CircularServiceImpl implements CircularService {
    private final ApplicantProfileServiceImpl applicantProfileService;
    private final ResourceRepository resourceRepository;
    private final CircularRepository circularRepository;
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;
    private final RoundService roundService;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningRoundRepository screeningRoundRepository;
    private final BookMarkCircularService bookMarkCircularService;
    private final UserService userService;
    private final ApplicationRepository applicationRepository;

    public Page<?> getAllCircular(Pageable pageable) {
        return circularRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> createCircular(CircularDto circularDto) {
        Circular savedCircular = circularRepository.save(modelMapper.map(circularDto, Circular.class));
        ScreeningRound initialScreeningRound = ScreeningRound.builder()
                .title("Application Filtering")
                .circular(savedCircular)
                .serialNo(0)
                .description("Applicant need to submit application first")
                .build();
        ScreeningRound endScreeningRound = ScreeningRound.builder()
                .title("Selected Candidates")
                .circular(savedCircular)
                .serialNo(1)
                .description("Final Select Candidates List Will Appear Here")
                .build();
        screeningRoundRepository.save(endScreeningRound);
        ScreeningRound savedScreeningRound = screeningRoundRepository.save(initialScreeningRound);
        ScreeningRoundMeta screeningRoundMeta = ScreeningRoundMeta.builder()
                .circular(savedCircular)
                .currentRound(savedScreeningRound)
                .build();
        ScreeningRoundMeta savedScreeningRoundMeta = screeningRoundMetaRepository.save(screeningRoundMeta);
        return ResponseEntity.ok(circularRepository.save(savedCircular));
    }

    public ResponseEntity<?> getCircularById(Long id) {
        Optional<Circular> circularById = circularRepository.findById(id);
        return circularById
                .map(circular -> ResponseEntity.ok().body(circular))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCircularById(Long id, CircularDto circularDto) {
        Circular existingCircular = circularRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Circular not found with id: " + id));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(circularDto, existingCircular);
        Circular savedCircular = circularRepository.save(existingCircular);
        return ResponseEntity.ok().body(modelMapper.map(savedCircular, CircularDto.class));
    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Circular circular = circularRepository.findById(id).orElseThrow();
        circularRepository.delete(circular);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> apply(Long circularId, ApplicantProfileDto applicantProfileDto, Principal principal) {
        User user = userService.getUserByPrincipal(principal)
                .orElseThrow(ApplicationPlacingFailedException::new);
        ApplicantProfile applicantProfile = applicantProfileService.getByUser(user)
                .orElseThrow(ApplicationPlacingFailedException::new);
        Circular circular = circularRepository.findById(circularId)
                .orElseThrow(ApplicationPlacingFailedException::new);
        Optional<Application> applied = applicationRepository.findByCircularIdAndApplicantId(circularId, applicantProfile.getId());
        if (applied.isPresent()) {
            throw new ApplicationPlacingFailedException();
        }
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circularId)
                .orElseThrow(ApplicationPlacingFailedException::new);
        Resource profilePicture = resourceRepository.findByIdAndOwnerId(applicantProfileDto.getProfileImageId(), user.getId())
                .orElseThrow(ApplicationPlacingFailedException::new);
        Resource resume = resourceRepository.findByIdAndOwnerId(applicantProfileDto.getResumeId(), user.getId())
                .orElseThrow(ApplicationPlacingFailedException::new);

        Application application = Application.builder()
                .applicant(applicantProfile)
                .circular(circular)
                .firstName(applicantProfileDto.getFirstName())
                .lastName(applicantProfileDto.getLastName())
                .email(applicantProfileDto.getEmail())
                .phone(applicantProfileDto.getPhone())
                .cgpa(applicantProfileDto.getCgpa())
                .gender(applicantProfileDto.getGender())
                .dateOfBirth(applicantProfileDto.getDateOfBirth())
                .degreeName(applicantProfileDto.getDegreeName())
                .institutionName(applicantProfileDto.getInstitutionName())
                .passingYear(applicantProfileDto.getPassingYear())
                .profileImage(profilePicture)
                .resume(resume)
                .currentRound(screeningRoundMeta.getCurrentRound())
                .appliedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        Application savedApplication = applicationRepository.save(application);
        savedApplication.setUniqueIdentifier(savedApplication.getId() + 1000);
        Application saved = applicationRepository.save(application);
        saved.setUniqueIdentifier(null);
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId) {
        return applicationService.getApplicationByIdUnderCircular(circularId, applicationId);
    }

    @Override
    public ResponseEntity<?> approveApplicant(Long circularId, Long applicationId) {
        Circular circular = circularRepository.findById(circularId).orElseThrow();
        return roundService.approveApplicant(circular, applicationId);
    }

    @Override
    public ResponseEntity<?> getAllCircular() {
        List<Circular> circularList = circularRepository.findAll();
        return ResponseEntity.ok(circularList);
    }

    @Override
    public Optional<Circular> getCircular(Long circularId) {
        return circularRepository.findById(circularId);
    }

    @Override
    @Transactional
    public ResponseEntity<?> bookmarkCircular(Principal principal, Long circularId) {
        Optional<User> userByPrincipal = userService.getUserByPrincipal(principal);
        Optional<Circular> circularById = circularRepository.findById(circularId);
        if (userByPrincipal.isEmpty() || circularById.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        bookMarkCircularService.toggleBookMark(userByPrincipal.get(), circularById.get());
        return ResponseEntity.ok().build();
    }
}
