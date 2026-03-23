package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.service.SessionWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionWorkflowController {

    private final SessionWorkflowService service;

    public SessionWorkflowController(SessionWorkflowService service) {
        this.service = service;
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<Void> startSession(@PathVariable UUID id) {

        service.startSession(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeSession(@PathVariable UUID id) {

        service.completeSession(id);

        return ResponseEntity.ok().build();
    }

}