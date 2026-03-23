package com.physioflow.application.service;

import com.physioflow.application.dto.response.PatientHistoryResponse;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientHistoryService {

    private final SessionRecordJpaRepository repository;

    public PatientHistoryService(SessionRecordJpaRepository repository) {
        this.repository = repository;
    }

    public List<PatientHistoryResponse> getPatientHistory(UUID patientId) {
        return repository.findPatientHistory(patientId);
    }
}