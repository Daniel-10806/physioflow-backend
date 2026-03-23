package com.physioflow.domain.model.aggregate;

import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.entity.TreatmentPlan;
import com.physioflow.domain.model.exception.DomainException;
import com.physioflow.domain.model.valueobject.Email;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Patient {

    private final UUID id;

    private String fullName;
    private String dni;
    private String phone;
    private Email email;
    private LocalDate birthDate;

    private String mainDiagnosis;
    private LocalDate injuryDate;
    private String observations;

    private final LocalDateTime createdAt;

    private final List<Evaluation> evaluations = new ArrayList<>();
    private final List<TreatmentPlan> treatmentPlans = new ArrayList<>();

    public Patient(
            UUID id,
            String fullName,
            String dni,
            String phone,
            Email email,
            LocalDate birthDate,
            String mainDiagnosis,
            LocalDate injuryDate,
            String observations) {

        if (fullName == null || fullName.isBlank()) {
            throw new DomainException("El nombre del paciente es obligatorio");
        }

        if (dni == null || dni.isBlank()) {
            throw new DomainException("El DNI es obligatorio");
        }

        this.id = id;
        this.fullName = fullName;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.mainDiagnosis = mainDiagnosis;
        this.injuryDate = injuryDate;
        this.observations = observations;
        this.createdAt = LocalDateTime.now();
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

    public Email getEmail() {
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

    public List<Evaluation> getEvaluations() {
        return List.copyOf(evaluations);
    }
}