package com.example.aereopuerto.dto;

import lombok.Data;

@Data
public class AvionDTO {
    private String identificador;
    private float capacidadTanque;
    private int capacidadPasajeros;
    private String modelo;
    private int aerolinea_id;
}
