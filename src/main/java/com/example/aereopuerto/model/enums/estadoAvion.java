package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum estadoAvion {
    DISPONIBLE, MANTENIMIENTO, ACTIVO, BAJA;

    @JsonCreator
    public static estadoAvion fromString(String value) {
        if (value == null) return null;
        return estadoAvion.valueOf(value.toUpperCase());
    }
}
