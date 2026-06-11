package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MetodosDePago {
    TarjetaCredito,
    TarjetaDebito,
    Transferencia;

    @JsonCreator
    public static MetodosDePago fromString(String value) {
        if (value == null) return null;
        return MetodosDePago.valueOf(value.toUpperCase());
    }
}