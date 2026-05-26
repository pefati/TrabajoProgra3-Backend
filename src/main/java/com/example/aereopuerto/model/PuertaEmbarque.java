package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PuertaEmbarque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long puerta_embarque_id;

    @Column(nullable = false)
    private int numero;

    @ManyToOne
    @JoinColumn(name = "aeropuerto_id", nullable = false)
    private Aeropuerto aeropuerto;

}
