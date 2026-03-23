package com.physioflow.domain.model.entity;

import com.physioflow.domain.model.enumtype.InjuryPhase;
import com.physioflow.domain.model.enumtype.InjuryType;
import com.physioflow.domain.model.valueobject.PainScale;
import com.physioflow.domain.model.valueobject.RangeOfMotion;

import java.time.LocalDate;
import java.util.UUID;

public class Evaluation {

    private final UUID id;
    private final InjuryType injuryType;
    private final InjuryPhase phase;
    private final PainScale painScale;
    private final RangeOfMotion rangeOfMotion;
    private final LocalDate evaluationDate;
    private UUID patientId;

    public Evaluation(
            UUID id,
            UUID patientId,
            InjuryType injuryType,
            InjuryPhase phase,
            PainScale painScale,
            RangeOfMotion rangeOfMotion,
            LocalDate evaluationDate) {

        this.id = id;
        this.patientId = patientId;
        this.injuryType = injuryType;
        this.phase = phase;
        this.painScale = painScale;
        this.rangeOfMotion = rangeOfMotion;
        this.evaluationDate = evaluationDate;
    }

    public UUID getId() {
        return id;
    }

    public InjuryType getInjuryType() {
        return injuryType;
    }

    public InjuryPhase getPhase() {
        return phase;
    }

    public PainScale getPainScale() {
        return painScale;
    }

    public RangeOfMotion getRangeOfMotion() {
        return rangeOfMotion;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public UUID getPatientId() {
        return patientId;
    }
}