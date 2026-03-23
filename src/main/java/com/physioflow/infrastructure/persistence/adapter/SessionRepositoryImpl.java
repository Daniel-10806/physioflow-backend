package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.entity.Session;
import com.physioflow.domain.repository.SessionRepository;
import com.physioflow.infrastructure.persistence.entity.PatientJpaEntity;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import com.physioflow.infrastructure.persistence.entity.TherapistJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.TherapistJpaRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

        private final SessionJpaRepository jpaRepository;
        private final PatientJpaRepository patientRepository;
        private final TherapistJpaRepository therapistRepository;

        public SessionRepositoryImpl(
                        SessionJpaRepository jpaRepository,
                        PatientJpaRepository patientRepository,
                        TherapistJpaRepository therapistRepository) {

                this.jpaRepository = jpaRepository;
                this.patientRepository = patientRepository;
                this.therapistRepository = therapistRepository;
        }

        @Override
        public Session save(Session session) {

                SessionJpaEntity entity = new SessionJpaEntity();

                entity.setId(session.getId());

                TherapistJpaEntity therapist = therapistRepository.findById(session.getTherapistId())
                                .orElseThrow();

                entity.setTherapist(therapist);

                PatientJpaEntity patient = patientRepository.findById(session.getPatientId())
                                .orElseThrow();

                entity.setPatient(patient);

                entity.setSessionDate(session.getSessionDate());
                entity.setSessionTime(session.getSessionTime());
                entity.setDurationMinutes(session.getDurationMinutes());
                entity.setType(session.getType());
                entity.setStatus(session.getStatus());

                jpaRepository.save(entity);

                return session;
        }

        @Override
        public List<Session> findByDate(LocalDate date) {

                return jpaRepository.findBySessionDate(date)
                                .stream()
                                .map(entity -> new Session(
                                                entity.getId(),
                                                entity.getPatient().getId(),
                                                entity.getTherapist().getId(),
                                                entity.getSessionDate(),
                                                entity.getSessionTime(),
                                                entity.getDurationMinutes(),
                                                entity.getType(),
                                                entity.getStatus()))
                                .collect(Collectors.toList());
        }

        @Override
        public List<Session> findByTherapistAndDate(UUID therapistId, LocalDate date) {

                return jpaRepository.findByTherapist_IdAndSessionDate(therapistId, date)
                                .stream()
                                .map(entity -> new Session(
                                                entity.getId(),
                                                entity.getPatient().getId(),
                                                entity.getTherapist().getId(),
                                                entity.getSessionDate(),
                                                entity.getSessionTime(),
                                                entity.getDurationMinutes(),
                                                entity.getType(),
                                                entity.getStatus()))
                                .collect(Collectors.toList());
        }

        @Override
        public void startSession(UUID sessionId) {

                SessionJpaEntity session = jpaRepository.findById(sessionId)
                                .orElseThrow();

                session.setStatus("EN_CURSO");

                jpaRepository.save(session);
        }

        @Override
        public void completeSession(UUID sessionId) {

                SessionJpaEntity session = jpaRepository.findById(sessionId)
                                .orElseThrow();

                session.setStatus("COMPLETADA");

                jpaRepository.save(session);
        }

}