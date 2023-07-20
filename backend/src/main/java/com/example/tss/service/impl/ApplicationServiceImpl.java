package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.ScreeningRoundMarkDto;
import com.example.tss.entity.Application;
import com.example.tss.entity.Circular;
import com.example.tss.model.ApplicationResponseModel;
import com.example.tss.repository.ApplicationRepository;
import com.example.tss.repository.CircularRepository;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.service.ApplicantService;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.ScreeningRoundMarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CircularRepository circularRepository;
    private final ScreeningRoundMarkService screeningRoundMarkService;
    public ResponseEntity<?> getAllApplicationsUnderCircular(Long circularId, Pageable pageable) {
        List<ApplicationResponseModel> applications=applicationRepository.findByCircularId(circularId).stream()
                .map(application -> {

                    ScreeningRoundMarkDto.builder()
//                            .mark()
                            .build();
                    return ApplicationResponseModel.builder()
                            .id(application.getId())
                            .build();
                }).toList();

        return ResponseEntity.ok(applications);
    }

    public ResponseEntity<?> getApplication(Long id) {
        return null;
    }


    public ResponseEntity<?> apply(Long circularId, ApplicantProfileDto applicantProfileDto) {
        Circular circular = circularRepository.findById(circularId).orElseThrow();
//        Authentication authentication,
//        ApplicantProfile applicant=applicantService.getProfile(authentication)
//        Resource profilePicture=resourceRepository.findByIdAndOwnerId(applicantProfileDto.getProfileImageId(),ownerId).orElseThrow();
//        Resource resume=resourceRepository.findByIdAndOwnerId(applicantProfileDto.getResumeId(),ownerId).orElseThrow();

        Application application = Application.builder()
//                .applicant(applicant)
                .circular(circular)
                .firstName(applicantProfileDto.getFirstName())
                .lastName(applicantProfileDto.getLastName())
                .email(applicantProfileDto.getEmail())
                .phone(applicantProfileDto.getPhone())
                .cgpa(applicantProfileDto.getCgpa())
                .gender(applicantProfileDto.getGender())
                .dateOfBirth(applicantProfileDto.getDateOfBirth())
//                .profileImage(profilePicture)
//                .resume(resume)
                .appliedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        Application savedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(savedApplication);
    }

    @Override
    public ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId) {
        Application application=applicationRepository.findByIdAndCircularId(applicationId,circularId).orElseThrow();
        return ResponseEntity.ok(application);
    }
}
