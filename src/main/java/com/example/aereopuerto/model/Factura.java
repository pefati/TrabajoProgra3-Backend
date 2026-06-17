package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.MetodosDePago;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "factura")

public class Factura implements Serializable{
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer id;

    @Column(nullable = false, name="situacion_fiscal")
    private String situacionFiscal;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(nullable = false)
    private String CUIL;

    @Column(nullable = false, name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(nullable = false, name = "metodo_pago", length = 20)
    @Enumerated(EnumType.STRING)
    private MetodosDePago metodoDePago;


}