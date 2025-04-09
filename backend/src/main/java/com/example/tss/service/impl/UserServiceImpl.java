package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.entity.User;
import com.example.tss.repository.UserRepository;
import com.example.tss.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
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
        User user = userRepository.findByEmail(newUser.getEmail())
                .orElseGet(User::new);
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            user.setPassword(encodedPassword);
        }
        if (newUser.getEnabled() != null) {
            user.setEnabled(newUser.getEnabled());
        }
        if (newUser.getLocked() != null) {
            user.setLocked(newUser.getLocked());
        }
        if (newUser.getRole() != null) {
            user.setRole(newUser.getRole());
        }

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUser(Principal principal) {
        String userEmail = principal.getName();
        if (!StringUtils.hasText(userEmail)) {
            return Optional.empty();
        }
        ModelMapper mapper = new ModelMapper();
        return userRepository.findByEmail(userEmail);
    }


    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllEvaluators() {
        return userRepository.findByRole(Role.EVALUATOR);
    }
}
