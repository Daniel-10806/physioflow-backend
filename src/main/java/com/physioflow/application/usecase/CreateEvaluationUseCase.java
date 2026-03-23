package com.physioflow.application.usecase;

import com.physioflow.application.dto.request.CreateEvaluationRequest;

import java.util.UUID;

public interface CreateEvaluationUseCase {

    UUID execute(UUID patientId, CreateEvaluationRequest request);
}