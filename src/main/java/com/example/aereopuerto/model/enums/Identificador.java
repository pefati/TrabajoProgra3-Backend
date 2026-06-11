package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Identificador {
    PASAPORTE, DNI;


    @JsonCreator
    public static Identificador fromString(String value) {
        if (value == null) return null;
        return Identificador.valueOf(value.toUpperCase());
    }
}
