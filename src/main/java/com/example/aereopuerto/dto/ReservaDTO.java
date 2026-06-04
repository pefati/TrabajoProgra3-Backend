package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.EstadoReserva;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private Integer clienteId;
    private Float valor;
    private Integer cantidadPasajes;
    private EstadoReserva estadoReserva;
    private LocalDateTime fechaReserva;
}
