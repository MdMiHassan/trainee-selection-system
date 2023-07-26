package com.example.tss.controller;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.model.ApplicantRegistrationRequest;
import com.example.tss.model.EmailVerificationRequest;
import com.example.tss.service.ApplicantService;
import com.example.tss.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<?> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<?> getApplicant(@PathVariable Long applicantId) {
        return applicantService.getApplicant(applicantId);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody ApplicantProfileDto applicantProfileDto) {
        return applicantService.updateApplicantProfile(principal, applicantProfileDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        return applicantService.getProfile(principal);
    }

    @GetMapping("/current/applications")
    public ResponseEntity<?> getAllApplications(Principal principal) {
        return applicantService.getAllApplications(principal);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerApplicant(@Valid @RequestBody ApplicantRegistrationRequest applicantRegistrationRequest) {
        return applicantService.registerApplicant(applicantRegistrationRequest);
    }

    @PostMapping("/register/email/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        return authenticationService.verifyEmail(emailVerificationRequest);
    }

    @PatchMapping("/{applicantId}")
    public ResponseEntity<?> updateApplicant(@PathVariable Long applicantId, ApplicantProfileDto applicantProfileDto) {
        return applicantService.updateApplicantProfile(applicantProfileDto);
    }

    @DeleteMapping("/{applicantId}")
    public ResponseEntity<?> deleteApplicant(@PathVariable Long applicantId) {
        return applicantService.deleteApplicant(applicantId);
    }

    @PatchMapping("/{applicantId}/actions/lock")
    public ResponseEntity<?> lockApplicant(@PathVariable Long applicantId) {
        return applicantService.lockApplicant(applicantId);
    }
}
