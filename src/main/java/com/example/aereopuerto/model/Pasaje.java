package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.ClasesVuelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Pasaje")
public class Pasaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String codigo_pasaje;

    @Column
    private String asiento;

    @Column
    @Enumerated(EnumType.STRING)
    private ClasesVuelo clasesVuelo;

    @ManyToOne
    @JoinColumn(name = "Vuelo_id", nullable = false)
    private Vuelo vuelo;

    @ManyToOne
    @JoinColumn(name= "equipaje_id", nullable = false)
    private Equipaje equipaje;

    @ManyToOne
    @JoinColumn(name = "Reserva_id", nullable = false)
    private Reserva reserva;

}
