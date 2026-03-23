package com.physioflow.domain.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final UUID sessionId;
    private final UUID patientId;
    private final UUID therapistId;

    private final double amount;
    private final String method;
    private final String status;

    private final LocalDateTime createdAt;

    public Payment(
            UUID id,
            UUID sessionId,
            UUID patientId,
            UUID therapistId,
            double amount,
            String method,
            String status,
            LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.patientId = patientId;
        this.therapistId = therapistId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public UUID getTherapistId() {
        return therapistId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}