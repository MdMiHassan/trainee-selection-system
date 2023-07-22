package com.example.tss.service;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.entity.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationService {
    ResponseEntity<?> getAllApplicationsUnderCircular(Long id,Pageable pageable);

    ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId);

    List<Application> getAllApplicationsOfApplicant(Long id);
}
