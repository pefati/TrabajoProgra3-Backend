package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "persona")

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "persona_id")
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = true, name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Identificador identificador;

    @Column(nullable = true, unique = true, name= "numero_identificador")
    private String numeroIdentificador;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

}