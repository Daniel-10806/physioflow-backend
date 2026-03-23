package com.physioflow.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.physioflow.infrastructure.persistence.repository")
public class JpaConfig {
}