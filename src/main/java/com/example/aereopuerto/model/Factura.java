package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.MetodosDePago;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Factura implements Serializable{
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_factura;

    @Column(nullable = false)
    private String situacion_fiscal;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(nullable = false)
    private String CUIL;

    @Column(nullable = false)
    private LocalDateTime fecha_emision;

    @Column(nullable = false)
    private MetodosDePago metodoDePago;


}