package com.example.tss.service;

import com.example.tss.dto.AuthRequestDto;
import com.example.tss.dto.AuthResponseDto;
import com.example.tss.model.EmailVerificationRequest;
import com.example.tss.model.EmailVerificationResponse;

public interface AuthService {

    AuthResponseDto login(AuthRequestDto authRequestDto);

    EmailVerificationResponse verifyEmail(EmailVerificationRequest emailVerificationRequest);
}
