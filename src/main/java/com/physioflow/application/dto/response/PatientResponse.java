package com.physioflow.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class PatientResponse {

    private UUID id;
    private String fullName;
    private String dni;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String mainDiagnosis;
    private LocalDate injuryDate;
    private String observations;
    private LocalDateTime createdAt;

    public PatientResponse(
            UUID id,
            String fullName,
            String dni,
            String phone,
            String email,
            LocalDate birthDate,
            String mainDiagnosis,
            LocalDate injuryDate,
            String observations,
            LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.mainDiagnosis = mainDiagnosis;
        this.injuryDate = injuryDate;
        this.observations = observations;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDni() {
        return dni;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getMainDiagnosis() {
        return mainDiagnosis;
    }

    public LocalDate getInjuryDate() {
        return injuryDate;
    }

    public String getObservations() {
        return observations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}