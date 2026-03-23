package com.physioflow.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(

        UUID id,

        UUID patientId,

        String patientName,

        double amount,

        String method,

        String status,

        LocalDateTime createdAt

) {
}