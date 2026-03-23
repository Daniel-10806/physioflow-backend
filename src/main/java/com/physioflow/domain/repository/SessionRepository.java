package com.physioflow.domain.repository;

import com.physioflow.domain.model.entity.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SessionRepository {

    Session save(Session session);

    List<Session> findByDate(LocalDate date);

    List<Session> findByTherapistAndDate(UUID therapistId, LocalDate date);

    void startSession(UUID sessionId);

    void completeSession(UUID sessionId);
}