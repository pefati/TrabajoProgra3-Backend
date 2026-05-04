package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long avion_id;

    @Column(nullable = false)
    private String identificador;

    @Column (nullable = false)
    private Float capacidadTanque;

    @Column (nullable = false)
    private int capacidadPasajeros;

    @Column (nullable = false)
    private String modelo;

    @ManyToOne
    @JoinColumn(name = "aerolinea_id")
    @Column (nullable = false)
    private Aerolinea aerolinea_id;

    @Column (nullable = false)
    private estadoAvion estado;


}
