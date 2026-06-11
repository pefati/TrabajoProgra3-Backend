package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EstadoReserva {
    ACTIVO,
    BAJA,
    PROCESANDO;

    @JsonCreator
    public static EstadoReserva fromString(String value) {
        if (value == null) return null;
        return EstadoReserva.valueOf(value.toUpperCase());
    }
}