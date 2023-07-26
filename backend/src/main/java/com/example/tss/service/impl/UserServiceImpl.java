package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.entity.User;
import com.example.tss.repository.ApplicantProfileRepository;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(User newUser) {
        String email = newUser.getEmail();
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            return userRepository.save(User.builder()
                    .email(email)
                    .role(newUser.getRole())
                    .password(encodedPassword)
                    .enabled(newUser.getEnabled())
                    .locked(newUser.getLocked())
                    .registeredAt(new Date(System.currentTimeMillis()))
                    .expiredAt(newUser.getExpiredAt())
                    .build());
        }
        User oldUser = userByEmail.get();
        oldUser.setEmail(email);
        oldUser.setPassword(encodedPassword);
        oldUser.setEnabled(newUser.getEnabled());
        oldUser.setLocked(newUser.getLocked());
        oldUser.setRole(newUser.getRole());
        return userRepository.save(oldUser);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByPrincipal(Principal principal) {
        String userEmail = principal.getName();
        return userRepository.findByEmail(userEmail);
    }


    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllEvaluators() {
        return userRepository.findByRole(Role.EVALUATOR);
    }
}
