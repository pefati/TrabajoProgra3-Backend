package com.example.aereopuerto.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class VueloDTO {
    private int aeropuertoOrigen_id;
    private int aeropuertoDestino_id;
    private int avion_id;
    private int pista_id;
    private int puerta_embarque_id;
    private LocalDateTime fecha_salida;
    private LocalDateTime fecha_salida_real;
    private LocalDateTime fecha_llegada;
    private LocalDateTime fecha_llegada_real;
    private LocalDateTime hora_salida;
    private LocalDateTime hora_salida_real;
    private LocalDateTime hora_llegada;
    private LocalDateTime hora_llegada_real;
}
