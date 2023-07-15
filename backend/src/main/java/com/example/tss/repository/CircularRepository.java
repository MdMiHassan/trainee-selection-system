package com.example.tss.repository;

import com.example.tss.entity.Circular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularRepository extends JpaRepository<Circular,Long> {
}
