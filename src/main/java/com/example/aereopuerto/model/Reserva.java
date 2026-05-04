package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Reserva implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Cliente cliente;
    @Column(nullable = false)
    private Vuelo vuelo;
    @Column(nullable = false)
    private float valor;
    @Column(nullable = false)
    private int cantidad_Pasajes;
    @Column(nullable = false)
    private ClasesVuelo clasesVuelo;
    @Column(nullable = false)
    private EstadoReserva estadoReserva;
    @Column(nullable = false, unique = true)
    private String numero_ticket;
    @Column(nullable = false)
    private Date fecha_reserva;

}