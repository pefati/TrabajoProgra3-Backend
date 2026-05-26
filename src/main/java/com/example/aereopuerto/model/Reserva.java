package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.ClasesVuelo;
import com.example.aereopuerto.model.enums.EstadoReserva;
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

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "Vuelo_id", nullable = false)
    private Vuelo vuelo;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private int cantidad_Pasajes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClasesVuelo clasesVuelo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @Column(nullable = false, unique = true)
    private String numero_ticket;

    @Column(nullable = false)
    private LocalDateTime fecha_reserva;
}