package com.physioflow.application.usecase;

import com.physioflow.application.dto.response.PatientEvolutionResponse;

import java.util.List;
import java.util.UUID;

public interface GetPatientEvolutionUseCase {

    List<PatientEvolutionResponse> execute(UUID patientId);

}