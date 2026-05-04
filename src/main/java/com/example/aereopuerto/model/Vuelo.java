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
    @ManyToOne
    @JoinColumn(name = "aeropuerto_origen_id", nullable = false)
    private Aeropuerto aeropuerto_origen;
    @ManyToOne
    @JoinColumn(name = "aeropuerto_destino_id", nullable = false)
    private Aeropuerto aeropuerto_destino;
    @ManyToOne
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion_id;
    @ManyToOne
    @JoinColumn(name = "pista_id", nullable = false)
    private Pista pista_id;
    @Column(nullable = false)
    private Date fecha_salida;

    private Date fecha_salida_real;

    @Column(nullable = false)
    private Date fecha_llegada;

    private Date fecha_llegada_real;

    @Column(nullable = false)
    private Time hora_salida;

    private Time hora_salida_real;

    @Column(nullable = false)
    private Time hora_llegada;

    private Time hora_llegada_real;

    @Column(nullable = false)
    private estadoVuelo estado;

    @ManyToOne
    @JoinColumn(name = "puerta_embarque_id", nullable = false)
    private PuertaEmbarque puerta_embarque_id;

    private boolean escala;



}
