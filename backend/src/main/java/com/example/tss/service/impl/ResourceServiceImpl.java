package com.example.tss.service.impl;

import com.example.tss.constants.ResourceType;
import com.example.tss.dto.ResourceDto;
import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import com.example.tss.exception.ResourceNotFoundException;
import com.example.tss.model.ResourceUploadResponse;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.ResourceService;
import com.example.tss.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResourceDto getById(Long id) {
        Resource fileResource = resourceRepository.findById(id).orElseThrow();
        return modelMapper.map(fileResource, ResourceDto.class);
    }

    @Override
    public ResponseEntity<?> uploadResource(Principal principal, MultipartFile multipartFile) {
        String email = principal.getName();
        Optional<User> useByEmail = userRepository.findByEmail(email);
        if (useByEmail.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = useByEmail.get();
        try {
            Resource storedResource = storeFile(user, multipartFile, true);
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

    @Override
    public Resource getResourceById(Principal principal, Long resourceId) {
        String email = principal.getName();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        return resourceRepository.findByIdAndOwnerId(resourceId, user.getId()).orElseThrow(() -> new ResourceNotFoundException(resourceId.toString()));
    }

    private Resource storeFile(User user, MultipartFile multipartFile, boolean upload) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = FileUtils.extractFileExtension(fileName).toLowerCase();
        if (!FileUtils.isValidFileType(fileExtension)) {
            throw new IOException();
        }
        ResourceType type = fileExtension.equals("pdf") ? (upload ? ResourceType.RESUME : ResourceType.ADMITCARD) : ResourceType.PROFILEPICTURE;
        byte[] fileByte = multipartFile.getBytes();

        Resource resource = Resource.builder()
                .owner(user)
                .fileData(fileByte)
                .fileRead(true)
                .fileWrite(true)
                .fileDelete(true)
                .fileName(fileName)
                .resourceType(type)
                .fileFormat(fileExtension)
                .uploadAt(new Timestamp(System.currentTimeMillis()))
                .deletedByUser(false).build();
        return resourceRepository.save(resource);
    }


}
