package com.physioflow.application.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateSessionRequest(
                UUID patientId,
                String patientName,
                String patientEmail,
                LocalDate sessionDate,
                LocalTime sessionTime,
                int durationMinutes,
                String type) {
}