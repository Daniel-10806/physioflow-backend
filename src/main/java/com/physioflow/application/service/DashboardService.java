package com.physioflow.application.service;

import com.physioflow.application.dto.response.DashboardSummaryResponse;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class DashboardService {

        private final SessionJpaRepository repository;

        public DashboardService(SessionJpaRepository repository) {
                this.repository = repository;
        }

        public DashboardSummaryResponse getSummary(UUID therapistId) {

                LocalDate today = LocalDate.now();

                long sessionsToday = repository.countByTherapist_IdAndSessionDate(
                                therapistId,
                                today);

                long sessionsCompleted = repository.countByTherapist_IdAndSessionDateAndStatus(
                                therapistId,
                                today,
                                "COMPLETADA");

                long sessionsPending = repository.countByTherapist_IdAndSessionDateAndStatus(
                                therapistId,
                                today,
                                "PROGRAMADO");

                long patientsActive = repository.countActivePatients(therapistId);

                return new DashboardSummaryResponse(
                                sessionsToday,
                                patientsActive,
                                sessionsCompleted,
                                sessionsPending);
        }
}