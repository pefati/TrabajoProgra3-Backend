package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "asistencia_al_viajero")

public class AsistenciaAlViajero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="asistencia_id")
    private Integer id;

    @Column(nullable = false, name = "nombre_plan")
    private String nombrePlan;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

