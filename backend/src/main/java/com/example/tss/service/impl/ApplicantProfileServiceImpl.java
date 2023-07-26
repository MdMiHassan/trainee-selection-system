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
    public Optional<ApplicantProfile> getByUser(User user) {
        Long userId = user.getId();
        System.out.println(applicantProfileRepository.findByUserId(userId).isEmpty());
        return applicantProfileRepository.findByUserId(userId);
    }
}
