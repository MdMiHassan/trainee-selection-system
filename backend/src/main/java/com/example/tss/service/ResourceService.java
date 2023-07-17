package com.example.tss.service;

import com.example.tss.constants.ResourceType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> getByIdAndResourceType(Long resourceId, ResourceType resourceType);

    ResponseEntity<?> getByIdAndResourceTypeAndOwnerId(Long resourceId, ResourceType resourceType, Long ownerId);

    ResponseEntity<?> uploadResource(MultipartFile multipartFile);
}
