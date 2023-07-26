package com.example.tss.service;

import com.example.tss.entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> getByEmail(String email);

    Optional<User> getUserByPrincipal(Principal principal);

    Optional<User> getById(Long evaluatorId);

    List<User> getAllEvaluators();
}
