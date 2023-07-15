package com.example.tss.service.impl;

import com.example.tss.dto.ApplicantProfileDto;
import com.example.tss.dto.ApplicationDto;
import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.dto.ScreeningRoundMarkDto;
import com.example.tss.entity.*;
import com.example.tss.repository.*;
import com.example.tss.service.EvaluatorService;
import com.example.tss.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundServiceImpl implements RoundService {
    private final ScreeningRoundRepository screeningRoundRepository;
    private final CircularRepository circularRepository;
    private final ApplicationRepository applicationRepository;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningMarksRepository screeningMarksRepository;
    private final EvaluatorService evaluatorService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> getAllRoundsUnderCircular(Long circularId) {
        ModelMapper modelMapper = new ModelMapper();
        List<ScreeningRoundDto> roundDtos = screeningRoundRepository.findByCircularId(circularId).stream()
                .map(screeningRound -> modelMapper
                        .map(screeningRound, ScreeningRoundDto.class))
                .toList();
        return ResponseEntity.ok(roundDtos);
    }

    @Override
    public ResponseEntity<?> getRoundByIdUnderCircular(Long circularId, Long roundId) {
        ModelMapper modelMapper = new ModelMapper();
        ScreeningRound screeningRound = screeningRoundRepository.findByIdAndCircularId(roundId, circularId)
                .orElseThrow();
        ScreeningRoundDto screeningRoundDto = modelMapper.map(screeningRound, ScreeningRoundDto.class);
        return ResponseEntity.ok(screeningRoundDto);
    }

    @Override
    public ResponseEntity<?> createRound(Long circularId, ScreeningRoundDto screeningRoundDto) {
        ModelMapper modelMapper = new ModelMapper();
        Circular circular = circularRepository.findById(circularId).orElseThrow();
        ScreeningRound screeningRound = modelMapper.map(screeningRoundDto, ScreeningRound.class);
        screeningRound.setCircular(circular);
        ScreeningRound savedScreeningRound = screeningRoundRepository.save(screeningRound);
        return ResponseEntity.ok(screeningRoundDto);
    }

    @Override
    public ResponseEntity<?> updateRound(Long circularId, Long roundId, ScreeningRoundDto screeningRoundDto) {
        ScreeningRound screeningRound = screeningRoundRepository.findByIdAndCircularId(roundId, circularId).orElseThrow();
        ModelMapper modelMapper = new ModelMapper();
        ScreeningRound newScreeningRound = modelMapper.map(screeningRoundDto, ScreeningRound.class);
        modelMapper.map(newScreeningRound, screeningRound);
        ScreeningRound updatedScreeningRound = screeningRoundRepository.save(screeningRound);
        return ResponseEntity.ok(updatedScreeningRound);
    }

    @Override
    public ResponseEntity<?> deleteRoundByIdUnderCircular(Long circularId, Long roundId) {
        screeningRoundRepository.findByIdAndCircularId(roundId, circularId).orElseThrow();
        screeningRoundRepository.deleteByIdAndCircularId(roundId, circularId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> approveApplicant(Circular circular, Long applicationId) {
        Application application = applicationRepository.findByIdAndCircularId(applicationId, circular.getId())
                .orElseThrow();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularIdAndCurrentRoundEnd(circular.getId(), true)
                .orElseThrow();
        application.setCurrentRound(screeningRoundMeta.getNextRound());
        Application savedApplication = applicationRepository.save(application);
        return ResponseEntity.ok(savedApplication);
    }

    @Override
    public ResponseEntity<?> endRound(Long circularId) {
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circularId).orElseThrow();
        screeningRoundMeta.setCurrentRoundEnd(true);
        screeningRoundMetaRepository.save(screeningRoundMeta);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getAllCandidatesUnderRoundUnderCircular(Long circularId, Long roundId) {
        List<Application> applications = applicationRepository.findByCircularIdAndCurrentRoundId(circularId, roundId);

        List<ApplicationDto> applicationDtos = applications.stream()
                .map(application -> {
                    ApplicantProfileDto applicantProfileDto = modelMapper.map(application,ApplicantProfileDto.class);
                    List<ScreeningRoundMarkDto> screeningRoundMarkDto = screeningMarksRepository.findByApplicationId(application.getId()).stream()
                            .map(screeningRoundMark->{
                                return ScreeningRoundMarkDto.builder()
                                        .roundId(screeningRoundMark.getScreeningRound().getId())
                                        .title(screeningRoundMark.getScreeningRound().getTitle())
                                        .mark(screeningRoundMark.getMark())
                                        .build();
                            }).toList();

                    return ApplicationDto.builder()
                            .applicantProfile(applicantProfileDto)
                            .roundMarks(screeningRoundMarkDto)
                            .build();
                }).toList();
        return ResponseEntity.ok(applicationDtos);
    }

    @Override
    public ResponseEntity<?> getAllEvaluatorsUnderRoundUnderCircular(Long circularId, Long roundId) {
        return evaluatorService.getAllEvaluatorsUnderRoundUnderCircular(circularId,roundId);
    }
}
