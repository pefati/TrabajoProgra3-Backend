package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.MetodosDePago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Integer personaId;

    private Integer equipajeId;

    private Integer asistenciaId;

    private String cuil;

    private String situacionFiscal;

    private MetodosDePago metodoPago;
}