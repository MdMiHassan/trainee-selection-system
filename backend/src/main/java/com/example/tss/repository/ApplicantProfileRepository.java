package com.example.tss.repository;

import com.example.tss.entity.ApplicantProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantProfileRepository extends JpaRepository<ApplicantProfile, Long> {
    Optional<ApplicantProfile> findByUserId(Long id);

    Optional<ApplicantProfile> getByUserId(Long id);
}
