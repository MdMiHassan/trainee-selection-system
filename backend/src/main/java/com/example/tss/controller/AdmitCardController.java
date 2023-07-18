package com.example.tss.controller;

import com.example.tss.service.AdmitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admits")
@RequiredArgsConstructor
//@CrossOrigin
public class AdmitCardController {
    private final AdmitCardService admitCardService;

    @GetMapping("/verify/{admitCardId}")
    public ResponseEntity<?> verify(@PathVariable Long admitCardId) {
        return admitCardService.retrieveAdmit(admitCardId);
    }
}
