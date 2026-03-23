package com.physioflow.application.service;

import com.physioflow.domain.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionWorkflowService {

    private final SessionRepository sessionRepository;

    public SessionWorkflowService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void startSession(UUID sessionId) {

        sessionRepository.startSession(sessionId);

    }

    public void completeSession(UUID sessionId) {

        sessionRepository.completeSession(sessionId);

    }

}