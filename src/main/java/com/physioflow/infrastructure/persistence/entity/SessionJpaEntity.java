package com.physioflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "sessions")
public class SessionJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id")
    private TherapistJpaEntity therapist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientJpaEntity patient;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private LocalTime sessionTime;

    @Column(nullable = false)
    private int durationMinutes;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionRecordJpaEntity> records = new ArrayList<>();

    @Column(name = "reminder_sent")
    private Boolean reminderSent;

    public SessionJpaEntity() {
    }

    public SessionJpaEntity(
            UUID id,
            TherapistJpaEntity therapist,
            PatientJpaEntity patient,
            LocalDate sessionDate,
            LocalTime sessionTime,
            int durationMinutes,
            String type,
            String status) {
        this.id = id;
        this.therapist = therapist;
        this.patient = patient;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.durationMinutes = durationMinutes;
        this.type = type;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public TherapistJpaEntity getTherapist() {
        return therapist;
    }

    public PatientJpaEntity getPatient() {
        return patient;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTherapist(TherapistJpaEntity therapist) {
        this.therapist = therapist;
    }

    public void setPatient(PatientJpaEntity patient) {
        this.patient = patient;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setSessionTime(LocalTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(Boolean reminderSent) {
        this.reminderSent = reminderSent;
    }
}