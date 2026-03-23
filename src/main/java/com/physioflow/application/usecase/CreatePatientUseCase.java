package com.physioflow.application.usecase;

import com.physioflow.application.dto.request.CreatePatientRequest;
import com.physioflow.application.dto.response.PatientResponse;

import java.util.List;

public interface CreatePatientUseCase {

    PatientResponse execute(CreatePatientRequest request);

    List<PatientResponse> getAll();
}