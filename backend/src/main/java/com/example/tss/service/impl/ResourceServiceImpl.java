package com.example.tss.service.impl;

import com.example.tss.entity.Resource;
import com.example.tss.exception.ResourceNotFoundException;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.constants.ResourceType;
import com.example.tss.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    public ResponseEntity<?> storeFile(MultipartFile multipartFile) {
        Resource savedResource=null;
        try {
            String fileName = multipartFile.getOriginalFilename();
            String fileExtension = getFileExtension(fileName);
            byte[] fileByte = multipartFile.getBytes();
            Resource resource = Resource.builder()
                    .fileData(fileByte)
                    .fileRead(true)
                    .fileWrite(true)
                    .fileDelete(true)
                    .fileName(fileName)
                    .fileFormat(fileExtension)
                    .uploadAt(new Timestamp(System.currentTimeMillis()))
                    .deletedByUser(false).build();
            savedResource = resourceRepository.save(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(URI.create("/resource/savedResource.getId()")).build();
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            int lastDotIndex = fileName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return fileName.substring(lastDotIndex + 1);
            }
        }
        return "";
    }

    public ResponseEntity<?> getById(Long id) {
        Resource fileResource = resourceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id.toString()));
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }

    @Override
    public ResponseEntity<?> getByIdAndResourceType(Long resourceId, ResourceType resourceType) {
        Resource fileResource=resourceRepository.findByIdAndResourceType(resourceId, resourceType)
                .orElseThrow(()->new ResourceNotFoundException(resourceId.toString()));
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }

    @Override
    public ResponseEntity<?> getByIdAndResourceTypeAndOwnerId(Long resourceId, ResourceType resourceType, Long ownerId) {
        Resource fileResource=resourceRepository.findByIdAndResourceTypeAndOwnerId(resourceId, resourceType,ownerId)
                .orElseThrow(()->new ResourceNotFoundException(resourceId.toString()));
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }


}
