package com.example.tss.exception;

public class AdmitCardGenerationFailedException extends RuntimeException {
    public AdmitCardGenerationFailedException(String error) {
        super(error);
    }
}
