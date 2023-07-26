package com.example.tss.controller;

import com.example.tss.dto.ResourceDto;
import com.example.tss.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile( Principal principal,@RequestParam("file") MultipartFile multipartFile){
        return resourceService.uploadResource(principal,multipartFile);
    }
    @GetMapping("/{resourceId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long resourceId){
        ResourceDto fileResource =resourceService.getById(resourceId);
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }
}
