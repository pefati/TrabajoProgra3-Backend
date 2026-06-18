package com.example.aereopuerto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "asiento")
public class Asiento {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo; // 1A, 1B, 7F, etc.

    private Double precioExtra;

    @ManyToOne
    @JoinColumn(name = "avion_id")
    private Avion avion;

    private Boolean ocupado;
}
