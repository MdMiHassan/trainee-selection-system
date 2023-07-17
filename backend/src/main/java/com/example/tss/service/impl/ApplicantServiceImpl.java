package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.entity.ApplicantProfile;
import com.example.tss.entity.EmailVerification;
import com.example.tss.entity.User;
import com.example.tss.exception.UserWithTheEmailAlreadyExistsException;
import com.example.tss.model.ApplicantRegistrationRequest;
import com.example.tss.model.ApplicantRegistrationResponse;
import com.example.tss.repository.ApplicantProfileRepository;
import com.example.tss.repository.EmailVerificationRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.ApplicantService;
import com.example.tss.service.EmailService;
import com.example.tss.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
    private final UserService userService;
    private final ApplicantProfileRepository applicantProfileRepository;
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    @Override
    @Transactional
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
                .emailVerified(false)
                .build();
        User savedUser = userService.save(user);
        String randomUUID = UUID.randomUUID().toString().replaceAll("-", "");
        String random6DigitNumber = randomUUID.substring(0, 6);
        EmailVerification emailVerification = EmailVerification.builder()
                .email(email)
                .verificationCode(random6DigitNumber)
                .verified(false)
                .build();
        EmailVerification savedEmailVerification = emailVerificationRepository.save(emailVerification);
        try {
            emailService.sendEmail(email,
                    "Email Verification Code",
                    savedEmailVerification.getVerificationCode());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ApplicantRegistrationResponse.builder()
                .success(true)
                .message("Registration success: please verify email").build());
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

    @Transactional
    public ResponseEntity<?> updateApplicantProfile(ApplicantProfileDto profileDto) {
//        ModelMapper modelMapper = new ModelMapper();
//        ApplicantProfile applicantProfile = modelMapper.map(request, ApplicantProfile.class);
//        applicantProfile.setUser(savedUser);
//        Optional<Resource> profileImage = resourceRepository.findByIdAndResourceTypeAndOwnerId(
//                request.getProfileImageId(),
//                ResourceType.PROFILEPICTURE,
//                savedUser.getId());
//        profileImage.ifPresent(applicantProfile::setProfileImage);
//        Optional<Resource> resume = resourceRepository.findByIdAndResourceTypeAndOwnerId(
//                request.getResumeId(),
//                ResourceType.RESUME,
//                savedUser.getId());
//        resume.ifPresent(applicantProfile::setResume);
//        applicantProfileRepository.save(applicantProfile);
//        return authenticationService.login(request.getEmail(), request.getPassword());

        ModelMapper modelMapper = new ModelMapper();
        ApplicantProfile applicantProfile = modelMapper.map(profileDto, ApplicantProfile.class);
        ApplicantProfile savedApplicantProfile = applicantProfileRepository.save(applicantProfile);
        return ResponseEntity.ok(profileDto);
    }

    @Transactional
    public ResponseEntity<?> deleteApplicant(Long id) {
        ApplicantProfile applicantProfile = applicantProfileRepository.findByUserId(id).orElseThrow();
        applicantProfileRepository.delete(applicantProfile);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> lockApplicant(Long applicantId) {
        ApplicantProfile applicantProfile = applicantProfileRepository.findById(applicantId).orElseThrow();
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateApplicantProfile(Principal principal, ApplicantProfileDto applicantProfileDto) {
        ApplicantProfile profile = getApplicantProfile(principal);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(applicantProfileDto, profile);
        ApplicantProfile saved = applicantProfileRepository.save(profile);
        return ResponseEntity.ok(modelMapper.map(saved, ApplicantProfileDto.class));
    }

    private ApplicantProfile getApplicantProfile(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return applicantProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
