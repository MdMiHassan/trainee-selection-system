package com.example.tss.controller;

import com.example.tss.dto.AdmitCardInfoDto;
import com.example.tss.dto.ResourceDto;
import com.example.tss.service.AdmitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admits")
@RequiredArgsConstructor
public class AdmitCardController {
    private final AdmitCardService admitCardService;

    @GetMapping("/verify/{admitCardId}")
    public ResponseEntity<?> verify(@PathVariable Long admitCardId) {
        ResourceDto fileResource = admitCardService.retrieveAdmit(admitCardId);
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }

    @PostMapping("/info/{circularId}")
    public ResponseEntity<?> saveInfo(@PathVariable Long circularId, @RequestBody AdmitCardInfoDto admitCardInfoDto) {
        AdmitCardInfoDto savedAdmitCardInfoDto = admitCardService.saveAdmitInfo(circularId, admitCardInfoDto);
        return ResponseEntity.ok(savedAdmitCardInfoDto);
    }
}
