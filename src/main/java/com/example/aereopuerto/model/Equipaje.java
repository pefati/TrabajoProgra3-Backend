package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.EstadoEquipaje;
import com.example.aereopuerto.model.enums.TipoEquipaje;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "equipaje")
public class Equipaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipaje_id")
    private Integer id;

    private Double peso;

    @Enumerated(EnumType.STRING)
    private TipoEquipaje tipo;

    private Double precio;

}
