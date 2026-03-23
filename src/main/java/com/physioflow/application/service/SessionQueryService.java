package com.physioflow.application.service;

import com.physioflow.application.dto.response.SessionResponse;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionQueryService {

    private final SessionJpaRepository repository;

    public SessionQueryService(SessionJpaRepository repository) {
        this.repository = repository;
    }

    public SessionResponse getSession(UUID id) {
        return repository.findSessionById(id);
    }
}