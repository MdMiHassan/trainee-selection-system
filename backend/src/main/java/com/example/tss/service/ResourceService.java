package com.example.tss.service;

import com.example.tss.constants.ResourceType;
import com.example.tss.entity.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ResourceService {
    ResponseEntity<?> getById(Long id);

    ResponseEntity<?> getByIdAndResourceType(Long resourceId, ResourceType resourceType);

    ResponseEntity<?> uploadResource(Principal principal,MultipartFile multipartFile);

    Resource getResourceById(Principal principal, Long attachmentId);

}
