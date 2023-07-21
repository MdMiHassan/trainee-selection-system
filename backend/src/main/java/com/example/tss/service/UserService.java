package com.example.tss.service;

import com.example.tss.entity.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> getByEmail(String email);

    Optional<User> getUserByPrincipal(Principal principal);
}
