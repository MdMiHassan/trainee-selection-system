package com.example.tss.service;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CircularService {
    Page<?> getAllCircular(Pageable pageable);

    ResponseEntity<?> createCircular(CircularDto circularDto);

    ResponseEntity<?> getCircularById(Long id);

    ResponseEntity<?> updateCircularById(Long id, CircularDto circularDto);

    ResponseEntity<?> getAllApplicationsUnderCircular(Long id);

    ResponseEntity<?> delete(Long id);

    ResponseEntity<?> apply(Long circularId, ApplicantProfileDto applicantProfileDto);

    ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId);

    ResponseEntity<?> approveApplicant(Long circularId, Long applicationId);

    ResponseEntity<?> getAllCircular();
}
