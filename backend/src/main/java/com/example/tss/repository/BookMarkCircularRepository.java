package com.example.tss.repository;

import com.example.tss.entity.BookMarkCircular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookMarkCircularRepository extends JpaRepository<BookMarkCircular, Long> {

    Optional<BookMarkCircular> findByUserIdAndCircularId(Long userId, Long circularId);
}
