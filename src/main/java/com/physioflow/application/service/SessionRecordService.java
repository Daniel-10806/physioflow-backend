package com.physioflow.application.service;

import com.physioflow.application.dto.request.SessionRecordRequest;
import com.physioflow.domain.model.entity.SessionRecord;
import com.physioflow.domain.repository.SessionRecordRepository;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionRecordService {

    private final SessionRecordRepository repository;
    private final SessionJpaRepository sessionJpaRepository;

    public SessionRecordService(
            SessionRecordRepository repository,
            SessionJpaRepository sessionJpaRepository) {
        this.repository = repository;
        this.sessionJpaRepository = sessionJpaRepository;
    }

    public void createRecord(UUID sessionId, SessionRecordRequest request) {

        SessionRecord record = new SessionRecord(
                UUID.randomUUID(),
                sessionId,
                request.subjective(),
                request.objective(),
                request.assessment(),
                request.plan(),
                request.treatment(),
                request.progressNotes(),
                LocalDateTime.now());

        repository.save(record);

        SessionJpaEntity session = sessionJpaRepository.findById(sessionId)
                .orElseThrow();

        session.setStatus("COMPLETED");

        sessionJpaRepository.save(session);

    }
}