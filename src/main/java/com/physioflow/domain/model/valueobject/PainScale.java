package com.physioflow.domain.model.valueobject;

import com.physioflow.domain.model.exception.DomainException;

import java.util.Objects;

public final class PainScale {

    private final int value;

    public PainScale(int value) {
        if (value < 0 || value > 10) {
            throw new DomainException("Escala de dolor debe estar entre 0 y 10");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PainScale))
            return false;
        PainScale that = (PainScale) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}