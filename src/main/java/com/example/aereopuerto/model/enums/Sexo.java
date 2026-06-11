package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    MASCULINO,
    FEMENINO,
    OTRO;

    @JsonCreator
    public static Sexo fromString(String value) {
        if (value == null) return null;
        return Sexo.valueOf(value.toUpperCase());
    }
}