package com.physioflow.domain.repository;

import com.physioflow.domain.model.entity.TreatmentPlan;

import java.util.Optional;
import java.util.UUID;

public interface TreatmentPlanRepository {

    void save(TreatmentPlan plan);

    Optional<TreatmentPlan> findById(UUID id);
}