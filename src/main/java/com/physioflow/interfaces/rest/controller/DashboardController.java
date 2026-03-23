package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.response.DashboardSummaryResponse;
import com.physioflow.application.service.DashboardService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {

        UUID therapistId = UUID.fromString(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        return service.getSummary(therapistId);
    }
}