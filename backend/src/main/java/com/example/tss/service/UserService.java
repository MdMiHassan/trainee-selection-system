package com.example.tss.service;

import com.example.tss.entity.User;

import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> getByEmail(String email);
}
