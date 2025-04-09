package com.example.tss.service.impl;

import com.example.tss.dto.AuthRequestDto;
import com.example.tss.dto.AuthResponseDto;
import com.example.tss.entity.EmailVerification;
import com.example.tss.entity.User;
import com.example.tss.exception.ErrorMessage;
import com.example.tss.model.EmailVerificationRequest;
import com.example.tss.model.EmailVerificationResponse;
import com.example.tss.repository.EmailVerificationRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.AuthService;
import com.example.tss.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        String email = authRequestDto.getEmail();
        String password = authRequestDto.getPassword();
        return login(email, password);
    }

    @Override
    @Transactional
    public EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest) {
        String email = emailVerificationRequest.getEmail();
        String counterVerificationCode = emailVerificationRequest.getVerificationCode();
        Optional<EmailVerification> verificationEntityByEmail = emailVerificationRepository.findByEmailAndVerificationCode(email, counterVerificationCode);
        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (verificationEntityByEmail.isEmpty() || userByEmail.isEmpty()) {
            return EmailVerificationResponse.builder()
                    .verified(false)
                    .message("Email Verification failed").build();
        }

        EmailVerification emailVerificationEntity = verificationEntityByEmail.get();
        User user = userByEmail.get();

        if (emailVerificationEntity.isVerified()) {
            return EmailVerificationResponse.builder()
                    .verified(false)
                    .message("Verification code already used").build();
        }

        if (user.getEmailVerified()) {
            return EmailVerificationResponse.builder()
                    .verified(false)
                    .message("User Already Verified").build();
        }

        emailVerificationEntity.setVerified(true);
        user.setEmailVerified(true);
        userRepository.save(user);
        emailVerificationRepository.save(emailVerificationEntity);
        return EmailVerificationResponse.builder()
                .verified(true)
                .message("Email Verification successful").build();
    }

    private AuthResponseDto login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String jwtToken = jwtService.generateJwtToken(authentication);
        return AuthResponseDto.builder()
                .success(true)
                .message(ErrorMessage.LOGIN_SUCCESSFUL)
                .token(jwtToken)
                .build();
    }
}
