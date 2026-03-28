package com.physioflow.interfaces.rest.controller;

import com.physioflow.domain.repository.SessionRepository;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;
import com.physioflow.infrastructure.persistence.repository.TherapistJpaRepository;
import com.physioflow.application.dto.response.SessionTodayResponse;
import com.physioflow.application.dto.response.SessionResponse;
import com.physioflow.application.dto.request.CreateSessionRequest;
import com.physioflow.application.service.AuditLogService;
import com.physioflow.application.service.EmailService;
import com.physioflow.application.service.NotificationService;
import com.physioflow.application.service.RoleService;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import com.physioflow.infrastructure.persistence.entity.SessionRecordJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import com.physioflow.domain.model.entity.Session;
import com.physioflow.domain.model.enumtype.AuditAction;
import com.physioflow.domain.model.enumtype.NotificationType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

        private final SessionRepository sessionRepository;
        private final SessionJpaRepository sessionJpaRepository;
        private final NotificationService notificationService;
        private final EmailService emailService;
        private final PatientJpaRepository patientRepository;
        private final SessionRecordJpaRepository sessionRecordRepository;
        private final AuditLogService auditLogService;
        private final RoleService roleService;
        private final TherapistJpaRepository therapistRepository;

        public SessionController(
                        SessionRepository sessionRepository,
                        SessionJpaRepository sessionJpaRepository,
                        NotificationService notificationService,
                        PatientJpaRepository patientRepository,
                        SessionRecordJpaRepository sessionRecordJpaRepository,
                        AuditLogService auditLogService,
                        RoleService roleService,
                        TherapistJpaRepository therapistRepository,
                        EmailService emailService) {

                this.sessionRepository = sessionRepository;
                this.sessionJpaRepository = sessionJpaRepository;
                this.notificationService = notificationService;
                this.patientRepository = patientRepository;
                this.sessionRecordRepository = sessionRecordJpaRepository;
                this.auditLogService = auditLogService;
                this.roleService = roleService;
                this.therapistRepository = therapistRepository;
                this.emailService = emailService;
        }

        // ===============================
        // Obtener sesiones de hoy
        // ===============================
        @GetMapping("/today")
        public List<SessionTodayResponse> getTodaySessions() {

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                return sessionJpaRepository.findTodaySessions(
                                therapistId,
                                LocalDate.now());
        }

        // ===============================
        // Obtener sesiones por fecha
        // ===============================
        @GetMapping
        public List<SessionTodayResponse> getSessionsByDate(
                        @RequestParam(required = false) String date) {

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                LocalDate selectedDate = date != null ? LocalDate.parse(date) : LocalDate.now();

                return sessionJpaRepository.findTodaySessions(
                                therapistId,
                                selectedDate);
        }

        // ===============================
        // NUEVO ENDPOINT (IMPORTANTE)
        // Obtener sesión por ID
        // ===============================
        @GetMapping("/{sessionId}")
        public SessionResponse getSession(
                        @PathVariable UUID sessionId) {

                return sessionJpaRepository.findSessionById(sessionId);
        }

        // ===============================
        // Crear sesión
        // ===============================
        @PostMapping
        public ResponseEntity<?> createSession(
                        @RequestBody CreateSessionRequest request) {

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                UUID id = UUID.randomUUID();

                var patient = patientRepository
                                .findById(request.patientId())
                                .orElseThrow();

                var therapist = therapistRepository
                                .findById(therapistId)
                                .orElseThrow();

                SessionJpaEntity session = new SessionJpaEntity();

                session.setId(id);
                session.setPatient(patient);
                session.setTherapist(therapist);
                session.setSessionDate(request.sessionDate());
                session.setSessionTime(request.sessionTime());
                session.setDurationMinutes(request.durationMinutes());
                session.setType(request.type());
                session.setStatus("PROGRAMADO");

                sessionJpaRepository.save(session);

                // ===== RECORD =====

                SessionRecordJpaEntity lastRecord = sessionRecordRepository.findLastRecord(
                                request.patientId());

                SessionRecordJpaEntity record = new SessionRecordJpaEntity();

                record.setId(UUID.randomUUID());
                record.setSession(session);
                record.setCreatedAt(LocalDateTime.now());

                if (lastRecord != null) {

                        record.setPainLevel(lastRecord.getPainLevel());
                        record.setMobilityLevel(lastRecord.getMobilityLevel());
                        record.setStrengthLevel(lastRecord.getStrengthLevel());

                } else {

                        record.setPainLevel(5);
                        record.setMobilityLevel(5);
                        record.setStrengthLevel(5);

                }

                sessionRecordRepository.save(record);

                try {

                        notificationService.send(
                                        NotificationType.SESSION_CREATED,
                                        request.patientEmail(),
                                        patient.getFullName(),
                                        request.sessionDate().toString(),
                                        request.sessionTime().toString(),
                                        request.type().toString());

                } catch (Exception e) {

                        System.out.println("Error enviando notificación: " + e.getMessage());

                }

                return ResponseEntity.ok().build();
        }

        // ===============================
        // Actualizar sesión (editar horario)
        // ===============================
        @PutMapping("/{sessionId}")
        public ResponseEntity<?> updateSession(
                        @PathVariable UUID sessionId,
                        @RequestBody CreateSessionRequest request) {

                SessionJpaEntity session = sessionJpaRepository.findById(sessionId)
                                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

                // actualizar datos
                session.setSessionDate(request.sessionDate());
                session.setSessionTime(request.sessionTime());
                session.setDurationMinutes(request.durationMinutes());
                session.setType(request.type());

                sessionJpaRepository.save(session);

                return ResponseEntity.ok().build();
        }

        // ===============================
        // Obtener sesiones por rango
        // ===============================
        @GetMapping("/range")
        public List<SessionTodayResponse> getSessionsByRange(
                        @RequestParam String start,
                        @RequestParam String end) {

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);

                return sessionJpaRepository.findSessionsByRange(
                                therapistId,
                                startDate,
                                endDate);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteSession(@PathVariable UUID id) {

                sessionJpaRepository.deleteById(id);

                return ResponseEntity.ok().build();

        }
}