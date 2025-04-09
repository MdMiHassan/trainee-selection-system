package com.example.tss.repository;

import com.example.tss.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCircularId(Long circularId);

    Optional<Application> findByIdAndCircularId(Long applicationId, Long circularId);

    Optional<Application> findAllByUniqueIdentifier(Long candidateUid);

    List<Application> findByCircularIdAndCurrentRoundId(Long circularId, Long roundId);

    Optional<Application> findByCircularIdAndCurrentRoundIdAndApplicantId(Long circularId, Long currentRoundId, Long id);

    List<Application> findByApplicantId(Long applicantId);

    Optional<Application> findByCircularIdAndApplicantId(Long circularId, Long id);

    Optional<Application> findByUniqueIdentifier(long uniqueIdentifier);

    boolean existsByCircularIdAndApplicantId(Long circularId, Long applicantId);
}
