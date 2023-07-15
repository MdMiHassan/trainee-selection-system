package com.example.tss.exception;

public class UserWithTheEmailAlreadyExistsException extends RuntimeException {
    public UserWithTheEmailAlreadyExistsException(String email) {
        super(email);
    }
}
