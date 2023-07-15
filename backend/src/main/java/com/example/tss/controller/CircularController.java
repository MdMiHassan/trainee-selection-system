package com.example.tss.controller;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.service.AdmitCardService;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.CircularService;
import com.example.tss.service.RoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/circulars")
@RequiredArgsConstructor
public class CircularController {
    private final CircularService circularService;
    private final ApplicationService applicationService;
    private final RoundService roundService;
    private final AdmitCardService admitCardService;

    @GetMapping
    public Page<?> getAllCirculars(Pageable pageable) {
        return circularService.getAllCircular(pageable);
    }

    @GetMapping("/{circularId}")
    public ResponseEntity<?> getCircularById(@PathVariable Long circularId) {
        return circularService.getCircularById(circularId);
    }

    @PostMapping
    public ResponseEntity<?> createCircular(@Valid @RequestBody CircularDto requestDto) {
        return circularService.createCircular(requestDto);
    }

    @PatchMapping("/{circularId}")
    public ResponseEntity<?> updateCircular(@PathVariable Long circularId, @RequestBody CircularDto requestDTO) {
        return circularService.updateCircularById(circularId, requestDTO);
    }

    @DeleteMapping("/{circularId}")
    public ResponseEntity<?> deleteCircular(@PathVariable Long circularId) {
        return circularService.delete(circularId);
    }

    @PostMapping("/{circularId}/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody ApplicantProfileDto applicantProfileDto, @PathVariable Long circularId) {
        return circularService.apply(circularId, applicantProfileDto);
    }

    @GetMapping("/{circularId}/applications")
    public ResponseEntity<?> getAllApplicationsUnderCircular(@PathVariable Long circularId) {
        return circularService.getAllApplicationsUnderCircular(circularId);
    }

    @GetMapping("/{circularId}/applications/{applicationId}")
    public ResponseEntity<?> getApplicationByIdUnderCircular(@PathVariable Long circularId, @PathVariable Long applicationId) {
        return circularService.getApplicationByIdUnderCircular(circularId, applicationId);
    }

    @PostMapping("/{circularId}/applications/{applicationId}/actions/approve")
    public ResponseEntity<?> approveApplicant(@PathVariable Long circularId, @PathVariable Long applicationId) {
        return circularService.approveApplicant(circularId, applicationId);
    }

    @PostMapping("/{circularId}/rounds/current/actions/end")
    public ResponseEntity<?> endRound(@PathVariable Long circularId) {
        return roundService.endRound(circularId);
    }

    @GetMapping("/{circularId}/rounds")
    public ResponseEntity<?> getAllRoundsUnderCircular(@PathVariable Long circularId) {
        return roundService.getAllRoundsUnderCircular(circularId);
    }

    @GetMapping("/{circularId}/rounds/{roundId}")
    public ResponseEntity<?> getRoundByIdUnderCircular(@PathVariable Long circularId, @PathVariable Long roundId) {
        return roundService.getRoundByIdUnderCircular(circularId, roundId);
    }

    @GetMapping("/{circularId}/rounds/{roundId}/candidates")
    public ResponseEntity<?> getAllCandidatesUnderRoundUnderCircular(@PathVariable Long circularId, @PathVariable Long roundId) {
        return roundService.getAllCandidatesUnderRoundUnderCircular(circularId, roundId);
    }

    @PostMapping("/{circularId}/rounds")
    public ResponseEntity<?> createRoundUnderCircular(@PathVariable Long circularId, @RequestBody ScreeningRoundDto screeningRoundDto) {
        return roundService.createRound(circularId, screeningRoundDto);
    }

    @PatchMapping("/{circularId}/rounds/{roundId}")
    public ResponseEntity<?> updateRoundByIdUnderCircular(@PathVariable Long circularId, @PathVariable Long roundId, @RequestBody ScreeningRoundDto screeningRoundDto) {
        return roundService.updateRound(circularId, roundId, screeningRoundDto);
    }

    @DeleteMapping("/{circularId}/rounds/{roundId}")
    public ResponseEntity<?> deleteRoundByIdUnderCircular(@PathVariable Long circularId, @PathVariable Long roundId) {
        return roundService.deleteRoundByIdUnderCircular(circularId, roundId);
    }

    @GetMapping("/{circularId}/rounds/{roundId}/download")
    public ResponseEntity<?> downloadAdmit(@PathVariable Long circularId, @PathVariable Long roundId, Principal principal) {
        return admitCardService.downloadAdmit(circularId, roundId, principal);
    }

    @DeleteMapping("/{circularId}/rounds/{roundId}/evaluators")
    public ResponseEntity<?> getAllEvaluatorsUnderRoundUnderCircular(@PathVariable Long circularId, @PathVariable Long roundId) {
        return roundService.getAllEvaluatorsUnderRoundUnderCircular(circularId, roundId);
    }
}
