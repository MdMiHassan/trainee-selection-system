package com.example.tss.service;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Optional;

public interface CircularService {
    Page<?> getAllCircular(Pageable pageable);

    ResponseEntity<?> createCircular(CircularDto circularDto);

    ResponseEntity<?> getCircularById(Long id);

    ResponseEntity<?> updateCircularById(Long id, CircularDto circularDto);

    ResponseEntity<?> delete(Long id);

    ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId);

    ResponseEntity<?> approveApplicant(Long circularId, Long applicationId);

    ResponseEntity<?> getAllCircular();

    Optional<CircularDto> getCircular(Long circularId);

    ResponseEntity<?> bookmarkCircular(Principal principal, Long circularId);

    ApplicantProfileDto apply(Long circularId, ApplicantProfileDto applicantProfileDto, Principal principal);
}
