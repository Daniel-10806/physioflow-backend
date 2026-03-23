package com.physioflow.domain.model.valueobject;

import com.physioflow.domain.model.exception.DomainException;

import java.util.Objects;

public final class RangeOfMotion {

    private final int degrees;

    public RangeOfMotion(int degrees) {
        if (degrees < 0 || degrees > 180) {
            throw new DomainException("Rango de movimiento debe estar entre 0 y 180 grados");
        }
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getValue() {
        return degrees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RangeOfMotion))
            return false;
        RangeOfMotion that = (RangeOfMotion) o;
        return degrees == that.degrees;
    }

    @Override
    public int hashCode() {
        return Objects.hash(degrees);
    }
}