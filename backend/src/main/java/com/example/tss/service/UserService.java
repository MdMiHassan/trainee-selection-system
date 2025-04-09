package com.example.tss.service;

import com.example.tss.entity.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User newUser);

    Optional<User> getUser(Long id);

    Optional<User> getUser(String email);

    Optional<User> getUser(Principal principal);

    List<User> getAllEvaluators();
}
