package com.example.tss.exception;

import com.example.tss.model.AuthenticationResponse;
import com.example.tss.model.RegistrationResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
                .message(ErrorMessage.EMAIL_EXISTS).build());
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

    @ExceptionHandler(AdmitCardGenerationFailedException.class)
    public ResponseEntity<?> handleAdmitCardGenerationFailedException(AdmitCardGenerationFailedException e) {
        return ResponseEntity.badRequest().body(ErrorMessage.ADMIT_CARD_GENERATION_FAILED);
    }

    @ExceptionHandler(ApplicationPlacingFailedException.class)
    public ResponseEntity<?> handleApplicationPlacingFailedException(ApplicationPlacingFailedException e) {
        return ResponseEntity.badRequest().body(ErrorMessage.FAILED_TO_PLACE_APPLICATION);
    }

    @ExceptionHandler(ApplicationApprovingFailedException.class)
    public ResponseEntity<?> handleApplicationApprovingFailedException(ApplicationApprovingFailedException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ApplicantMarkUpdateFailedException.class)
    public ResponseEntity<?> handleApplicationApprovingFailedException(ApplicantMarkUpdateFailedException e) {
        return ResponseEntity.badRequest().build();
    }
}
