package com.example.tss.controller;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.service.AdmitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/admits")
@RequiredArgsConstructor
public class AdmitCardController {
    private final AdmitCardService admitCardService;

    @GetMapping("/verify/{admitCardId}")
    public ResponseEntity<?> verify(@PathVariable Long admitCardId) {
        return admitCardService.retrieveAdmit(admitCardId);
    }
    @PostMapping("/info/{circularId}")
    public ResponseEntity<?> saveInfo(@PathVariable Long circularId, @RequestBody AdmitCardInfoDto admitCardInfoDto) {
        return admitCardService.saveAdmitInfo(circularId,admitCardInfoDto);
    }
}
