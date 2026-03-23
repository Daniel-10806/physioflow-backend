package com.physioflow.application.dto.response;

import java.util.List;

public class StatisticsResponse {

    private List<Long> sessionsPerDay;
    private List<Double> incomePerDay;

    private List<Long> sessionsPerMonth;
    private List<Double> incomePerMonth;

    private long totalPatients;
    private long totalSessions;
    private double totalIncome;

    public StatisticsResponse(
            List<Long> sessionsPerDay,
            List<Double> incomePerDay,
            List<Long> sessionsPerMonth,
            List<Double> incomePerMonth,
            long totalPatients,
            long totalSessions,
            double totalIncome) {

        this.sessionsPerDay = sessionsPerDay;
        this.incomePerDay = incomePerDay;
        this.sessionsPerMonth = sessionsPerMonth;
        this.incomePerMonth = incomePerMonth;
        this.totalPatients = totalPatients;
        this.totalSessions = totalSessions;
        this.totalIncome = totalIncome;
    }

    public List<Long> getSessionsPerDay() {
        return sessionsPerDay;
    }

    public List<Double> getIncomePerDay() {
        return incomePerDay;
    }

    public List<Long> getSessionsPerMonth() {
        return sessionsPerMonth;
    }

    public List<Double> getIncomePerMonth() {
        return incomePerMonth;
    }

    public long getTotalPatients() {
        return totalPatients;
    }

    public long getTotalSessions() {
        return totalSessions;
    }

    public double getTotalIncome() {
        return totalIncome;
    }
}