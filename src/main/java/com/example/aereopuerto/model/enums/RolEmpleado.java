package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RolEmpleado {
    AZAFATA,
    PILOTO,
    COPILOTO;

    @JsonCreator
    public static RolEmpleado fromString(String value) {
        if (value == null) return null;
        return RolEmpleado.valueOf(value.toUpperCase());
    }
}