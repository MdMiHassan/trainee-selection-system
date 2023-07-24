package com.example.tss.service.impl;

import com.example.tss.constants.Role;
import com.example.tss.dto.AssignedApplicantDto;
import com.example.tss.dto.EvaluatorDto;
import com.example.tss.dto.MarksDto;
import com.example.tss.dto.ScreeningRoundDto;
import com.example.tss.entity.*;
import com.example.tss.repository.*;
import com.example.tss.service.EvaluatorService;
import com.example.tss.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
//        Circular circularById = circularRepository.findById(evaluatorDto.getCircularId())
//                .orElseThrow();
//        ScreeningRound screeningRound = screeningRoundRepository.findById(evaluatorDto.getRoundId())
//                .orElseThrow();
//        ModelMapper modelMapper = new ModelMapper();
//        Evaluator evaluator = modelMapper.map(evaluatorDto, Evaluator.class);
//        evaluator.setUser(savedEvaluatorUser);
//        evaluator.setCircular(circularById);
//        evaluator.setAssignedRound(screeningRound);
//        List<Long> evaluatorDtoApplications = evaluatorDto.getApplications();
//        List<Application> applicationList = applicationRepository.findAllById(evaluatorDtoApplications);
//        evaluator.setApplications(applicationList);
//        Evaluator savedEvaluator = evaluatorRepository.save(evaluator);
//        return ResponseEntity.ok(savedEvaluator);
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
//                            .markUpdatable(assignedRound == screeningRoundMeta.getCurrentRound())
                                    .candidatesUid(application.getId() + 1000)
                                    .build();
                            return assignedApplicantDto;
                        }
                ).toList();

        return ResponseEntity.ok(assigned);
    }

    @Override
    @Transactional
    public ResponseEntity<?> assignEvaluatorToApplicants(Long evaluatorId, Long candidateId) {
        System.out.println("After -2");
        User user = userService.getById(evaluatorId).orElseThrow();
        System.out.println("After -1");
        Application application = applicationRepository.findById(candidateId).orElseThrow();
        Circular circular = application.getCircular();
        System.out.println("After 1");
        ScreeningRoundMeta screeningRoundMeta = screeningRoundMetaRepository.findByCircularId(circular.getId()).orElseThrow();
        ScreeningRound applicationCurrentRound = application.getCurrentRound();
        ScreeningRound circularCurrentRound = screeningRoundMeta.getCurrentRound();
        System.out.println("After 2");
        if (circularCurrentRound.getSerialNo().intValue() != applicationCurrentRound.getSerialNo().intValue()) {
            System.out.println("Current round "+circularCurrentRound.getSerialNo().intValue()+" app round"+ applicationCurrentRound.getSerialNo().intValue());
            return ResponseEntity.badRequest().build();
        }
        System.out.println("After 3");
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
        User user = userService.getUserByPrincipal(principal).orElseThrow();
        long applicationId = marksDto.getCandidateUid() - 1000;
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        Double totalMarks = marksDto.getTotalMarks();
        Evaluator evaluator = evaluatorRepository.findByUserIdAndApplicationId(user.getId(),
                applicationId).orElseThrow();
        ScreeningRound assignedRound = evaluator.getAssignedRound();
        Optional<ScreeningRoundMark> screeningRoundMarkop = screeningMarksRepository.findByApplicationIdAndRoundId(application.getId(), assignedRound.getId());
        if (screeningRoundMarkop.isEmpty()) {
            ScreeningRoundMark screeningRoundMark = ScreeningRoundMark.builder()
                    .application(application)
                    .round(assignedRound)
                    .mark(totalMarks).build();
            screeningMarksRepository.save(screeningRoundMark);
        } else {
            ScreeningRoundMark screeningRoundMark = screeningRoundMarkop.get();
            screeningRoundMark.setMark(totalMarks);
            screeningMarksRepository.save(screeningRoundMark);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getAllEvaluatorsUnderRoundUnderCircular(Long circularId, Long roundId) {
        List<Evaluator> evaluators = evaluatorRepository.findByCircularIdAndAssignedRoundId(circularId, roundId);
        return ResponseEntity.ok(evaluators);
    }

    @Override
    public ResponseEntity<?> getAllAssignedApplicants(Principal principal) {
        User user=userService.getUserByPrincipal(principal).orElseThrow();
        List<AssignedApplicantDto> assigned = evaluatorRepository.findByUserId(user.getId()).stream()
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
//                            .markUpdatable(assignedRound == screeningRoundMeta.getCurrentRound())
                                    .candidatesUid(application.getId() + 1000)
                                    .build();
                            return assignedApplicantDto;
                        }
                ).toList();

        return ResponseEntity.ok(assigned);
    }

    @Override
    public ResponseEntity<?> getEvaluators() {
        List<User> evaluators= userService.getAllEvaluators();
        List<EvaluatorDto> evaluatorDto = evaluators.stream().map(evaluator -> EvaluatorDto.builder()
                .email(evaluator.getEmail())
                .id(evaluator.getId())
                .build()).toList();
        return ResponseEntity.ok(evaluatorDto);
    }
}
