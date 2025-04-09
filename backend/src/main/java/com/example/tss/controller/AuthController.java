package com.example.tss.controller;

import com.example.tss.dto.AuthRequestDto;
import com.example.tss.dto.AuthResponseDto;
import com.example.tss.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
//    @PreAuthorize("permitAll()")
    public final ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponse = authService.login(authRequestDto);
        return ResponseEntity.ok(authResponse);
    }
}
