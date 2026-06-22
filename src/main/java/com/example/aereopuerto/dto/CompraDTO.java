package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.MetodosDePago;
import lombok.Data;

import java.util.List;

@Data
public class CompraDTO {
    private Integer personaId;
    private List<Integer> equipajeId;
    private Integer asistenciaId;
    private String cuil;
    private String situacionFiscal;
    private MetodosDePago metodoPago;
    private List<Integer> asientosSeleccionados;
    private Double asientoExtra;
    private Double servicioExtra;
    private Integer cuotas;

}
