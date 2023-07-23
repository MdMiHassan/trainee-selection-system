package com.example.tss.repository;

import com.example.tss.entity.ScreeningRoundMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningMarksRepository extends JpaRepository<ScreeningRoundMark,Long> {
    List<ScreeningRoundMark> findByApplicationId(Long id);

    Optional<ScreeningRoundMark> findByApplicationIdAndRoundId(Long applicationId,Long roundId);
}
