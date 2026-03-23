package com.physioflow.domain.repository;

import com.physioflow.domain.model.aggregate.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository {

    void save(Patient patient);

    Optional<Patient> findById(UUID id);

    List<Patient> findAll();

    void deleteById(UUID id);
}