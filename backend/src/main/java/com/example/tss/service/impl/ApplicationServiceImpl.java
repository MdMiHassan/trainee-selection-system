package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.ScreeningRoundMarkDto;
import com.example.tss.entity.Application;
import com.example.tss.entity.Circular;
import com.example.tss.entity.ScreeningRoundMeta;
import com.example.tss.model.ApplicationResponseModel;
import com.example.tss.repository.ApplicationRepository;
import com.example.tss.repository.CircularRepository;
import com.example.tss.repository.ScreeningRoundMetaRepository;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.ScreeningRoundMarkService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final CircularRepository circularRepository;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningRoundMarkService screeningRoundMarkService;

    @Override
    @Transactional
    public ResponseEntity<?> getAllApplicationsUnderCircular(Long circularId, Pageable pageable) {
        List<ApplicationResponseModel> applications = applicationRepository.findByCircularId(circularId).stream()
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
