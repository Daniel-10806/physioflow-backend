package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.infrastructure.persistence.entity.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogJpaRepository
        extends JpaRepository<AuditLogJpaEntity, UUID> {

    List<AuditLogJpaEntity> findByUserId(UUID userId);

}