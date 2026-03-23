package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.infrastructure.persistence.entity.EvaluationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EvaluationJpaRepository
        extends JpaRepository<EvaluationJpaEntity, UUID> {

    Optional<EvaluationJpaEntity> findTopByPatient_IdOrderByEvaluationDateDesc(UUID patientId);

    List<EvaluationJpaEntity> findByPatientId(UUID patientId);
}