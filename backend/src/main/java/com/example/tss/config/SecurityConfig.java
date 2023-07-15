package com.example.tss.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Base64;

@Configuration
public class SecurityConfig {
    private final String base64Key = "dG9rZW5XbVpxNHQ3dyF6JUMqRi1KYU5jUmZValhuMnI1dTh4L0E/RChHK0tiUGVTZ1ZrWXAzczZ2OXkkQiZFKUhATWNRZlRqV21acTR0N3cheiVDKkYtSmFOZFJnVWtYcDJyNXU4eC9BP0QoRytLYlBlU2hWbVlxM3Q2djl5JEImRSlIQA==";

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Key secretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }
}