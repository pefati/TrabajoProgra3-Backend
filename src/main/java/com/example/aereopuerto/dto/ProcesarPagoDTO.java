package com.example.aereopuerto.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProcesarPagoDTO {
    private String token;
    private Double amount;
    private Integer installments = 1;
    private String paymentMethodId;
    private String issuerId;
    private String payerEmail;
    private String payerDocType;
    private String payerDocNumber;
    private List<Integer> equipajeId;
    private Integer asistenciaId;
    private String cuil;
    private String situacionFiscal;
    private List<Integer> asientosSeleccionados;
    private Double asientoExtra;
    private Double servicioExtra;
}
