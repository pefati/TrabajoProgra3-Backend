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
@Table(name = "reserva")

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false, name= "cantidad_pasajes")
    private int cantidadPasajes;

    @Column(nullable = false, name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @Column(nullable = false, name="fecha_reserva")
    private LocalDateTime fechaReserva;



}