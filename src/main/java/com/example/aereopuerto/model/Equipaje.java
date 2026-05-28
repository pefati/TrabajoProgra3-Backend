package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.EstadoEquipaje;
import com.example.aereopuerto.model.enums.TipoEquipaje;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity

    public class Equipaje implements Serializable {

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, unique = true)
        private Long equipaje_id;

        @ManyToOne
        @JoinColumn(name = "reserva_id")
        private Reserva reserva_id;

        private Float peso;

        @Enumerated(EnumType.STRING)
        private TipoEquipaje tipo;

        @Enumerated(EnumType.STRING)
        private EstadoEquipaje estado;

    }

