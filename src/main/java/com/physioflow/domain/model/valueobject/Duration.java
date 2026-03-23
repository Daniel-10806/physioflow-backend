package com.physioflow.domain.model.valueobject;

import com.physioflow.domain.model.exception.DomainException;

import java.util.Objects;

public final class Duration {

    private final int minutes;

    public Duration(int minutes) {
        if (minutes <= 0) {
            throw new DomainException("La duración debe ser mayor que cero");
        }
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Duration))
            return false;
        Duration duration = (Duration) o;
        return minutes == duration.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }
}