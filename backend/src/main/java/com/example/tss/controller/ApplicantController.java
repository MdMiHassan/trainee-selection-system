package com.example.tss.controller;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.model.ApplicantRegistrationRequest;
import com.example.tss.service.ApplicantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;

    @GetMapping
    public ResponseEntity<?> getAllApplicants() {
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<?> getApplicant(@PathVariable Long applicantId) {
        return applicantService.getApplicant(applicantId);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal,ApplicantProfileDto applicantProfileDto) {
        return applicantService.updateApplicantProfile(principal,applicantProfileDto);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> updateProfile(Principal principal) {
        return applicantService.getProfile(principal);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerApplicant(@Valid @RequestBody ApplicantRegistrationRequest applicantRegistrationRequest) {
        return applicantService.registerApplicant(applicantRegistrationRequest);
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
