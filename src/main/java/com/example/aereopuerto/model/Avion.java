package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.estadoAvion;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "avion")

public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="avion_id")
    private Integer id;

    @Column(nullable = false)
    private String identificador;

    @Column (nullable = false, name= "capacidad_bodega")
    private Float capacidadBodega;

    @Column (nullable = false, name= "capacidad_pasajeros")
    private Integer capacidadPasajeros;

    @Column (nullable = false)
    private String modelo;


    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private estadoAvion estado;


}
