package com.physioflow.application.usecase;

import java.util.UUID;

public interface GenerateTreatmentPlanUseCase {

    UUID execute(UUID patientId);
}