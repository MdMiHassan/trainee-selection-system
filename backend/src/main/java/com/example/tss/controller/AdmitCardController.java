package com.example.tss.controller;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.service.AdmitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @GetMapping("/current/{circularId}")
    public ResponseEntity<?> getAdmitId(Principal principal, @PathVariable Long circularId) {
        return admitCardService.getAdmitId(principal,circularId);
    }
    @PostMapping("/info/{circularId}")
    public ResponseEntity<?> saveInfo(@PathVariable Long circularId, @RequestBody AdmitCardInfoDto admitCardInfoDto) {
        return admitCardService.saveAdmitInfo(circularId,admitCardInfoDto);
    }
}
