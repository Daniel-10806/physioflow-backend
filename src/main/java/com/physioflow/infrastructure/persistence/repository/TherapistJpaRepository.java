package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.infrastructure.persistence.entity.TherapistJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TherapistJpaRepository
        extends JpaRepository<TherapistJpaEntity, UUID> {

    Optional<TherapistJpaEntity> findByEmail(String email);
}