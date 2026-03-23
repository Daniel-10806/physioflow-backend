package com.physioflow.domain.service;

import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.entity.TreatmentPlan;

import java.util.UUID;

public interface TreatmentPlanDomainService {

    TreatmentPlan generatePlan(UUID patientId, Evaluation evaluation);
}