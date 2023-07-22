package com.example.tss.service;

import com.example.tss.entity.ApplicantProfile;
import com.example.tss.entity.User;

import java.util.Optional;

public interface ApplicantProfileService {
    Optional<ApplicantProfile> getByUser(User user);
}
