package com.example.aereopuerto.model;


import com.example.aereopuerto.model.enums.ClasesVuelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pasaje")
public class Pasaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "pasaje_id")
    private Long id;

    @Column(name = "codigo_pasaje")
    private String codigoPasaje;

    private String asiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "clase")
    private ClasesVuelo clasesVuelo;

    @ManyToOne
    @JoinColumn(name = "vuelo_id", nullable = false)
    private Vuelo vuelo;

    @ManyToOne
    @JoinColumn(name= "equipaje_id", nullable = false)
    private Equipaje equipaje;

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

}
