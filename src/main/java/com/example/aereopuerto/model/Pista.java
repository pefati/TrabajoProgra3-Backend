package com.example.aereopuerto.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pista_id;

    @Column(nullable = false)
    private int Numero;

    @Column(nullable = false)
    private EstadoPista Estado;

}
