package com.example.tss.service;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.model.ApplicantRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.Principal;

public interface ApplicantService {

    ResponseEntity<?> registerApplicant(ApplicantRegistrationRequest request);

    ResponseEntity<?> getApplicant(Long id);

    ResponseEntity<?> getAllApplicants();

    ResponseEntity<?> updateApplicantProfile(ApplicantProfileDto profileDto);

    ResponseEntity<?> deleteApplicant(Long id);

    ResponseEntity<?> lockApplicant(Long applicantId);

    ResponseEntity<?> updateApplicantProfile(Principal principal, ApplicantProfileDto applicantProfileDto);

    ResponseEntity<?> getProfile(Principal principal);

    ResponseEntity<?> getAllApplications(Principal principal);
}
