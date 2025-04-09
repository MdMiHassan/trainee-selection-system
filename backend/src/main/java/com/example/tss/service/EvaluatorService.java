package com.example.tss.service;

import com.example.tss.dto.AssignedApplicantDto;
import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface EvaluatorService {
    EvaluatorDto createEvaluator(EvaluatorDto evaluatorDto);

    List<AssignedApplicantDto> getAllAssignedApplicants(Long evaluatorId);

    ResponseEntity<?> assignEvaluatorToApplicants(Long evaluatorId, Long candidateId, Long roundId);

    ResponseEntity<?> updateAssignedApplicantsMarks(Principal principal, MarksDto marksDto);

    ResponseEntity<?> getAllAssignedApplicants(Principal principal);

    List<EvaluatorDto> getEvaluators();
}
