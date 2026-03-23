package com.physioflow.application.service;

import com.physioflow.domain.model.enumtype.NotificationType;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import com.physioflow.application.dto.response.SessionTodayResponse;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SessionReminderService {

    private final SessionJpaRepository sessionRepository;
    private final PatientJpaRepository patientRepository;
    private final NotificationService notificationService;

    public SessionReminderService(
            SessionJpaRepository sessionRepository,
            PatientJpaRepository patientRepository,
            NotificationService notificationService) {

        this.sessionRepository = sessionRepository;
        this.patientRepository = patientRepository;
        this.notificationService = notificationService;
    }

    // cada 10 minutos
    // @Scheduled(fixedRate = 600000)
    public void sendReminders() {

        LocalDate today = LocalDate.now();

        // aquí puedes poner therapistId fijo o buscar todos
        UUID therapistId = null;

        List<SessionTodayResponse> sessions = sessionRepository.findTodaySessionsAll(today);

        for (SessionTodayResponse s : sessions) {

            var patient = patientRepository
                    .findById(
                            s.getPatientId())
                    .orElse(null);

            if (patient == null)
                continue;

            notificationService.send(

                    NotificationType.SESSION_REMINDER,

                    patient.getEmail(),

                    patient.getFullName(),

                    s.getSessionDate().toString(),

                    s.getSessionTime().toString(),

                    s.getType()

            );
        }
    }
}