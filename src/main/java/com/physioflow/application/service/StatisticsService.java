package com.physioflow.application.service;

import com.physioflow.application.dto.response.StatisticsResponse;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PaymentJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatisticsService {

    private final SessionJpaRepository sessionRepository;
    private final PaymentJpaRepository paymentRepository;
    private final PatientJpaRepository patientRepository;

    public StatisticsService(
            SessionJpaRepository sessionRepository,
            PaymentJpaRepository paymentRepository,
            PatientJpaRepository patientRepository) {

        this.sessionRepository = sessionRepository;
        this.paymentRepository = paymentRepository;
        this.patientRepository = patientRepository;
    }

    public StatisticsResponse getStats(UUID therapistId) {

        long totalPatients = patientRepository.count();

        long totalSessions = sessionRepository.count();

        double totalIncome = paymentRepository
                .findByTherapistId(therapistId)
                .stream()
                .mapToDouble(p -> p.getAmount())
                .sum();

        // demo data (puedes luego hacer queries reales)

        List<Long> sessionsPerDay = List.of(2L, 5L, 3L, 6L, 4L, 7L, 2L);

        List<Double> incomePerDay = List.of(50.0, 80.0, 60.0, 100.0, 40.0, 120.0, 30.0);

        List<Long> sessionsPerMonth = List.of(40L, 30L, 60L, 20L, 80L, 50L);

        List<Double> incomePerMonth = List.of(500.0, 800.0, 600.0, 300.0, 1200.0, 700.0);

        return new StatisticsResponse(
                sessionsPerDay,
                incomePerDay,
                sessionsPerMonth,
                incomePerMonth,
                totalPatients,
                totalSessions,
                totalIncome);
    }
}