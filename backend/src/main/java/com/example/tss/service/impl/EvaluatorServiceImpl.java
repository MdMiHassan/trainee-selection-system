package com.example.tss.service.impl;

import com.example.tss.dto.AssignedApplicantDto;
import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.entity.*;
import com.example.tss.repository.*;
import com.example.tss.service.EvaluatorService;
import com.example.tss.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {
    private final EvaluatorRepository evaluatorRepository;
    private final UserService userService;
    private final CircularRepository circularRepository;
    private final ScreeningRoundRepository screeningRoundRepository;
    private final ApplicationRepository applicationRepository;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningMarksRepository screeningMarksRepository;

    @Override
    public ResponseEntity<?> createEvaluator(EvaluatorDto evaluatorDto) {
        User evaluatorUser = User.builder()
                .email(evaluatorDto.getEmail())
                .password(evaluatorDto.getPassword())
                .expiredAt(evaluatorDto.getExpireAt())
                .locked(false)
                .enabled(true)
                .build();
        User savedEvaluatorUser = userService.save(evaluatorUser);
        Circular circularById = circularRepository.findById(evaluatorDto.getCircularId())
                .orElseThrow();
        ScreeningRound screeningRound = screeningRoundRepository.findById(evaluatorDto.getRoundId())
                .orElseThrow();
        ModelMapper modelMapper = new ModelMapper();
        Evaluator evaluator = modelMapper.map(evaluatorDto, Evaluator.class);
        evaluator.setUser(savedEvaluatorUser);
        evaluator.setCircular(circularById);
        evaluator.setAssignedRound(screeningRound);
        List<Long> evaluatorDtoApplications = evaluatorDto.getApplications();
        List<Application> applicationList = applicationRepository.findAllById(evaluatorDtoApplications);
        evaluator.setApplications(applicationList);
        Evaluator savedEvaluator = evaluatorRepository.save(evaluator);
        return ResponseEntity.ok(savedEvaluator);
    }

    @Override
    public ResponseEntity<?> getAllAssignedApplicants(Long evaluatorId) {
        Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow();
        ScreeningRound assignedRound = evaluator.getAssignedRound();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(evaluator.getCircular().getId())
                .orElseThrow();
        List<Long> assignedApplicationUids = evaluator.getApplications().stream()
                .map(Application::getUniqueIdentifier)
                .toList();
        ScreeningRoundDto screeningRoundDto = ScreeningRoundDto.builder()
                .roundId(assignedRound.getId())
                .circularId(assignedRound.getCircular().getId())
                .title(assignedRound.getTitle())
                .description(assignedRound.getTitle())
                .maxMark(assignedRound.getMaxMark())
                .passMark(assignedRound.getPassMark())
                .build();
        AssignedApplicantDto assignedApplicantDto = AssignedApplicantDto.builder()
                .screeningRound(screeningRoundDto)
                .markUpdatable(assignedRound == screeningRoundMeta.getCurrentRound())
                .candidatesUid(assignedApplicationUids)
                .build();
        return ResponseEntity.ok(assignedApplicantDto);
    }

    @Override
    public ResponseEntity<?> assignEvaluatorToApplicants(Long evaluatorId, List<Long> candidateIds) {
        Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow();
        Set<Application> assignedApplications = new HashSet<>(evaluator.getApplications());
        assignedApplications.addAll(applicationRepository.findAllById(candidateIds));
        evaluator.setApplications(assignedApplications.stream().toList());
        evaluatorRepository.save(evaluator);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updateAssignedApplicantsMarks(Long evaluatorId, List<MarksDto> marksDtos) {

        Evaluator evaluator = evaluatorRepository.findById(evaluatorId).orElseThrow();
        Circular assignedCircular = evaluator.getCircular();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(assignedCircular.getId()).orElseThrow();
        ScreeningRound currentRound = screeningRoundMeta.getCurrentRound();
        ScreeningRound assignedRound = evaluator.getAssignedRound();
        if (currentRound.getId() != assignedRound.getId()) {
            return ResponseEntity.badRequest().build();
        }
        Set<Long> assignedUid = evaluator.getApplications().stream().map(Application::getUniqueIdentifier).collect(Collectors.toSet());
        for (MarksDto marksDto : marksDtos) {
            Long candidateUid = marksDto.getCandidateUid();
            Long circularId = marksDto.getCircularId();
            if (assignedUid.contains(candidateUid) && circularId == assignedCircular.getId()) {
                Double totalMarks = marksDto.getTotalMarks();
                Application application = applicationRepository.findAllByUniqueIdentifier(candidateUid).orElseThrow();
                ScreeningRoundMark screeningRoundMark = ScreeningRoundMark.builder()
                        .round(assignedRound)
                        .application(application)
                        .mark(totalMarks)
                        .build();
                screeningMarksRepository.save(screeningRoundMark);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getAllEvaluatorsUnderRoundUnderCircular(Long circularId, Long roundId) {
        List<EvaluatorDto> evaluatorDtos = evaluatorRepository.findByCircularIdAndAssignedRoundId(circularId, roundId).stream()
                .map(evaluator ->
                        EvaluatorDto.builder()
                                .roundId(evaluator.getId())
                                .firstName(evaluator.getFirstName())
                                .lastName(evaluator.getLastName())
                                .phone(evaluator.getPhone())
                                .empId(evaluator.getEmpId())
                                .division(evaluator.getDivision())
                                .designation(evaluator.getDesignation())
                                .build()
                ).toList();
        return ResponseEntity.ok(evaluatorDtos);
    }

    @Override
    public ResponseEntity<?> getAllAssignedApplicants(Principal principal) {
//        User user=userService.getUserByPrincipal(principal).orElseThrow();
//        List<Evaluator> evaluators=evaluatorRepository.findByUserId(user.getId()).stream()
//                .map(evaluator -> {
//                    Circular circular = evaluator.getCircular();
//                    List<Long> uids = evaluator.getApplications().stream()
//                            .map(Application::getUniqueIdentifier).toList();
//
//                })
//                .toList();

        return ResponseEntity.ok("Ok");
    }
}
