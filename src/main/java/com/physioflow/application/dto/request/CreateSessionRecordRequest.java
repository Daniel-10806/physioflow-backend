package com.physioflow.application.dto.request;

public class CreateSessionRecordRequest {

    private String subjective;
    private String objective;
    private String assessment;
    private String treatment;
    private String progressNotes;
    private Integer painLevel;
    private Integer mobilityLevel;
    private Integer strengthLevel;

    public String getSubjective() {
        return subjective;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getProgressNotes() {
        return progressNotes;
    }

    public void setProgressNotes(String progressNotes) {
        this.progressNotes = progressNotes;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Integer getMobilityLevel() {
        return mobilityLevel;
    }

    public void setMobilityLevel(Integer mobilityLevel) {
        this.mobilityLevel = mobilityLevel;
    }

    public Integer getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(Integer strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

}