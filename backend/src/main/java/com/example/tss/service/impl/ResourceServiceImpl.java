package com.example.tss.service.impl;

import com.example.tss.constants.ResourceType;
import com.example.tss.entity.Resource;
import com.example.tss.exception.ResourceNotFoundException;
import com.example.tss.model.ResourceUploadResponse;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.service.ResourceService;
import com.example.tss.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    @Override
    public ResponseEntity<?> getById(Long id) {
        Resource fileResource = resourceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id.toString()));
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }

    @Override
    public ResponseEntity<?> getByIdAndResourceType(Long resourceId, ResourceType resourceType) {
        Resource fileResource = resourceRepository.findByIdAndResourceType(resourceId, resourceType)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId.toString()));
        MediaType mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName()).orElseThrow();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(fileResource.getFileData());
    }

    @Override
    public ResponseEntity<?> getByIdAndResourceTypeAndOwnerId(Long resourceId, ResourceType resourceType, Long ownerId) {
        Resource fileResource = resourceRepository.findByIdAndResourceTypeAndOwnerId(resourceId, resourceType, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId.toString()));
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileResource.getFileName());
        if (mediaType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(mediaType.get())
                .body(fileResource.getFileData());
    }

    @Override
    public ResponseEntity<?> uploadResource(MultipartFile multipartFile) {
        try {
            Resource storedResource = storeFile(multipartFile);
            return new ResponseEntity<>(ResourceUploadResponse.builder()
                    .success(true)
                    .message("File upload successful")
                    .id(storedResource.getId())
                    .name(storedResource.getFileName()).build(), HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResourceUploadResponse.builder()
                    .success(false)
                    .message("File upload failed").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private Resource storeFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = FileUtils.extractFileExtension(fileName);
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
        return resourceRepository.save(resource);
    }


}
