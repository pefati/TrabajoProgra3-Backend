package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Factura implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_factura;
    @Column(nullable = false)
    private String situacion_fiscal;
    @Column(nullable = false)
    private Reserva reserva_id;
    @ManyToOne
    @Column(nullable = false)
    private String CUIL;
    @Column(nullable = false)
    private Date fecha_emision;
    @Column(nullable = false)
    private MetodosDePago metodoDePago;


}