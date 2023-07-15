package com.example.tss.repository;

import com.example.tss.entity.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator,Long> {
    List<Evaluator> findByCircularIdAndAssignedRoundId(Long circularId, Long roundId);
}
