package com.example.tss.exception;

import com.example.tss.model.AuthenticationResponse;
import com.example.tss.model.RegistrationResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserWithTheEmailAlreadyExistsException.class)
    public ResponseEntity<?> handleUserWithTheEmailAlreadyExistsException(UserWithTheEmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(RegistrationResponse.builder()
                .success(false)
                .message("User With Email \"" + e.getMessage() + "\" already exists").build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(AuthenticationResponse.builder()
                .success(false)
                .message(ErrorMessage.USERNAME_OR_PASSWORD_MISMATCH)
                .build(), HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleCredentialException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

}
