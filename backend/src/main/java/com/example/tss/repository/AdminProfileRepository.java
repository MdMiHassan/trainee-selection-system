package com.example.tss.repository;

import com.example.tss.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminProfileRepository extends JpaRepository<AdminProfile,Long> {
    Optional<AdminProfile> findByUserId(Long id);
}
