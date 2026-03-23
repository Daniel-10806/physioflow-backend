package com.physioflow.application.dto.response;

import java.util.UUID;

public record SessionResponse(
        UUID id,
        UUID patientId) {
}