package com.physioflow.application.usecase.impl;

import com.physioflow.application.dto.response.PatientEvolutionResponse;
import com.physioflow.application.usecase.GetPatientEvolutionUseCase;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetPatientEvolutionUseCaseImpl
        implements GetPatientEvolutionUseCase {

    private final SessionRecordJpaRepository repository;

    public GetPatientEvolutionUseCaseImpl(
            SessionRecordJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PatientEvolutionResponse> execute(
            UUID patientId) {

        return repository.findEvolution(patientId);

    }
}