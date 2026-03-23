package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.entity.Therapist;
import com.physioflow.domain.repository.TherapistRepository;
import com.physioflow.infrastructure.persistence.entity.TherapistJpaEntity;
import com.physioflow.infrastructure.persistence.repository.TherapistJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TherapistRepositoryImpl implements TherapistRepository {

    private final TherapistJpaRepository jpaRepository;

    public TherapistRepositoryImpl(TherapistJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Therapist therapist) {

        TherapistJpaEntity entity = new TherapistJpaEntity(
                therapist.getId(),
                therapist.getFullName(),
                therapist.getEmail(),
                therapist.getPasswordHash(),
                therapist.getRole(),
                therapist.getGender());

        jpaRepository.save(entity);
    }

    @Override
    public Optional<Therapist> findByEmail(String email) {

        return jpaRepository.findByEmail(email)
                .map(entity -> new Therapist(
                        entity.getId(),
                        entity.getFullName(),
                        entity.getEmail(),
                        entity.getPasswordHash(),
                        entity.getRole(),
                        entity.getGender()));
    }

    @Override
    public Optional<Therapist> findById(UUID id) {

        return jpaRepository.findById(id)
                .map(entity -> new Therapist(
                        entity.getId(),
                        entity.getFullName(),
                        entity.getEmail(),
                        entity.getPasswordHash(),
                        entity.getRole(),
                        entity.getGender()));
    }
}