package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.request.CreateSessionRecordRequest;
import com.physioflow.application.service.CreateSessionRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionRecordController {

    private final CreateSessionRecordService service;

    public SessionRecordController(CreateSessionRecordService service) {
        this.service = service;
    }

    @PostMapping("/{sessionId}/record")
    public void createRecord(
            @PathVariable UUID sessionId,
            @RequestBody CreateSessionRecordRequest request) {

        service.createRecord(sessionId, request);
    }
}