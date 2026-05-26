package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.Sexo;
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

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private LocalDateTime fecha_nacimiento;

    @Column(nullable = false, unique = true)
    private String DNI_pasaporte;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

}