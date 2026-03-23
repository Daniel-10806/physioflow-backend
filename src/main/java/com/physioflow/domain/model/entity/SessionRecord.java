package com.physioflow.domain.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionRecord {

    private UUID id;
    private UUID sessionId;

    private String subjective;
    private String objective;
    private String assessment;
    private String plan;
    private String treatment;
    private String progressNotes;

    private LocalDateTime createdAt;

    public SessionRecord(
            UUID id,
            UUID sessionId,
            String subjective,
            String objective,
            String assessment,
            String plan,
            String treatment,
            String progressNotes,
            LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.plan = plan;
        this.treatment = treatment;
        this.progressNotes = progressNotes;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public String getSubjective() {
        return subjective;
    }

    public String getObjective() {
        return objective;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getPlan() {
        return plan;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getProgressNotes() {
        return progressNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}