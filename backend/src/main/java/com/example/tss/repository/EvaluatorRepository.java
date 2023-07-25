package com.example.tss.repository;

import com.example.tss.entity.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator,Long> {
    List<Evaluator> findByUserId(Long id);

    Optional<Evaluator> findByUserIdAndApplicationId(Long id, long applicationId);

    boolean existsByUserIdAndApplicationIdAndAssignedRoundId(Long userId, Long applicationId, Long assignedRoundId);

    boolean existsByApplicationIdAndAssignedRoundId(Long applicationId, Long assignedRoundId);
}
