package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.application.dto.response.SessionResponse;
import com.physioflow.application.dto.response.SessionTodayResponse;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SessionJpaRepository extends JpaRepository<SessionJpaEntity, UUID> {

        @Query("""
                        SELECT new com.physioflow.application.dto.response.SessionTodayResponse(
                            s.id,
                            p.id,
                            p.fullName,
                            s.sessionDate,
                            s.sessionTime,
                            s.type,
                            s.status,
                            s.durationMinutes,
                            t.fullName,
                            t.area,
                            CASE WHEN pay.id IS NOT NULL THEN true ELSE false END
                        )
                        FROM SessionJpaEntity s
                        JOIN s.patient p
                        JOIN s.therapist t
                        LEFT JOIN PaymentJpaEntity pay ON pay.sessionId = s.id
                        WHERE t.id = :therapistId
                        AND s.sessionDate = :date
                        ORDER BY s.sessionTime
                        """)
        List<SessionTodayResponse> findTodaySessions(
                        UUID therapistId,
                        LocalDate date);

        @Query("""
                        SELECT new com.physioflow.application.dto.response.SessionTodayResponse(
                            s.id,
                            p.id,
                            p.fullName,
                            s.sessionDate,
                            s.sessionTime,
                            s.type,
                            s.status,
                            s.durationMinutes,
                            t.fullName,
                            t.area,
                            CASE WHEN pay.id IS NOT NULL THEN true ELSE false END
                        )
                        FROM SessionJpaEntity s
                        JOIN s.patient p
                        JOIN s.therapist t
                        LEFT JOIN PaymentJpaEntity pay ON pay.sessionId = s.id
                        WHERE t.id = :therapistId
                        AND s.sessionDate BETWEEN :start AND :end
                        ORDER BY s.sessionDate, s.sessionTime
                        """)
        List<SessionTodayResponse> findSessionsByRange(
                        UUID therapistId,
                        LocalDate start,
                        LocalDate end);

        long countBySessionDate(LocalDate sessionDate);

        List<SessionJpaEntity> findBySessionDate(LocalDate date);

        long countByTherapist_IdAndSessionDate(UUID therapistId, LocalDate date);

        long countByTherapist_IdAndSessionDateAndStatus(
                        UUID therapistId,
                        LocalDate date,
                        String status);

        @Query("""
                        SELECT COUNT(DISTINCT s.patient.id)
                        FROM SessionJpaEntity s
                        WHERE s.therapist.id = :therapistId
                        """)
        long countActivePatients(UUID therapistId);

        @Query("""
                        SELECT new com.physioflow.application.dto.response.SessionResponse(
                            s.id,
                            s.patient.id
                        )
                        FROM SessionJpaEntity s
                        WHERE s.id = :sessionId
                        """)
        SessionResponse findSessionById(UUID sessionId);

        List<SessionJpaEntity> findByTherapist_IdAndSessionDate(
                        UUID therapist,
                        LocalDate date);

        @Query("""
                            SELECT new com.physioflow.application.dto.response.SessionTodayResponse(
                                s.id,
                                p.id,
                                p.fullName,
                                s.sessionDate,
                                s.sessionTime,
                                s.type,
                                s.status,
                                s.durationMinutes,
                                t.fullName,
                                t.area,
                                false
                            )
                            FROM SessionJpaEntity s
                            JOIN s.patient p
                            JOIN s.therapist t
                            WHERE s.sessionDate = :date
                        """)
        List<SessionTodayResponse> findTodaySessionsAll(LocalDate date);

        long countByTherapist_Id(UUID therapistId);

        long countByTherapist_IdAndStatus(
                        UUID therapistId,
                        String status);

        @Query("""
                        select s
                        from SessionJpaEntity s
                        where
                        (s.sessionDate >= coalesce(:fromDate, s.sessionDate))
                        and (s.sessionDate <= coalesce(:toDate, s.sessionDate))
                        and (coalesce(:patientId, s.patient.id) = s.patient.id)
                        and (coalesce(:therapistId, s.therapist.id) = s.therapist.id)
                        """)
        List<SessionJpaEntity> search(
                        @Param("fromDate") LocalDate fromDate,
                        @Param("toDate") LocalDate toDate,
                        @Param("patientId") UUID patientId,
                        @Param("therapistId") UUID therapistId);
}