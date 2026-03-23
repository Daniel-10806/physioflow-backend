package com.physioflow.application.service;

import com.physioflow.domain.model.entity.Settings;
import com.physioflow.infrastructure.persistence.repository.SettingsJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private final SettingsJpaRepository repo;

    private Settings cache;

    public SettingsService(SettingsJpaRepository repo) {
        this.repo = repo;
    }

    // =========================
    // GET GLOBAL SETTINGS
    // =========================

    public Settings getSettings() {

        if (cache != null) {
            return cache;
        }

        cache = repo.findById(1L)
                .orElseGet(() -> {

                    Settings s = new Settings();

                    s.setId(1L);
                    s.setSystemName("PhysioFlow");
                    s.setCurrency("S/");
                    s.setTax(18);
                    s.setBillingSeries("F001");
                    s.setBillingEnabled(true);
                    s.setIgv(18);

                    return repo.save(s);

                });

        return cache;
    }

    // =========================
    // SAVE GLOBAL SETTINGS
    // =========================

    public Settings save(Settings data) {

        data.setId(1L);

        cache = repo.save(data);

        return cache;

    }

}