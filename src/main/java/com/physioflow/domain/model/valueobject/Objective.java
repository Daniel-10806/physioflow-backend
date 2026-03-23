package com.physioflow.domain.model.valueobject;

import com.physioflow.domain.model.exception.DomainException;

import java.util.Objects;

public final class Objective {

    private final String description;

    public Objective(String description) {
        if (description == null || description.isBlank()) {
            throw new DomainException("El objetivo no puede estar vacío");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Objective))
            return false;
        Objective that = (Objective) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}