package com.example.tss.exception;

public interface ErrorMessage {
        String EMAIL_EXISTS = "user with the email already exists";
        String JWT_TOKEN_GENERATION_FAILED = "Failed to generate the jwt token please try again";
        String LOGIN_SUCCESSFUL = "Login successful";
        String LOGIN_FAILED = "Login failed";
        String USERNAME_OR_PASSWORD_MISMATCH = "Incorrect Email or Password";
        String FAILED_TO_PLACE_APPLICATION="Failed to place application";
        String ADMIT_CARD_GENERATION_FAILED="Admit card generation failed";
}
