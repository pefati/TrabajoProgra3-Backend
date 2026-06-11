package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.MetodosDePago;
import lombok.Data;

@Data
public class CompraDTO {
    private Integer personaId;
    private Integer equipajeId;
    private Integer asistenciaId;
    private String cuil;
    private String situacionFiscal;
    private MetodosDePago metodoPago;

}
