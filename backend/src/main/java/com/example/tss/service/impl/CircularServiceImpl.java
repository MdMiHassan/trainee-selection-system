package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.entity.*;
import com.example.tss.exception.ApplicationPlacingFailedException;
import com.example.tss.repository.*;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.CircularService;
import com.example.tss.service.RoundService;
import com.example.tss.service.UserService;
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
import java.util.Date;
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
    public ApplicantProfileDto apply(Long circularId, ApplicantProfileDto applicantProfileDto, Principal principal) {
        Circular circular = getCircularOrThrow(circularId);
        User user = getUserFromPrincipal(principal);
        ApplicantProfile applicantProfile = getApplicantProfileForUser(user);
        ScreeningRoundMeta screeningRoundMeta = getScreeningRoundMetaForCircular(circularId);
        Resource profilePicture = getResourceByIdAndOwnerId(applicantProfileDto.getProfileImageId(), user.getId());
        Resource resume = getResourceByIdAndOwnerId(applicantProfileDto.getResumeId(), user.getId());
        ScreeningRound currentRound = screeningRoundMeta.getCurrentRound();

        validateApplicationConditions(circularId, applicantProfile.getId(), currentRound);

        Application application = createApplication(applicantProfile, circular, applicantProfileDto, profilePicture, resume, currentRound);
        Application savedApplication = saveApplication(application);
        savedApplication.setUniqueIdentifier(savedApplication.getId() + 1000);
        Application cleanSavedApplication = clearUniqueIdentifier(savedApplication);

        return modelMapper.map(cleanSavedApplication, ApplicantProfileDto.class);
    }

    private Circular getCircularOrThrow(Long circularId) {
        return circularRepository.findById(circularId)
                .orElseThrow(ApplicationPlacingFailedException::new);
    }

    private User getUserFromPrincipal(Principal principal) {
        return userService.getUser(principal)
                .orElseThrow(ApplicationPlacingFailedException::new);
    }

    private ApplicantProfile getApplicantProfileForUser(User user) {
        return applicantProfileService.getApplicantProfile(user)
                .orElseThrow(ApplicationPlacingFailedException::new);
    }

    private ScreeningRoundMeta getScreeningRoundMetaForCircular(Long circularId) {
        return screeningRoundMetaRepository.findByCircularId(circularId)
                .orElseThrow(ApplicationPlacingFailedException::new);
    }

    private Resource getResourceByIdAndOwnerId(Long resourceId, Long ownerId) {
        return resourceRepository.findByIdAndOwnerId(resourceId, ownerId)
                .orElseThrow(ApplicationPlacingFailedException::new);
    }

    private void validateApplicationConditions(Long circularId, Long applicantId, ScreeningRound currentRound) {
        boolean alreadyApplied = applicationRepository.existsByCircularIdAndApplicantId(circularId, applicantId);
        Circular circular = currentRound.getCircular();
        boolean circularClosed = circular.getClosingDate().after(new Date(System.currentTimeMillis()));
        boolean applicationReceivingClosed = currentRound.getSerialNo() > 0;

        if (alreadyApplied || circularClosed || applicationReceivingClosed) {
            throw new ApplicationPlacingFailedException();
        }
    }

    private Application createApplication(ApplicantProfile applicantProfile,
                                          Circular circular,
                                          ApplicantProfileDto applicantProfileDto,
                                          Resource profilePicture,
                                          Resource resume,
                                          ScreeningRound currentRound) {
        return Application.builder()
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
                .currentRound(currentRound)
                .appliedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    private Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    private Application clearUniqueIdentifier(Application application) {
        application.setUniqueIdentifier(null);
        return application;
    }


    @Override
    public ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId) {
        return applicationService.getApplication(circularId, applicationId);
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
    public Optional<CircularDto> getCircular(Long circularId) {
        ModelMapper mapper = new ModelMapper();
        Optional<CircularDto> circularDto = circularRepository.findById(circularId)
                .map(circular -> mapper.map(circular, CircularDto.class));
        return circularDto;
    }

    @Override
    public ResponseEntity<?> bookmarkCircular(Principal principal, Long circularId) {
        return null;
    }

}
