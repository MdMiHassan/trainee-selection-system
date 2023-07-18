package com.example.tss.repository;

import com.example.tss.entity.Resource;
import com.example.tss.constants.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByIdAndResourceType(Long id, ResourceType resourceType);

    Optional<Resource> findByIdAndResourceTypeAndOwnerId(Long resourceId, ResourceType resourceType, Long ownerId);

    Optional<Resource> findByIdAndOwnerId(Long id, Long ownerId);
}
