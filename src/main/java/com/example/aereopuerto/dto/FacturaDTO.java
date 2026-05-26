package com.example.aereopuerto.dto;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class FacturaDTO {
    private String situacion_fiscal;
    private int reserva_id;
    private String CUIL;
    private LocalDateTime fecha_emision;
}
