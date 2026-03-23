package com.physioflow.application.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class PatientHistoryResponse {

    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String type;
    private String subjective;
    private String objective;
    private String assessment;
    private String treatment;
    private String progressNotes;

    public PatientHistoryResponse(
            LocalDate sessionDate,
            LocalTime sessionTime,
            String type,
            String subjective,
            String objective,
            String assessment,
            String treatment,
            String progressNotes) {

        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.type = type;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.treatment = treatment;
        this.progressNotes = progressNotes;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public String getType() {
        return type;
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

    public String getTreatment() {
        return treatment;
    }

    public String getProgressNotes() {
        return progressNotes;
    }
}