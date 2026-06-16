package com.example.aereopuerto.dto;

import lombok.Data;

@Data
public class AsientoDTO {
    private Integer id;
    private String codigo;
    private Double precioExtra;
    private Integer vueloId;
    private Boolean ocupado;
}
