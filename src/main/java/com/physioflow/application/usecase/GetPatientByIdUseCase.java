package com.physioflow.application.usecase;

import com.physioflow.application.dto.response.PatientResponse;

import java.util.UUID;

public interface GetPatientByIdUseCase {

    PatientResponse execute(UUID id);

}