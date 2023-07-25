package com.example.tss.repository;

import com.example.tss.entity.ScreeningRoundMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreeningRoundMetaRepository extends JpaRepository<ScreeningRoundMeta, Long> {
    Optional<ScreeningRoundMeta> findByCircularId(Long circularId);
}
