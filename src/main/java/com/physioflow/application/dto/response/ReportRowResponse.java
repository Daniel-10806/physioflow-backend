package com.physioflow.application.dto.response;

import java.time.LocalDate;

public class ReportRowResponse {

    private LocalDate date;
    private String patient;
    private String therapist;
    private String type;
    private double amount;

    public ReportRowResponse(
            LocalDate date,
            String patient,
            String therapist,
            String type,
            double amount) {
        this.date = date;
        this.patient = patient;
        this.therapist = therapist;
        this.type = type;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPatient() {
        return patient;
    }

    public String getTherapist() {
        return therapist;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    
}