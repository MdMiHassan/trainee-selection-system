package com.example.tss.controller;

import com.example.tss.model.AuthenticationRequest;
import com.example.tss.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public final ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }
}
