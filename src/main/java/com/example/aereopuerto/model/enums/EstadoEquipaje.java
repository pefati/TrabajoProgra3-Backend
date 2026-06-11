package com.example.aereopuerto.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EstadoEquipaje {
    ENTREGADO, DESPACHADO, DAÑADO, EXTRAVIADO;

    @JsonCreator
    public static EstadoEquipaje fromString(String value) {
        if (value == null) return null;
        return EstadoEquipaje.valueOf(value.toLowerCase());
    }
}
