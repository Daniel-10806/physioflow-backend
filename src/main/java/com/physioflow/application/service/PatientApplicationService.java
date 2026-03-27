package com.physioflow.application.service;

import com.physioflow.application.dto.request.CreateEvaluationRequest;
import com.physioflow.application.dto.request.CreatePatientRequest;
import com.physioflow.application.dto.response.PatientResponse;
import com.physioflow.application.mapper.PatientMapper;
import com.physioflow.application.usecase.CreateEvaluationUseCase;
import com.physioflow.application.usecase.CreatePatientUseCase;
import com.physioflow.application.usecase.DeletePatientUseCase;
import com.physioflow.application.usecase.GenerateTreatmentPlanUseCase;
import com.physioflow.domain.model.aggregate.Patient;
import org.springframework.beans.factory.ObjectProvider;

import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.repository.EvaluationRepository;
import com.physioflow.domain.model.entity.TreatmentPlan;
import com.physioflow.domain.model.valueobject.PainScale;
import com.physioflow.domain.model.valueobject.RangeOfMotion;
import com.physioflow.domain.repository.PatientRepository;
import com.physioflow.domain.repository.TreatmentPlanRepository;
import com.physioflow.domain.service.TreatmentPlanDomainService;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public class PatientApplicationService
                implements CreatePatientUseCase, GenerateTreatmentPlanUseCase, CreateEvaluationUseCase,
                DeletePatientUseCase {

        private final PatientRepository patientRepository;
        private final TreatmentPlanRepository treatmentPlanRepository;
        private final TreatmentPlanDomainService treatmentPlanDomainService;
        private final EvaluationRepository evaluationRepository;

        public PatientApplicationService(
                        PatientRepository patientRepository,
                        TreatmentPlanRepository treatmentPlanRepository,
                        TreatmentPlanDomainService treatmentPlanDomainService,
                        EvaluationRepository evaluationRepository) {

                this.patientRepository = patientRepository;
                this.treatmentPlanRepository = treatmentPlanRepository;
                this.treatmentPlanDomainService = treatmentPlanDomainService;
                this.evaluationRepository = evaluationRepository;
        }

        // =====================================
        // CREATE PATIENT
        // =====================================
        @Override
        public PatientResponse execute(CreatePatientRequest request) {

                Patient patient = PatientMapper.toDomain(request);
                patientRepository.save(patient);

                return PatientMapper.toResponse(patient);
        }

        // =====================================
        // CREATE EVALUATION
        // =====================================
        @Override
        public UUID execute(UUID patientId, CreateEvaluationRequest request) {

                Patient patient = patientRepository.findById(patientId)
                                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

                Evaluation evaluation = new Evaluation(
                                UUID.randomUUID(),
                                patient.getId(),
                                request.getInjuryType(),
                                request.getPhase(),
                                new PainScale(request.getPainScale()),
                                new RangeOfMotion(request.getRangeOfMotion()),
                                java.time.LocalDate.now());

                evaluationRepository.save(evaluation);

                return evaluation.getId();
        }

        // =====================================
        // GENERATE TREATMENT PLAN
        // =====================================
        @Override
        public UUID execute(UUID patientId) {

                Patient patient = patientRepository.findById(patientId)
                                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

                Evaluation evaluation = evaluationRepository
                                .findLatestByPatientId(patientId)
                                .orElseThrow(() -> new RuntimeException("El paciente no tiene evaluaciones"));

                TreatmentPlan plan = treatmentPlanDomainService.generatePlan(
                                patient.getId(),
                                evaluation);

                treatmentPlanRepository.save(plan);

                return plan.getId();
        }

        // =====================================
        // GET ALL PATIENTS
        // =====================================
        @Override
        public List<PatientResponse> getAll() {

                return patientRepository.findAll()
                                .stream()
                                .map(PatientMapper::toResponse)
                                .toList();
        }

        @Override
        public void delete(UUID id) {

                if (patientRepository.findById(id).isEmpty()) {
                        throw new RuntimeException("Paciente no encontrado");
                }

                patientRepository.deleteById(id);
        }
}