package com.example.aereopuerto.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vuelo implements Serializable {

    /**
     * SerialVersionUID es para objetos serializables
     * Esto es para que redis pueda identificar el objeto y los cambios en el objeto en java
     * que si bien java lo hace auto, puede fallar, asiq se define
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vuelo_id;
    @Column(nullable = false, unique = true)

    @ManyToOne
    @JoinColumn(name = "aeropuerto_id")
    private Aeropuerto aeropuerto_origen;
    @Column(nullable = false)

    @ManyToOne
    @JoinColumn(name = "aeropuerto_id")
    private Aeropuerto aeropuerto_destino;
    @Column(nullable = false)

    @ManyToOne
    @JoinColumn(name = "avion_id")
    private Avion avion_id;
    @Column(nullable = false)

    @ManyToOne
    @JoinColumn(name = "pista_id")
    private Pista pista_id;
    @Column(nullable = false)

    private Date fecha_salida;
    @Column(nullable = false)

    private Date fecha_salida_real;

    private Date fecha_llegada;
    @Column(nullable = false)

    private Date fecha_llegada_real;

    private Time hora_salida;
    @Column(nullable = false)

    private Time hora_salida_real;

    private Time hora_llegada;
    @Column(nullable = false)

    private Time hora_llegada_real;

    private estadoVuelo estado;
    @Column(nullable = false)

    @ManyToOne
    @JoinColumn(name = "puerta_embarque_id")
    private PuertaEmbarque puerta_embarque_id;
    @Column(nullable = false)

    private boolean escala;



}