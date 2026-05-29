package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente")

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cliente_id")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private LocalDate fecha_nacimiento;

    @Enumerated(EnumType.STRING)
    private Identificador identificador;

    @Column(nullable = false, unique = true, name= "numero_identificador")
    private String numeroIdentificador;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

}