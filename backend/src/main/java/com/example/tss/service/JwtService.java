package com.example.tss.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {
    Claims parseClaims(String token);

    String generateJwtToken(Authentication authentication);
}