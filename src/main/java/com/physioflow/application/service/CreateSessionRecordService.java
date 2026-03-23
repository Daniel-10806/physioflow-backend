package com.physioflow.application.service;

import com.physioflow.application.dto.request.CreateSessionRecordRequest;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import com.physioflow.infrastructure.persistence.entity.SessionRecordJpaEntity;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateSessionRecordService {

    private final SessionRecordJpaRepository recordRepository;
    private final SessionJpaRepository sessionRepository;

    public CreateSessionRecordService(
            SessionRecordJpaRepository recordRepository,
            SessionJpaRepository sessionRepository) {

        this.recordRepository = recordRepository;
        this.sessionRepository = sessionRepository;
    }

    public void createRecord(
            UUID sessionId,
            CreateSessionRecordRequest request) {

        // =========================
        // obtener sesión
        // =========================

        SessionJpaEntity session = sessionRepository.findById(sessionId)
                .orElseThrow();

        // =========================
        // crear record
        // =========================

        SessionRecordJpaEntity record = new SessionRecordJpaEntity();

        record.setId(UUID.randomUUID());

        record.setSession(session);

        record.setSubjective(
                request.getSubjective());

        record.setObjective(
                request.getObjective());

        record.setAssessment(
                request.getAssessment());

        record.setTreatment(
                request.getTreatment());

        record.setProgressNotes(
                request.getProgressNotes());

        record.setPainLevel(
                request.getPainLevel());

        record.setMobilityLevel(
                request.getMobilityLevel());

        record.setStrengthLevel(
                request.getStrengthLevel());

        record.setCreatedAt(
                LocalDateTime.now());

        recordRepository.save(record);

        // =========================
        // cambiar status
        // =========================

        session.setStatus("COMPLETED");

        sessionRepository.save(session);

    }
}