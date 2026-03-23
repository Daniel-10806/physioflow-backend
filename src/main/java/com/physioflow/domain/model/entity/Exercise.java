package com.physioflow.domain.model.entity;

import com.physioflow.domain.model.enumtype.BodyRegion;
import com.physioflow.domain.model.exception.DomainException;

import java.util.UUID;

public class Exercise {

    private final UUID id;
    private final String name;
    private final BodyRegion bodyRegion;
    private final int repetitions;
    private final int sets;

    public Exercise(UUID id, String name, BodyRegion bodyRegion, int repetitions, int sets) {

        if (name == null || name.isBlank()) {
            throw new DomainException("El nombre del ejercicio no puede estar vacío");
        }

        if (repetitions <= 0 || sets <= 0) {
            throw new DomainException("Las repeticiones y series deben ser mayores que cero");
        }

        this.id = id;
        this.name = name;
        this.bodyRegion = bodyRegion;
        this.repetitions = repetitions;
        this.sets = sets;
    }

    public UUID getId() {
        return id;
    }

    public BodyRegion getBodyRegion() {
        return bodyRegion;
    }
}