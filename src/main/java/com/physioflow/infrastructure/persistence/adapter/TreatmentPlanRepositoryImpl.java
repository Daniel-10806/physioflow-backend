package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.entity.TreatmentPlan;
import com.physioflow.domain.repository.TreatmentPlanRepository;
import com.physioflow.infrastructure.persistence.entity.TreatmentPlanJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import com.physioflow.infrastructure.persistence.repository.TreatmentPlanJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class TreatmentPlanRepositoryImpl implements TreatmentPlanRepository {

    private final TreatmentPlanJpaRepository jpaRepository;

    public TreatmentPlanRepositoryImpl(TreatmentPlanJpaRepository jpaRepository,
            PatientJpaRepository patientJpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(TreatmentPlan plan) {

        TreatmentPlanJpaEntity entity = new TreatmentPlanJpaEntity(
                plan.getId(),
                plan.getPatientId(),
                plan.getStartDate(),
                plan.getEndDate());

        jpaRepository.save(entity);
    }

    @Override
    public Optional<TreatmentPlan> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> new TreatmentPlan(
                        entity.getId(),
                        entity.getPatientId(),
                        List.of(),
                        entity.getStartDate(),
                        entity.getEndDate()));
    }
}