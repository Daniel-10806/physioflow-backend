package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.request.ReportFilterRequest;
import com.physioflow.application.dto.response.ReportSummaryResponse;
import com.physioflow.application.dto.response.ReportRowResponse;
import com.physioflow.application.service.ReportService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public ReportSummaryResponse summary() {

        UUID therapistId = UUID.fromString(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());

        return service.getSummary(therapistId);
    }

    @PostMapping("/search")
    public List<ReportRowResponse> search(
            @RequestBody ReportFilterRequest filter) {

        UUID therapistId = UUID.fromString(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());

        // solo si no viene en el filtro
        if (filter.getTherapistId() == null) {
            filter.setTherapistId(therapistId);
        }

        return service.search(filter);
    }
}