package com.example.tss.service;

import com.example.tss.model.ApplicantRegistrationRequest;
import com.example.tss.model.AuthenticationRequest;
import com.example.tss.model.EmailVerificationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(String email, String password);

    ResponseEntity<?> login(AuthenticationRequest authenticationRequest);

    ResponseEntity<?> verifyEmail(EmailVerificationRequest emailVerificationRequest);
}
