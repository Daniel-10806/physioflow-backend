package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.infrastructure.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PaymentJpaRepository
                extends JpaRepository<PaymentJpaEntity, UUID> {

        List<PaymentJpaEntity> findByTherapistId(UUID therapistId);

        @Query("""
                            SELECT COALESCE(MAX(p.number),0)
                            FROM PaymentJpaEntity p
                        """)
        Long getLastNumber();

        @Query("""
                        SELECT COALESCE(MAX(p.number),0)
                        FROM PaymentJpaEntity p
                        WHERE p.serie = :serie
                        """)
        Long getLastNumberBySerie(String serie);

        @Query("""
                            SELECT COALESCE(SUM(p.amount),0)
                            FROM PaymentJpaEntity p
                            WHERE p.therapistId = :therapistId
                        """)
        Double getTotalRevenue(UUID therapistId);

        @Query("""
                            select coalesce(sum(p.amount),0)
                            from PaymentJpaEntity p
                            where p.sessionId = :sessionId
                        """)
        Double getAmountBySessionId(
                        @Param("sessionId") UUID sessionId);

}