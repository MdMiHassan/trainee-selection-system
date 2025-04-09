package com.example.tss.service;

import com.example.tss.entity.ApplicantProfile;
import com.example.tss.entity.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationService {
    ResponseEntity<?> getAllApplications(Long circularId, Pageable pageable);

    ResponseEntity<?> getApplication(Long circularId, Long applicationId);

    List<Application> getAllApplications(ApplicantProfile applicant);
}
