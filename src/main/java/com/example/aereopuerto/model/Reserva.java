package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reserva")

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Persona persona;

    @Column(nullable = false)
    private Float valor;

    @Column(nullable = false, name= "cantidad_pasajes")
    private Integer cantidadPasajes;

    @Column(nullable = false, name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @Column(nullable = false, name="fecha_reserva")
    private LocalDateTime fechaReserva;



}