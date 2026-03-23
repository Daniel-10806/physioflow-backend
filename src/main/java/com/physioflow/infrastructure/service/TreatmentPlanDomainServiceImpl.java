package com.physioflow.infrastructure.service;

import com.physioflow.domain.model.entity.TreatmentPlan;
import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.entity.Exercise;
import com.physioflow.domain.service.TreatmentPlanDomainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TreatmentPlanDomainServiceImpl implements TreatmentPlanDomainService {

    @Override
    public TreatmentPlan generatePlan(UUID patientId, Evaluation evaluation) {

        List<Exercise> exercises = List.of();

        return new TreatmentPlan(
                UUID.randomUUID(),
                patientId,
                exercises,
                LocalDate.now(),
                LocalDate.now().plusWeeks(4));
    }
}