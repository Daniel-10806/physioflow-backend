package com.physioflow.application.usecase.impl;

import com.physioflow.application.dto.response.PatientResponse;
import com.physioflow.application.usecase.GetPatientByIdUseCase;
import com.physioflow.domain.model.aggregate.Patient;
import com.physioflow.domain.repository.PatientRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPatientByIdUseCaseImpl
        implements GetPatientByIdUseCase {

    private final PatientRepository patientRepository;

    public GetPatientByIdUseCaseImpl(
            PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    @Override
    public PatientResponse execute(UUID id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        return new PatientResponse(
                patient.getId(),
                patient.getFullName(),
                patient.getDni(),
                patient.getPhone(),
                patient.getEmail().getValue(),
                patient.getBirthDate(),
                patient.getMainDiagnosis(),
                patient.getInjuryDate(),
                patient.getObservations(),
                patient.getCreatedAt());
    }
}