package com.example.tss.repository;

import com.example.tss.entity.CircularBookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CircularBookMarkRepository extends JpaRepository<CircularBookMark, Long> {
    Optional<CircularBookMark> findByUserIdAndCircularId(Long userId, Long circularId);
}
