package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.ClasesVuelo;

import lombok.Data;

@Data
public class PasajeDTO {

    private String codigoPasaje;
    private Integer asientoId;
    private ClasesVuelo clasesVuelo;
    private Integer vueloId;
    private Integer equipajeId;
    private Integer reservaId;
}
