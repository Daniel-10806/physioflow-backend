package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.application.dto.response.PatientEvolutionResponse;
import com.physioflow.application.dto.response.PatientHistoryResponse;
import com.physioflow.infrastructure.persistence.entity.SessionRecordJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SessionRecordJpaRepository extends JpaRepository<SessionRecordJpaEntity, UUID> {

    @Query("""
                SELECT new com.physioflow.application.dto.response.PatientHistoryResponse(
                    s.sessionDate,
                    s.sessionTime,
                    s.type,
                    r.subjective,
                    r.objective,
                    r.assessment,
                    r.treatment,
                    r.progressNotes
                )
                FROM SessionRecordJpaEntity r
                JOIN r.session s
                WHERE s.patient.id = :patientId
                AND r.createdAt = (
                    SELECT MAX(r2.createdAt)
                    FROM SessionRecordJpaEntity r2
                    WHERE r2.session.id = s.id
                )
                ORDER BY s.sessionDate DESC, s.sessionTime DESC
            """)
    List<PatientHistoryResponse> findPatientHistory(@Param("patientId") UUID patientId);

    List<SessionRecordJpaEntity> findBySession_Patient_Id(UUID patientId);

    @Query("""
                SELECT r
                FROM SessionRecordJpaEntity r
                JOIN r.session s
                WHERE s.patient.id = :patientId
                ORDER BY r.createdAt DESC
                LIMIT 1
            """)
    SessionRecordJpaEntity findLastRecord(@Param("patientId") UUID patientId);

    @Query("""
                SELECT r
                FROM SessionRecordJpaEntity r
                WHERE r.session.id = :sessionId
                ORDER BY r.createdAt DESC
            """)
    List<SessionRecordJpaEntity> findBySessionId(UUID sessionId);

    @Query("""
                SELECT new com.physioflow.application.dto.response.PatientEvolutionResponse(
                    s.sessionDate,
                    r.painLevel,
                    r.mobilityLevel,
                    r.strengthLevel
                )
                FROM SessionRecordJpaEntity r
                JOIN r.session s
                WHERE s.patient.id = :patientId
                AND r.createdAt = (
                    SELECT MAX(r2.createdAt)
                    FROM SessionRecordJpaEntity r2
                    WHERE r2.session.id = s.id
                )
                ORDER BY s.sessionDate ASC
            """)
    List<PatientEvolutionResponse> findEvolution(@Param("patientId") UUID patientId);

}