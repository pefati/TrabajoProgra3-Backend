package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "aeropuerto")

public class Aeropuerto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long aeropuerto_id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, name="codigo_iata")
    private String codigoIata;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String pais;

}
