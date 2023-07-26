package com.example.tss.service.impl;

import com.example.tss.constants.Gender;
import com.example.tss.constants.Role;
import com.example.tss.entity.AdminProfile;
import com.example.tss.entity.User;
import com.example.tss.repository.AdminProfileRepository;
import com.example.tss.service.AdminService;
import com.example.tss.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleAdminService implements AdminService {
    private final UserService userService;
    private final AdminProfileRepository adminProfileRepository;

    @Override
    @Transactional
    public void createSuperAdmin() {
        User superAdmin = User.builder()
                .email("super.admin@example.com")
                .password("super.password")
                .role(Role.ADMIN)
                .locked(false)
                .enabled(true)
                .build();
        User savedUser = userService.save(superAdmin);
        Optional<AdminProfile> adminProfileById = adminProfileRepository.findByUserId(savedUser.getId());
        if (adminProfileById.isEmpty()) {
            adminProfileRepository.save(AdminProfile.builder()
                    .user(savedUser)
                    .firstName("Super")
                    .lastName("Admin")
                    .empId("000000")
                    .phone("000000")
                    .gender(Gender.OTHER)
                    .designation("Admin")
                    .division("System")
                    .build());
        }
    }
}
