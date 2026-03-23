package com.physioflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "evaluations")
public class EvaluationJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientJpaEntity patient;

    @Column(name = "injury_type")
    private String injuryType;

    @Column(name = "injury_phase")
    private String injuryPhase;

    @Column(name = "pain_scale")
    private int painScale;

    @Column(name = "range_of_motion")
    private int rangeOfMotion;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    protected EvaluationJpaEntity() {
    }

    public EvaluationJpaEntity(UUID id,
            PatientJpaEntity patient,
            String injuryType,
            String injuryPhase,
            int painScale,
            int rangeOfMotion,
            LocalDate evaluationDate) {
        this.id = id;
        this.patient = patient;
        this.injuryType = injuryType;
        this.injuryPhase = injuryPhase;
        this.painScale = painScale;
        this.rangeOfMotion = rangeOfMotion;
        this.evaluationDate = evaluationDate;
    }

    public UUID getId() {
        return id;
    }

    public PatientJpaEntity getPatient() {
        return patient;
    }

    public String getInjuryType() {
        return injuryType;
    }

    public String getInjuryPhase() {
        return injuryPhase;
    }

    public int getPainScale() {
        return painScale;
    }

    public int getRangeOfMotion() {
        return rangeOfMotion;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }
}