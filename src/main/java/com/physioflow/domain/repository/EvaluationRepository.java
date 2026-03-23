package com.physioflow.domain.repository;

import com.physioflow.domain.model.entity.Evaluation;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface EvaluationRepository {

    void save(Evaluation evaluation);

    Optional<Evaluation> findById(UUID id);

    Optional<Evaluation> findLatestByPatientId(UUID patientId);

    List<Evaluation> findByPatientId(UUID patientId);
}