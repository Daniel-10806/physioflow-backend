package com.physioflow.application.dto.request;

import com.physioflow.domain.model.enumtype.InjuryPhase;
import com.physioflow.domain.model.enumtype.InjuryType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateEvaluationRequest {

    @NotNull
    private InjuryType injuryType;

    @NotNull
    private InjuryPhase phase;

    @Min(0)
    @Max(10)
    private int painScale;

    @Min(0)
    @Max(180)
    private int rangeOfMotion;

    public InjuryType getInjuryType() {
        return injuryType;
    }

    public InjuryPhase getPhase() {
        return phase;
    }

    public int getPainScale() {
        return painScale;
    }

    public int getRangeOfMotion() {
        return rangeOfMotion;
    }
}