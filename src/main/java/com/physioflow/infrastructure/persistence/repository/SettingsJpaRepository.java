package com.physioflow.infrastructure.persistence.repository;

import com.physioflow.domain.model.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsJpaRepository
        extends JpaRepository<Settings, Long> {
}