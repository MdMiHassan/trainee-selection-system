package com.example.tss.repository;

import com.example.tss.entity.ScreeningRoundMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundMarkRepository extends JpaRepository<ScreeningRoundMark, Long> {
}
