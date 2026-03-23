package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.dto.response.StatisticsResponse;
import com.physioflow.application.service.StatisticsService;
import com.physioflow.application.service.RoleService;
import com.physioflow.domain.model.enumtype.Role;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService service;
    private final RoleService roleService;

    public StatisticsController(
            StatisticsService service,
            RoleService roleService) {

        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping
    public StatisticsResponse getStats() {

        if (!roleService.hasRole(Role.ROLE_ADMIN)
                && !roleService.hasRole(Role.ROLE_THERAPIST)) {

            throw new RuntimeException("No autorizado");
        }

        UUID therapistId = (UUID) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return service.getStats(therapistId);
    }
}