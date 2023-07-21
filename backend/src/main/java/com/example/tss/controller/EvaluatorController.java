package com.example.tss.controller;

import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import com.example.tss.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/evaluators")
@RequiredArgsConstructor
//@CrossOrigin
public class EvaluatorController {
    private EvaluatorService evaluatorService;

    @PostMapping
    public ResponseEntity<?> createEvaluator(EvaluatorDto evaluatorDto) {
        return evaluatorService.createEvaluator(evaluatorDto);
    }

    @GetMapping("/{evaluatorId}/candidates")
    public ResponseEntity<?> getAllAssignedApplicants(@PathVariable Long evaluatorId) {
        return evaluatorService.getAllAssignedApplicants(evaluatorId);
    }

    @GetMapping("/current/candidates")
    public ResponseEntity<?> getAllAssignedApplicantsToCurrentEvaluator(Principal principal) {
        return evaluatorService.getAllAssignedApplicants(principal);
    }

    @PostMapping("/{evaluatorId}/candidates")
    public ResponseEntity<?> assignEvaluatorToApplicants(@PathVariable Long evaluatorId, @RequestBody List<Long> candidateIds) {
        return evaluatorService.assignEvaluatorToApplicants(evaluatorId, candidateIds);
    }

    @PostMapping("/candidates/marks")
    public ResponseEntity<?> updateAssignedApplicantsMarks(Principal principal, List<MarksDto> marksDtos) {
        return evaluatorService.updateAssignedApplicantsMarks(Long.valueOf(principal.getName()), marksDtos);
    }
}