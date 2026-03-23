package com.physioflow.application.usecase.impl;

import com.physioflow.application.usecase.GenerateTreatmentPlanUseCase;
import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.entity.TreatmentPlan;
import com.physioflow.domain.repository.EvaluationRepository;
import com.physioflow.domain.repository.TreatmentPlanRepository;
import com.physioflow.domain.service.TreatmentPlanDomainService;
import com.physioflow.infrastructure.kafka.producer.TreatmentPlanEventProducer;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateTreatmentPlanUseCaseImpl
        implements GenerateTreatmentPlanUseCase {

    private final EvaluationRepository evaluationRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final TreatmentPlanDomainService domainService;
    private final TreatmentPlanEventProducer producer;

    public GenerateTreatmentPlanUseCaseImpl(
            EvaluationRepository evaluationRepository,
            TreatmentPlanRepository treatmentPlanRepository,
            TreatmentPlanDomainService domainService,
            TreatmentPlanEventProducer producer) {

        this.evaluationRepository = evaluationRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.domainService = domainService;
        this.producer = producer;
    }

    @Override
    public UUID execute(UUID patientId) {

        Evaluation evaluation = evaluationRepository
                .findLatestByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Patient has no evaluation"));

        TreatmentPlan plan = domainService.generatePlan(patientId, evaluation);

        treatmentPlanRepository.save(plan);

        producer.publishTreatmentPlanGenerated(
                patientId,
                plan.getId());

        return plan.getId();
    }
}