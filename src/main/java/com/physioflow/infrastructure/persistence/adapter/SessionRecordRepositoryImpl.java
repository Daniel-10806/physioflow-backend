package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.entity.SessionRecord;
import com.physioflow.domain.repository.SessionRecordRepository;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import com.physioflow.infrastructure.persistence.entity.SessionRecordJpaEntity;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public class SessionRecordRepositoryImpl implements SessionRecordRepository {

    private final SessionRecordJpaRepository jpaRepository;

    public SessionRecordRepositoryImpl(SessionRecordJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(SessionRecord record) {

        SessionRecordJpaEntity entity = new SessionRecordJpaEntity();

        entity.setId(record.getId());

        SessionJpaEntity session = new SessionJpaEntity();
        session.setId(record.getSessionId());

        entity.setSession(session);

        entity.setSubjective(record.getSubjective());
        entity.setObjective(record.getObjective());
        entity.setAssessment(record.getAssessment());
        entity.setPlan(record.getPlan());
        entity.setTreatment(record.getTreatment());
        entity.setProgressNotes(record.getProgressNotes());
        entity.setCreatedAt(record.getCreatedAt());

        jpaRepository.save(entity);
    }

    @Override
    public List<SessionRecord> findByPatientId(UUID patientId) {

        return jpaRepository.findBySession_Patient_Id(patientId)
                .stream()
                .map(entity -> new SessionRecord(
                        entity.getId(),
                        entity.getSession().getId(),
                        entity.getSubjective(),
                        entity.getObjective(),
                        entity.getAssessment(),
                        entity.getPlan(),
                        entity.getTreatment(),
                        entity.getProgressNotes(),
                        entity.getCreatedAt()))
                .toList();
    }
}