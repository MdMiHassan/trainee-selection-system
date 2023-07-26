package com.example.tss.service.impl;

import com.example.tss.dto.ScreeningRoundMarkDto;
import com.example.tss.entity.Application;
import com.example.tss.model.ApplicationResponseModel;
import com.example.tss.repository.ApplicationRepository;
import com.example.tss.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public ResponseEntity<?> getAllApplicationsUnderCircular(Long circularId, Pageable pageable) {
        List<ApplicationResponseModel> applications = applicationRepository.findByCircularId(circularId).stream()
                .map(application -> {
                    ScreeningRoundMarkDto.builder()
                            .build();
                    return ApplicationResponseModel.builder()
                            .id(application.getId())
                            .build();
                }).toList();

        return ResponseEntity.ok(applications);
    }

    @Override
    public ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId) {
        Application application = applicationRepository.findByIdAndCircularId(applicationId, circularId).orElseThrow();
        return ResponseEntity.ok(application);
    }

    @Override
    public List<Application> getAllApplicationsOfApplicant(Long applicantId) {
        List<Application> applications=applicationRepository.findByApplicantId(applicantId);
        return applications;
    }
}
