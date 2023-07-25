package com.example.tss.controller;

import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import com.example.tss.service.EvaluatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluators")
@RequiredArgsConstructor
public class EvaluatorController {
    private final EvaluatorService evaluatorService;

    @PostMapping
    public ResponseEntity<?> createEvaluator(@RequestBody EvaluatorDto evaluatorDto) {
        return evaluatorService.createEvaluator(evaluatorDto);
    }
    @GetMapping
    public ResponseEntity<?> getEvaluators() {
        return evaluatorService.getEvaluators();
    }

    @GetMapping("/{evaluatorId}/candidates")
    public ResponseEntity<?> getAllAssignedApplicants(@PathVariable Long evaluatorId) {
        return evaluatorService.getAllAssignedApplicants(evaluatorId);
    }

    @GetMapping("/current/candidates")
    public ResponseEntity<?> getAllAssignedApplicantsToCurrentEvaluator(Principal principal) {
        return evaluatorService.getAllAssignedApplicants(principal);
    }

    @PostMapping("/{evaluatorId}/candidates/assign")
    public ResponseEntity<?> assignEvaluatorToApplicants(@PathVariable Long evaluatorId,
                                                         @RequestParam Long candidate,
                                                         @RequestParam Long round) {
        return evaluatorService.assignEvaluatorToApplicants(evaluatorId,candidate,round);
    }

    @PostMapping("/current/candidates/marks")
    public ResponseEntity<?> updateAssignedApplicantsMarks(Principal principal,@Valid @RequestBody MarksDto marksDto) {
        return evaluatorService.updateAssignedApplicantsMarks(principal, marksDto);
    }
}