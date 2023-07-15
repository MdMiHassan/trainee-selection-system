package com.example.tss.controller;

import com.example.tss.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        return resourceService.storeFile(multipartFile);
    }
    @GetMapping("/{resourceId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long resourceId){
        return resourceService.getById(resourceId);
    }
}
