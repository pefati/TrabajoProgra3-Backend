package com.example.aereopuerto.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private int cliente_id;
    private int vuelo_id;
    private float valor;
    private int cantidad_pasajes;
    private String numero_ticket;
    private LocalDateTime fecha_reserva;
}
