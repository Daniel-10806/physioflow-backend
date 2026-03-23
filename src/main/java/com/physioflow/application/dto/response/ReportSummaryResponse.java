package com.physioflow.application.dto.response;

public class ReportSummaryResponse {

    private long totalSessions;
    private long completedSessions;
    private long totalPatients;
    private double totalRevenue;

    public ReportSummaryResponse(
            long totalSessions,
            long completedSessions,
            long totalPatients,
            double totalRevenue) {

        this.totalSessions = totalSessions;
        this.completedSessions = completedSessions;
        this.totalPatients = totalPatients;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalSessions() {
        return totalSessions;
    }

    public long getCompletedSessions() {
        return completedSessions;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}