package com.example.tss.repository;

import com.example.tss.entity.ScreeningRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRoundRepository extends JpaRepository<ScreeningRound, Long> {
    List<ScreeningRound> findByCircularId(Long circularId);

    Optional<ScreeningRound> findByIdAndCircularId(Long roundId, Long circularId);

    void deleteByIdAndCircularId(Long roundId, Long circularId);

    Optional<ScreeningRound> findByCircularIdAndSerialNo(Long CircularId, int SerialNo);
}
