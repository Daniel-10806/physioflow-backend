package com.physioflow.application.service;

import com.physioflow.application.dto.response.PaymentResponse;
import com.physioflow.infrastructure.persistence.repository.PaymentJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentQueryService {

    private final PaymentJpaRepository paymentRepository;
    private final PatientJpaRepository patientRepository;

    public PaymentQueryService(
            PaymentJpaRepository paymentRepository,
            PatientJpaRepository patientRepository) {

        this.paymentRepository = paymentRepository;
        this.patientRepository = patientRepository;
    }

    public List<PaymentResponse> getPayments(
            UUID therapistId) {

        var payments = paymentRepository.findByTherapistId(
                therapistId);

        return payments.stream().map(p -> {

            var patient = patientRepository
                    .findById(
                            p.getPatientId())
                    .orElse(null);

            String name = patient != null
                    ? patient.getFullName()
                    : "Paciente";

            return new PaymentResponse(
                    p.getId(),
                    p.getPatientId(),
                    name,
                    p.getAmount(),
                    p.getMethod(),
                    p.getStatus(),
                    p.getCreatedAt());

        }).toList();
    }
}