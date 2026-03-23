package com.physioflow.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Session {

    private final UUID id;
    private final UUID patientId;
    private final UUID therapistId;
    private final LocalDate sessionDate;
    private final LocalTime sessionTime;
    private final int durationMinutes;
    private final String type;
    private final String status;

    public Session(
            UUID id,
            UUID patientId,
            UUID therapistId,
            LocalDate sessionDate,
            LocalTime sessionTime,
            int durationMinutes,
            String type,
            String status) {
        this.id = id;
        this.patientId = patientId;
        this.therapistId = therapistId;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.durationMinutes = durationMinutes;
        this.type = type;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getTherapistId() {
        return therapistId;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}