package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.infrastructure.persistence.entity.TreatmentPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TreatmentPlanJpaRepository
        extends JpaRepository<TreatmentPlanJpaEntity, UUID> {
}