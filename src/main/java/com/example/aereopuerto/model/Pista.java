package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.EstadoPista;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Pista")

public class Pista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pista_id;

    @Column(nullable = false)
    private int Numero;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPista Estado;

}
