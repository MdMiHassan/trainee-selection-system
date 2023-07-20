package com.example.tss.service;

import com.example.tss.dto.ApplicantProfileDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ApplicationService {
    ResponseEntity<?> getAllApplicationsUnderCircular(Long id,Pageable pageable);

    ResponseEntity<?> apply(Long circularId, ApplicantProfileDto applicantProfileDto);

    ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId);

}
