package com.physioflow.domain.repository;

import com.physioflow.domain.model.entity.Therapist;
import java.util.Optional;
import java.util.UUID;

public interface TherapistRepository {

    void save(Therapist therapist);

    Optional<Therapist> findByEmail(String email);

    Optional<Therapist> findById(UUID id);
}