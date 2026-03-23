package com.physioflow.domain.model.entity;

import com.physioflow.domain.model.exception.DomainException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TreatmentPlan {

    private final UUID id;
    private final List<Exercise> exercises;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private UUID patientId;

    public TreatmentPlan(
            UUID id,
            UUID patientId,
            List<Exercise> exercises,
            LocalDate startDate,
            LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new DomainException("La fecha de finalización no puede ser anterior a la fecha de inicio");
        }

        this.id = id;
        this.patientId = patientId;
        this.exercises = exercises;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public List<Exercise> getExercises() {
        return List.copyOf(exercises);
    }

    public UUID getPatientId() {
        return patientId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}