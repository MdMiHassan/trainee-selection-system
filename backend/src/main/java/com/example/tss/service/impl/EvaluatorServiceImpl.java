package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.AssignedApplicantDto;
import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.entity.*;
import com.example.tss.exception.ApplicantMarkUpdateFailedException;
import com.example.tss.exception.EvaluatorAssigningFailedException;
import com.example.tss.repository.ApplicationRepository;
import com.example.tss.repository.EvaluatorRepository;
import com.example.tss.repository.ScreeningMarksRepository;
import com.example.tss.repository.ScreeningRoundMetaRepository;
import com.example.tss.service.EvaluatorService;
import com.example.tss.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {
    private final EvaluatorRepository evaluatorRepository;
    private final UserService userService;
    private final ApplicationRepository applicationRepository;
    private final ScreeningRoundMetaRepository screeningRoundMetaRepository;
    private final ScreeningMarksRepository screeningMarksRepository;

    @Override
    public ResponseEntity<?> createEvaluator(EvaluatorDto evaluatorDto) {
        String email = evaluatorDto.getEmail();
        Optional<User> userOptional = userService.getByEmail(email);
        if (userOptional.isEmpty()) {
            User evaluatorUser = User.builder()
                    .email(email)
                    .password(evaluatorDto.getPassword())
                    .expiredAt(evaluatorDto.getExpireAt())
                    .locked(false)
                    .role(Role.EVALUATOR)
                    .enabled(true)
                    .build();
            User savedEvaluatorUser = userService.save(evaluatorUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllAssignedApplicants(Long evaluatorId) {
        List<AssignedApplicantDto> assigned = evaluatorRepository.findByUserId(evaluatorId).stream()
                .map(evaluator -> {
                            Application application = evaluator.getApplication();
                            ScreeningRound assignedRound = evaluator.getAssignedRound();
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
                                    .candidatesUid(application.getId() + 1000)
                                    .build();
                            return assignedApplicantDto;
                        }
                ).toList();

        return ResponseEntity.ok(assigned);
    }

    @Override
    @Transactional
    public ResponseEntity<?> assignEvaluatorToApplicants(Long evaluatorId, Long candidateId, Long assignedRoundId) {
        User user = userService.getById(evaluatorId).orElseThrow();
        Application application = applicationRepository.findById(candidateId).orElseThrow(EvaluatorAssigningFailedException::new);
        Circular circular = application.getCircular();
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circular.getId()).orElseThrow();
        if (evaluatorRepository.existsByApplicationIdAndAssignedRoundId(application.getId(), assignedRoundId)) {
            throw new EvaluatorAssigningFailedException();
        }
        if (evaluatorRepository.existsByUserIdAndApplicationIdAndAssignedRoundId(user.getId(), application.getId(), assignedRoundId)) {
            throw new EvaluatorAssigningFailedException();
        }
        ScreeningRound applicationCurrentRound = application.getCurrentRound();
        ScreeningRound circularCurrentRound = screeningRoundMeta.getCurrentRound();
//        if (circularCurrentRound.getSerialNo().intValue() != applicationCurrentRound.getSerialNo().intValue()) {
//            return ResponseEntity.badRequest().build();
//        }
        Evaluator evaluator = Evaluator.builder()
                .user(user)
                .application(application)
                .assignedRound(applicationCurrentRound)
                .build();
        evaluatorRepository.save(evaluator);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> updateAssignedApplicantsMarks(Principal principal, MarksDto marksDto) {
        User user = userService.getUserByPrincipal(principal)
                .orElseThrow(ApplicantMarkUpdateFailedException::new);
        long applicationUid = marksDto.getCandidateUid();
        Application application = applicationRepository.findByUniqueIdentifier(applicationUid)
                .orElseThrow(ApplicantMarkUpdateFailedException::new);
        Double totalMarks = marksDto.getTotalMarks();
        long applicationId = application.getId();
        Evaluator evaluator = evaluatorRepository.findByUserIdAndApplicationId(user.getId(), applicationId)
                .orElseThrow(ApplicantMarkUpdateFailedException::new);
        Circular circular = application.getCircular();
        ScreeningRoundMeta circularMeta = screeningRoundMetaRepository.findByCircularId(circular.getId())
                .orElseThrow(ApplicantMarkUpdateFailedException::new);
        ScreeningRound circularCurrentRound = circularMeta.getCurrentRound();
        ScreeningRound assignedRound = evaluator.getAssignedRound();
        if (circularCurrentRound.getSerialNo().intValue() != assignedRound.getSerialNo().intValue()) {
            throw new ApplicantMarkUpdateFailedException();
        }
        Optional<ScreeningRoundMark> screeningRoundMarkOp = screeningMarksRepository.findByApplicationIdAndRoundId(applicationId, assignedRound.getId());
        ScreeningRoundMark screeningRoundMark;
        if (screeningRoundMarkOp.isEmpty()) {
            screeningRoundMark = ScreeningRoundMark.builder()
                    .application(application)
                    .round(assignedRound)
                    .mark(totalMarks).build();
        } else {
            screeningRoundMark = screeningRoundMarkOp.get();
            screeningRoundMark.setMark(totalMarks);
        }
        screeningMarksRepository.save(screeningRoundMark);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getAllAssignedApplicants(Principal principal) {
        User user = userService.getUserByPrincipal(principal).orElseThrow();
        List<AssignedApplicantDto> assignedEvaluation = evaluatorRepository.findByUserId(user.getId()).stream()
                .filter(evaluator -> {
                    Application application = evaluator.getApplication();
                    Circular circular = application.getCircular();
                    ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circular.getId()).orElseThrow();
                    ScreeningRound currentRound = screeningRoundMeta.getCurrentRound();
                    ScreeningRound assignedRound = evaluator.getAssignedRound();
                    return currentRound.getSerialNo().intValue() == assignedRound.getSerialNo().intValue();
                })
                .map(evaluator -> {
                    Application application = evaluator.getApplication();
                    ScreeningRound assignedRound = evaluator.getAssignedRound();
                    Optional<ScreeningRoundMark> roundMarkOp = screeningMarksRepository.findByApplicationIdAndRoundId(application.getId(), assignedRound.getId());
                    ScreeningRoundDto screeningRoundDto = ScreeningRoundDto.builder()
                            .roundId(assignedRound.getId())
                            .circularId(assignedRound.getCircular().getId())
                            .title(assignedRound.getTitle())
                            .description(assignedRound.getDescription())
                            .maxMark(assignedRound.getMaxMark())
                            .passMark(assignedRound.getPassMark())
                            .build();

                    AssignedApplicantDto assignedApplicantDto = AssignedApplicantDto.builder()
                            .screeningRound(screeningRoundDto)
                            .candidatesUid(application.getId()+1000)
                            .build();

                    if (roundMarkOp.isPresent()) {
                        ScreeningRoundMark screeningRoundMark = roundMarkOp.get();
                        assignedApplicantDto.setMark(screeningRoundMark.getMark());
                    }

                    return assignedApplicantDto;
                })
                .toList();
        return ResponseEntity.ok(assignedEvaluation);
    }

    @Override
    public ResponseEntity<?> getEvaluators() {
        List<User> evaluators = userService.getAllEvaluators();
        List<EvaluatorDto> evaluatorDto = evaluators.stream().map(evaluator -> EvaluatorDto.builder()
                .email(evaluator.getEmail())
                .id(evaluator.getId())
                .build()).toList();
        return ResponseEntity.ok(evaluatorDto);
    }
}
