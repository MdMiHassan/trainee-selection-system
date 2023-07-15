package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.CircularDto;
import com.example.tss.entity.Circular;
import com.example.tss.entity.ScreeningRoundMeta;
import com.example.tss.entity.ScreeningRound;
import com.example.tss.repository.CircularRepository;
import com.example.tss.repository.ScreeningRoundMetaRepository;
import com.example.tss.repository.ScreeningRoundRepository;
import com.example.tss.service.ApplicationService;
import com.example.tss.service.CircularService;
import com.example.tss.service.RoundService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CircularServiceImpl implements CircularService {
    private final CircularRepository circularRepository;
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;
    private final RoundService roundService;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningRoundRepository screeningRoundRepository;

    public Page<?> getAllCircular(Pageable pageable) {
        return circularRepository.findAll(pageable);
    }

    public ResponseEntity<?> createCircular(CircularDto circularDto) {
        Circular savedCircular = circularRepository.save(modelMapper.map(circularDto, Circular.class));
        ScreeningRound initialScreeningRound=ScreeningRound.builder()
                .title("Apply")
                .circular(savedCircular)
                .serialNo(1)
                .description("Applicant need to submit application first")
                .build();
        ScreeningRound savedScreeningRound = screeningRoundRepository.save(initialScreeningRound);
        ScreeningRoundMeta screeningRoundMeta = ScreeningRoundMeta.builder()
                .circular(savedCircular)
                .currentRound(savedScreeningRound)
                .currentRoundEnd(true)
                .nextRound(savedScreeningRound)
                .build();
        ScreeningRoundMeta savedScreeningRoundMeta = screeningRoundMetaRepository.save(screeningRoundMeta);
        return ResponseEntity.ok(savedScreeningRoundMeta);
    }

    public ResponseEntity<?> getCircularById(Long id) {
        Optional<Circular> circularById = circularRepository.findById(id);
        return circularById
                .map(circular -> ResponseEntity.ok().body(circular))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> updateCircularById(Long id, CircularDto circularDto) {
        Circular existingCircular = circularRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Circular not found with id: " + id));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(circularDto, existingCircular);
        Circular savedCircular = circularRepository.save(existingCircular);
        return ResponseEntity.ok().body(modelMapper.map(savedCircular, CircularDto.class));
    }

    public ResponseEntity<?> getAllApplicationsUnderCircular(Long circularId) {
        return applicationService.getAllApplicationsUnderCircular(circularId);
    }

    public ResponseEntity<?> delete(Long id) {
        Circular circular = circularRepository.findById(id).orElseThrow();
        circularRepository.delete(circular);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> apply(Long circularId, ApplicantProfileDto applicantProfileDto) {
        return applicationService.apply(circularId, applicantProfileDto);
    }

    @Override
    public ResponseEntity<?> getApplicationByIdUnderCircular(Long circularId, Long applicationId) {
        return applicationService.getApplicationByIdUnderCircular(circularId, applicationId);
    }

    @Override
    public ResponseEntity<?> approveApplicant(Long circularId, Long applicationId) {
        Circular circular = circularRepository.findById(circularId).orElseThrow();
        return roundService.approveApplicant(circular,applicationId);
    }

}
