package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MetodosDePago {
    TARJETA_CREDITO,
    TARJETA_DEBITO,
    TRANSFERENCIA;

    @JsonCreator
    public static MetodosDePago fromString(String value) {
        if (value == null) return null;
        return MetodosDePago.valueOf(value.toUpperCase());
    }
}