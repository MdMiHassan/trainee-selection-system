package com.example.tss.service.impl;

import com.example.tss.entity.ApplicantProfile;
import com.example.tss.entity.User;
import com.example.tss.repository.ApplicantProfileRepository;
import com.example.tss.service.ApplicantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantProfileServiceImpl implements ApplicantProfileService {
    private final ApplicantProfileRepository applicantProfileRepository;

    @Override
    public Optional<ApplicantProfile> getApplicantProfile(User user) {
        Long userId = user.getId();
        return applicantProfileRepository.findByUserId(userId);
    }
}
