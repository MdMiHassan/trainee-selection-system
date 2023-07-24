package com.example.tss.service;

import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.entity.Circular;
import org.springframework.http.ResponseEntity;

public interface RoundService {
    ResponseEntity<?> getAllRoundsUnderCircular(Long circularId);

    ResponseEntity<?> getRoundByIdUnderCircular(Long circularId, Long roundId);

    ResponseEntity<?> updateRound(Long circularId, Long roundId, ScreeningRoundDto screeningRoundDto);

    ResponseEntity<?> deleteRoundByIdUnderCircular(Long circularId, Long roundId);

    ResponseEntity<?> approveApplicant(Circular circular, Long applicationId);

    ResponseEntity<?> endRound(Long circularId);

    ResponseEntity<?> getAllCandidatesUnderRoundUnderCircular(Long circularId, Long roundId);


    ResponseEntity<?> storeRound(Long circularId, ScreeningRoundDto screeningRoundDto);

    ResponseEntity<?> saveCandidateMark(Long circularId, Long roundId, Long candidateId, Float mark);
}
