package com.example.tss.service;

import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface AdmitCardService {
    ResponseEntity<?> retrieveAdmit(Long id);

    ResponseEntity<?> downloadAdmit(Long circularId, Long roundId, Principal principal);
}
