package com.example.aereopuerto.model;

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
@Table(name = "empleado")

public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "empleado_id")
    private Integer id;

    @Column(nullable = false, name= "fecha_nacimiento")
    private LocalDateTime fechaNacimiento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;
}