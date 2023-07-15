package com.example.tss.controller;

import com.example.tss.service.AdmitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admits")
@RequiredArgsConstructor
public class AdmitCardController {
    private final AdmitCardService admitCardService;

    @GetMapping("/verify/{admitCardId}")
    public ResponseEntity<?> verify(@PathVariable Long admitCardId) {
        return admitCardService.retrieveAdmit(admitCardId);
    }
}
