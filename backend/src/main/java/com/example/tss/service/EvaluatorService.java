package com.example.tss.service;

import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EvaluatorService {
    ResponseEntity<?> createEvaluator(EvaluatorDto evaluatorDto);

    ResponseEntity<?> getAllAssignedApplicants(Long evaluatorId);

    ResponseEntity<?> assignEvaluatorToApplicants(Long evaluatorId, List<Long> candidateIds);

    ResponseEntity<?> updateAssignedApplicantsMarks(Long aLong, List<MarksDto> marksDtos);

    ResponseEntity<?> getAllEvaluatorsUnderRoundUnderCircular(Long circularId, Long roundId);
}
