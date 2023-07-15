package com.example.tss.service.impl;

import com.example.tss.exception.ErrorMessage;
import com.example.tss.model.AuthenticationRequest;
import com.example.tss.model.AuthenticationResponse;
import com.example.tss.service.AuthenticationService;
import com.example.tss.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public ResponseEntity<?> login(String email, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
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
        return login(authenticationRequest.getEmail(),authenticationRequest.getPassword());
    }
}
