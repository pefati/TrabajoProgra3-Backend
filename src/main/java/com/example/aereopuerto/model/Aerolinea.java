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
public class Aerolinea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long aerolinea_id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String codigo_iata;
}
