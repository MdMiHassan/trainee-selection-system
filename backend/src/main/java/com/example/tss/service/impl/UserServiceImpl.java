package com.example.tss.service.impl;

import com.example.tss.entity.User;
import com.example.tss.exception.UserWithTheEmailAlreadyExistsException;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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
        User oldUser =userByEmail.get();
        oldUser.setEmail(email);
        oldUser.setPassword(encodedPassword);
        oldUser.setEnabled(newUser.getEnabled());
        oldUser.setLocked(newUser.getLocked());
        oldUser.setRole(newUser.getRole());
        return userRepository.save(oldUser);
    }
}
