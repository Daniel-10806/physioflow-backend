package com.physioflow.application.service;

import com.physioflow.domain.model.enumtype.AuditAction;
import com.physioflow.infrastructure.persistence.entity.AuditLogJpaEntity;
import com.physioflow.infrastructure.persistence.repository.AuditLogJpaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuditLogService {

    private final AuditLogJpaRepository repository;

    public AuditLogService(
            AuditLogJpaRepository repository) {

        this.repository = repository;
    }

    public void log(

            UUID userId,

            AuditAction action,

            String entity,

            String details) {

        AuditLogJpaEntity log = new AuditLogJpaEntity();

        log.setId(UUID.randomUUID());

        log.setUserId(userId);

        log.setAction(action.name());

        log.setEntity(entity);

        log.setDetails(details);

        log.setCreatedAt(
                LocalDateTime.now());

        repository.save(log);
    }
}