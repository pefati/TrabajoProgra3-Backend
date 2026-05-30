package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.MetodosDePago;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class FacturaDTO {
    private String situacionFiscal;
    private int reservaId;
    private String CUIL;
    private LocalDateTime fechaEmision;
    private MetodosDePago metodoDePago;
}
