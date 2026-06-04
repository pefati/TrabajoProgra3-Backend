package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.estadoVuelo;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class VueloDTO {
    private Integer aeropuertoOrigenId;
    private Integer aeropuertoDestinoId;
    private Integer avionId;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private LocalDateTime horaSalida;
    private LocalDateTime horaLlegada;
    private estadoVuelo estado;
    private Boolean escala;
}
