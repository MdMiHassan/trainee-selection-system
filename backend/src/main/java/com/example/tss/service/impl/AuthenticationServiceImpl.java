package com.example.tss.service.impl;

import com.example.tss.entity.EmailVerification;
import com.example.tss.entity.User;
import com.example.tss.exception.ErrorMessage;
import com.example.tss.model.AuthenticationRequest;
import com.example.tss.model.AuthenticationResponse;
import com.example.tss.model.EmailVerificationRequest;
import com.example.tss.model.EmailVerificationResponse;
import com.example.tss.repository.EmailVerificationRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.AuthenticationService;
import com.example.tss.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body(AuthenticationResponse.builder()
                    .success(false)
                    .message(ErrorMessage.LOGIN_FAILED)
                    .build());
        }
        String jwtToken = jwtService.generateJwtToken(authentication);
        if (jwtToken == null) {
            return ResponseEntity.internalServerError().body(AuthenticationResponse.builder()
                    .success(false)
                    .message(ErrorMessage.JWT_TOKEN_GENERATION_FAILED)
                    .build());
        }
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .success(true)
                .message(ErrorMessage.LOGIN_SUCCESSFUL)
                .token(jwtToken)
                .build());
    }

    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        return login(authenticationRequest.getEmail(), authenticationRequest.getPassword());
    }

    @Override
    @Transactional
    public ResponseEntity<?> verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        String email = emailVerificationRequest.getEmail();
        String counterVerificationCode = emailVerificationRequest.getVerificationCode();
        Optional<EmailVerification> verificationEntityByEmail = emailVerificationRepository.findByEmail(email);
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (verificationEntityByEmail.isPresent() && userByEmail.isPresent()) {
            EmailVerification emailVerificationEntity = verificationEntityByEmail.get();
            User user = userByEmail.get();
            String verificationCode = emailVerificationEntity.getVerificationCode();

            if (!emailVerificationEntity.isVerified() && verificationCode.equals(counterVerificationCode)) {
                emailVerificationEntity.setVerified(true);
                user.setEmailVerified(true);
                userRepository.save(user);
                emailVerificationRepository.save(emailVerificationEntity);
                return ResponseEntity.ok(EmailVerificationResponse.builder()
                        .verified(true)
                        .message("Email Verification successful").build());
            }

        }
        return ResponseEntity.badRequest().body(EmailVerificationResponse.builder()
                .verified(false)
                .message("Email Verification failed").build());
    }
}
