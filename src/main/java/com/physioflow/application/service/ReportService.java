package com.physioflow.application.service;

import com.physioflow.application.dto.request.ReportFilterRequest;
import com.physioflow.application.dto.response.ReportSummaryResponse;
import com.physioflow.application.dto.response.ReportRowResponse;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PaymentJpaRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class ReportService {

    private final SessionJpaRepository sessionRepository;
    private final PaymentJpaRepository paymentRepository;

    public ReportService(
            SessionJpaRepository sessionRepository,
            PaymentJpaRepository paymentRepository) {

        this.sessionRepository = sessionRepository;
        this.paymentRepository = paymentRepository;
    }

    public ReportSummaryResponse getSummary(
            UUID therapistId) {

        long totalSessions = sessionRepository
                .countByTherapist_Id(
                        therapistId);

        long completed = sessionRepository
                .countByTherapist_IdAndStatus(
                        therapistId,
                        "COMPLETADA");

        long patients = sessionRepository
                .countActivePatients(
                        therapistId);

        double revenue = paymentRepository
                .getTotalRevenue(
                        therapistId);

        return new ReportSummaryResponse(
                totalSessions,
                completed,
                patients,
                revenue);
    }

    public List<ReportRowResponse> search(
            ReportFilterRequest filter) {

        var sessions = sessionRepository.search(
                filter.getFromDate(),
                filter.getToDate(),
                filter.getPatientId(),
                filter.getTherapistId());

        return sessions.stream()
                .map(s -> {

                    Double amount = paymentRepository
                            .getAmountBySessionId(
                                    s.getId());

                    if (amount == null) {
                        amount = 0.0;
                    }

                    return new ReportRowResponse(
                            s.getSessionDate(),
                            s.getPatient().getFullName(),
                            s.getTherapist().getFullName(),
                            s.getType(),
                            amount);

                })
                .toList();
    }
}