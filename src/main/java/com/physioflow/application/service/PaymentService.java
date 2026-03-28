package com.physioflow.application.service;

import com.physioflow.infrastructure.persistence.entity.PaymentJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PaymentJpaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;

@Service
public class PaymentService {

    private final PaymentJpaRepository repository;
    private final SessionJpaRepository sessionRepository;

    public PaymentService(
            PaymentJpaRepository repository,
            SessionJpaRepository sessionRepository) {

        this.repository = repository;
        this.sessionRepository = sessionRepository;
    }

    public PaymentJpaEntity createPayment(
            PaymentJpaEntity payment,
            UUID therapistId) {

        payment.setId(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setTherapistId(therapistId);

        if (payment.getType() == null)
            payment.setType("BOLETA");

        String serie = payment.getType().equals("FACTURA")
                ? "F001"
                : "B001";

        payment.setSerie(serie);

        Long last = repository.getLastNumberBySerie(serie);
        payment.setNumber(last + 1);

        double subtotal = payment.getAmount() / 1.18;
        double igv = payment.getAmount() - subtotal;

        payment.setSubtotal(subtotal);
        payment.setIgv(igv);

        PaymentJpaEntity saved = repository.save(payment);

        SessionJpaEntity session = sessionRepository
                .findById(payment.getSessionId())
                .orElseThrow();

        session.setStatus("PAID");

        sessionRepository.save(session);

        return saved;
    }
}