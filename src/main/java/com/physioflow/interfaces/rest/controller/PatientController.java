package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.request.CreateEvaluationRequest;
import com.physioflow.application.dto.request.CreatePatientRequest;
import com.physioflow.application.dto.response.PatientEvolutionResponse;
import com.physioflow.application.dto.response.PatientResponse;
import com.physioflow.application.usecase.CreateEvaluationUseCase;
import com.physioflow.application.usecase.CreatePatientUseCase;
import com.physioflow.application.usecase.DeletePatientUseCase;
import com.physioflow.application.usecase.GenerateTreatmentPlanUseCase;
import com.physioflow.application.usecase.GetPatientByIdUseCase;
import com.physioflow.application.usecase.GetPatientEvolutionUseCase;

import com.physioflow.application.service.RoleService;
import com.physioflow.domain.model.enumtype.Role;

import org.springframework.beans.factory.annotation.Qualifier;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;
    private final GenerateTreatmentPlanUseCase generateTreatmentPlanUseCase;
    private final CreateEvaluationUseCase createEvaluationUseCase;
    private final DeletePatientUseCase deletePatientUseCase;
    private final GetPatientByIdUseCase getPatientByIdUseCase;
    private final GetPatientEvolutionUseCase getPatientEvolutionUseCase;

    private final RoleService roleService;

    public PatientController(

            @Qualifier("patientApplicationService") CreatePatientUseCase createPatientUseCase,

            @Qualifier("generateTreatmentPlanUseCaseImpl") GenerateTreatmentPlanUseCase generateTreatmentPlanUseCase,

            @Qualifier("createEvaluationUseCaseImpl") CreateEvaluationUseCase createEvaluationUseCase,

            @Qualifier("deletePatientUseCaseImpl") DeletePatientUseCase deletePatientUseCase,

            @Qualifier("getPatientByIdUseCaseImpl") GetPatientByIdUseCase getPatientByIdUseCase,

            @Qualifier("getPatientEvolutionUseCaseImpl") GetPatientEvolutionUseCase getPatientEvolutionUseCase,

            RoleService roleService) {

        this.createPatientUseCase = createPatientUseCase;
        this.generateTreatmentPlanUseCase = generateTreatmentPlanUseCase;
        this.createEvaluationUseCase = createEvaluationUseCase;
        this.deletePatientUseCase = deletePatientUseCase;
        this.getPatientByIdUseCase = getPatientByIdUseCase;
        this.getPatientEvolutionUseCase = getPatientEvolutionUseCase;

        this.roleService = roleService;
    }

    // =========================================
    // CREATE PATIENT
    // =========================================
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody CreatePatientRequest request) {

        if (!roleService.hasRole(Role.ROLE_ADMIN)
                && !roleService.hasRole(Role.ROLE_THERAPIST)) {

            throw new RuntimeException("No autorizado");
        }

        PatientResponse response = createPatientUseCase.execute(request);

        return ResponseEntity
                .created(
                        URI.create(
                                "/api/v1/patients/" +
                                        response.getId()))
                .body(response);
    }

    @PostMapping("/{id}/evaluations")
    public ResponseEntity<UUID> createEvaluation(
            @PathVariable UUID id,
            @Valid @RequestBody CreateEvaluationRequest request) {

        UUID evaluationId = createEvaluationUseCase.execute(
                id,
                request);

        return ResponseEntity.ok(evaluationId);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {

        List<PatientResponse> patients = createPatientUseCase.getAll();

        return ResponseEntity.ok(patients);
    }

    // =========================================
    // GENERATE TREATMENT PLAN
    // =========================================
    @PostMapping("/{id}/treatment-plan")
    public ResponseEntity<UUID> generateTreatmentPlan(
            @PathVariable UUID id) {

        UUID planId = generateTreatmentPlanUseCase.execute(id);

        return ResponseEntity.ok(planId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable UUID id) {

        if (!roleService.hasRole(Role.ROLE_ADMIN)) {
            throw new RuntimeException("Solo admin");
        }

        deletePatientUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================
    // GET PATIENT BY ID
    // =========================================
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable UUID id) {

        PatientResponse patient = getPatientByIdUseCase.execute(id);

        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{id}/evolution")
    public ResponseEntity<List<PatientEvolutionResponse>> getEvolution(
            @PathVariable UUID id) {

        List<PatientEvolutionResponse> evolution = getPatientEvolutionUseCase.execute(id);

        return ResponseEntity.ok(evolution);
    }
}