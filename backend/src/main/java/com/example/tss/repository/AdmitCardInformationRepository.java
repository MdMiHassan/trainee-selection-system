package com.example.tss.repository;

import com.example.tss.entity.AdmitCardInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmitCardInformationRepository extends JpaRepository<AdmitCardInformation, Long> {
    Optional<AdmitCardInformation> findByCircularId(Long id);
}
