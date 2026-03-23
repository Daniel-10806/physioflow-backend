package com.physioflow.application.dto.request;

public record SessionRecordRequest(

        String subjective,
        String objective,
        String assessment,
        String plan,
        String treatment,
        String progressNotes

) {
}