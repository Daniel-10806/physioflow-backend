package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.response.PatientHistoryResponse;
import com.physioflow.application.service.PatientHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientHistoryController {

    private final PatientHistoryService service;

    public PatientHistoryController(PatientHistoryService service) {
        this.service = service;
    }

    @GetMapping("/{patientId}/history")
    public List<PatientHistoryResponse> getHistory(
            @PathVariable UUID patientId) {

        return service.getPatientHistory(patientId);
    }
}