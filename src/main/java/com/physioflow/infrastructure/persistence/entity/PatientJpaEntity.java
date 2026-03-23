package com.physioflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patients")
public class PatientJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    private LocalDate birthDate;

    private String mainDiagnosis;

    private LocalDate injuryDate;

    @Column(length = 2000)
    private String observations;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionJpaEntity> sessions = new ArrayList<>();

    public PatientJpaEntity() {
    }

    public PatientJpaEntity(
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

    public void setId(UUID id) {
        this.id = id;
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