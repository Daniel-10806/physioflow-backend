package com.physioflow.interfaces.rest.controller;

import com.physioflow.infrastructure.persistence.entity.AuditLogJpaEntity;
import com.physioflow.infrastructure.persistence.repository.AuditLogJpaRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditLogController {

    private final AuditLogJpaRepository repository;

    public AuditLogController(
            AuditLogJpaRepository repository) {

        this.repository = repository;
    }

    @GetMapping
    public List<AuditLogJpaEntity> getAll() {

        return repository.findAll();
    }

    @GetMapping("/user/{id}")
    public List<AuditLogJpaEntity> byUser(
            @PathVariable UUID id) {

        return repository.findByUserId(id);
    }
}