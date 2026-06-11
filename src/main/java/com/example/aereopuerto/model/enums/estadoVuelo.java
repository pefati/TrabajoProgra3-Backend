package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum estadoVuelo {
    PROGRAMADO, BOARDING, ACTIVO, CANCELADO, REPROGRAMADO;

    @JsonCreator
    public static estadoVuelo fromString(String value) {
        if (value == null) return null;
        return estadoVuelo.valueOf(value.toUpperCase());
    }
}
