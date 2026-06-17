package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.ClasesVuelo;

import lombok.Data;

import java.util.List;

@Data
public class PasajeDTO {

    private String codigoPasaje;
    private Integer asientoId;
    private ClasesVuelo clasesVuelo;
    private Integer vueloId;
    private List<Integer> equipajeIds;
    private Integer reservaId;
}
