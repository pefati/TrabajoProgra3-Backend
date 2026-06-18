package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.estadoAvion;
import lombok.Data;

@Data
public class AvionDTO {
    private String identificador;
    private Double capacidadBodega;
    private Integer capacidadPasajeros;
    private String modelo;
    private estadoAvion estado;
}
