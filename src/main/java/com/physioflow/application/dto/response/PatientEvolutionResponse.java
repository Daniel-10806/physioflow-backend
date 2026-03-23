package com.physioflow.application.dto.response;

import java.time.LocalDate;

public class PatientEvolutionResponse {

    private LocalDate sessionDate;
    private Integer painLevel;
    private Integer mobilityLevel;
    private Integer strengthLevel;

    public PatientEvolutionResponse(
            LocalDate sessionDate,
            Integer painLevel,
            Integer mobilityLevel,
            Integer strengthLevel) {

        this.sessionDate = sessionDate;
        this.painLevel = painLevel;
        this.mobilityLevel = mobilityLevel;
        this.strengthLevel = strengthLevel;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public Integer getMobilityLevel() {
        return mobilityLevel;
    }

    public Integer getStrengthLevel() {
        return strengthLevel;
    }

}