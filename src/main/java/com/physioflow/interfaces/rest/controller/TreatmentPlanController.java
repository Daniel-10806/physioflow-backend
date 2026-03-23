package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.usecase.GenerateTreatmentPlanUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/treatment-plans")
public class TreatmentPlanController {

    private final GenerateTreatmentPlanUseCase useCase;

    public TreatmentPlanController(

            @Qualifier("generateTreatmentPlanUseCaseImpl") GenerateTreatmentPlanUseCase useCase) {

        this.useCase = useCase;
    }

    @PostMapping("/{patientId}")
    public UUID generate(@PathVariable UUID patientId) {

        return useCase.execute(patientId);

    }
}