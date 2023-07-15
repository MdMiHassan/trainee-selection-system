package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.entity.ApplicantProfile;
import com.example.tss.entity.Resource;
import com.example.tss.entity.User;
import com.example.tss.exception.UserWithTheEmailAlreadyExistsException;
import com.example.tss.model.ApplicantRegistrationRequest;
import com.example.tss.repository.ApplicantProfileRepository;
import com.example.tss.repository.ResourceRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.constants.ResourceType;
import com.example.tss.service.ApplicantService;
import com.example.tss.service.AuthenticationService;
import com.example.tss.service.ResourceService;
import com.example.tss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
    private final UserService userService;
    private final ApplicantProfileRepository applicantProfileRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;

    public ResponseEntity<?> registerApplicant(ApplicantRegistrationRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserWithTheEmailAlreadyExistsException(email);
        }
        User user = User.builder()
                .email(request.getEmail())
                .role(Role.APPLICANT)
                .password(request.getPassword())
                .enabled(true)
                .locked(false)
                .build();
        User savedUser = userService.save(user);
        ModelMapper modelMapper = new ModelMapper();
        ApplicantProfile applicantProfile = modelMapper.map(request, ApplicantProfile.class);
        applicantProfile.setUser(savedUser);
        Optional<Resource> profileImage = resourceRepository.findByIdAndResourceTypeAndOwnerId(request.getProfileImageId(), ResourceType.PROFILEPICTURE, savedUser.getId());
        profileImage.ifPresent(applicantProfile::setProfileImage);
        Optional<Resource> resume = resourceRepository.findByIdAndResourceTypeAndOwnerId(request.getResumeId(), ResourceType.RESUME, savedUser.getId());
        resume.ifPresent(applicantProfile::setResume);
        applicantProfileRepository.save(applicantProfile);
        return authenticationService.login(request.getEmail(), request.getPassword());
    }


    public ResponseEntity<?> getProfile(Principal principal) {
        ApplicantProfile profile = getApplicantProfile(principal);
        ModelMapper modelMapper = new ModelMapper();
        ApplicantProfileDto profileDto = modelMapper.map(profile, ApplicantProfileDto.class);
        profileDto.setProfileImagePath("/resource/" + profile.getProfileImage().getId());
        profileDto.setResumePath("/resource/" + profile.getResume().getId());
        return ResponseEntity.ok(profileDto);
    }

    public ResponseEntity<?> getApplicant(Long id) {
        ApplicantProfile applicantProfile = applicantProfileRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id.toString()));
        ModelMapper modelMapper = new ModelMapper();
        ApplicantProfileDto applicantProfileDto = modelMapper.map(applicantProfile, ApplicantProfileDto.class);
        return ResponseEntity.ok(applicantProfileDto);
    }

    public ResponseEntity<?> getAllApplicants() {
        List<ApplicantProfile> all = applicantProfileRepository.findAll();
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> updateApplicantProfile(ApplicantProfileDto profileDto) {
        ModelMapper modelMapper = new ModelMapper();
        ApplicantProfile applicantProfile = modelMapper.map(profileDto, ApplicantProfile.class);
        ApplicantProfile savedApplicantProfile = applicantProfileRepository.save(applicantProfile);
        return ResponseEntity.ok(profileDto);
    }

    public ResponseEntity<?> deleteApplicant(Long id) {
        ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(id).orElseThrow();
        applicantProfileRepository.delete(applicantProfile);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> lockApplicant(Long applicantId) {
        ApplicantProfile applicantProfile = applicantProfileRepository.findById(applicantId).orElseThrow();
        return null;
    }

    @Override
    public ResponseEntity<?> updateApplicantProfile(Principal principal, ApplicantProfileDto applicantProfileDto) {
        ApplicantProfile profile = getApplicantProfile(principal);
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.map(applicantProfileDto,profile);
        ApplicantProfile saved = applicantProfileRepository.save(profile);
        return ResponseEntity.ok(modelMapper.map(saved,ApplicantProfileDto.class));
    }
    private ApplicantProfile getApplicantProfile(Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return applicantProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
