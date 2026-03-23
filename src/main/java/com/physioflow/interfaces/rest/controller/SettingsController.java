package com.physioflow.interfaces.rest.controller;

import com.physioflow.application.service.SettingsService;
import com.physioflow.domain.model.entity.Settings;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin
public class SettingsController {

    private final SettingsService service;

    public SettingsController(SettingsService service) {
        this.service = service;
    }

    // =========================
    // GET
    // =========================

    @GetMapping
    public Settings get() {

        return service.getSettings();

    }

    // =========================
    // SAVE
    // =========================

    @PostMapping
    public Settings save(
            @RequestBody Settings data) {

        return service.save(data);

    }

}