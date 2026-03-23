package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.aggregate.Patient;
import com.physioflow.domain.repository.PatientRepository;
import com.physioflow.domain.model.valueobject.Email;
import com.physioflow.infrastructure.persistence.entity.PatientJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientJpaRepository jpaRepository;

    public PatientRepositoryImpl(PatientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Patient patient) {

        PatientJpaEntity entity = new PatientJpaEntity(
                patient.getId(),
                patient.getFullName(),
                patient.getDni(),
                patient.getPhone(),
                patient.getEmail().getValue(),
                patient.getBirthDate(),
                patient.getMainDiagnosis(),
                patient.getInjuryDate(),
                patient.getObservations(),
                patient.getCreatedAt());

        jpaRepository.save(entity);
    }

    @Override
    public Optional<Patient> findById(UUID id) {

        return jpaRepository.findById(id)
                .map(entity -> new Patient(
                        entity.getId(),
                        entity.getFullName(),
                        entity.getDni(),
                        entity.getPhone(),
                        new Email(entity.getEmail()),
                        entity.getBirthDate(),
                        entity.getMainDiagnosis(),
                        entity.getInjuryDate(),
                        entity.getObservations()));
    }

    @Override
    public List<Patient> findAll() {

        return jpaRepository.findAll()
                .stream()
                .map(entity -> new Patient(
                        entity.getId(),
                        entity.getFullName(),
                        entity.getDni(),
                        entity.getPhone(),
                        new Email(entity.getEmail()),
                        entity.getBirthDate(),
                        entity.getMainDiagnosis(),
                        entity.getInjuryDate(),
                        entity.getObservations()))
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}