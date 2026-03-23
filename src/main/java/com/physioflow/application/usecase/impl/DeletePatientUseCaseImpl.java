package com.physioflow.application.usecase.impl;

import com.physioflow.application.usecase.DeletePatientUseCase;
import com.physioflow.domain.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletePatientUseCaseImpl
        implements DeletePatientUseCase {

    private final PatientRepository patientRepository;

    public DeletePatientUseCaseImpl(
            PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    @Override
    public void delete(UUID id) {

        patientRepository.deleteById(id);

    }
}