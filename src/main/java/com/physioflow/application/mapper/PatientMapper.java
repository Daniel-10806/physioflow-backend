package com.physioflow.application.mapper;

import com.physioflow.application.dto.request.CreatePatientRequest;
import com.physioflow.application.dto.response.PatientResponse;
import com.physioflow.domain.model.aggregate.Patient;
import com.physioflow.domain.model.valueobject.Email;

import java.util.UUID;

public class PatientMapper {

    private PatientMapper() {
    }

    public static Patient toDomain(CreatePatientRequest request) {

        return new Patient(
                UUID.randomUUID(),
                request.getFullName(),
                request.getDni(),
                request.getPhone(),
                new Email(request.getEmail()),
                request.getBirthDate(),
                request.getMainDiagnosis(),
                request.getInjuryDate(),
                request.getObservations());
    }

    public static PatientResponse toResponse(Patient patient) {

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