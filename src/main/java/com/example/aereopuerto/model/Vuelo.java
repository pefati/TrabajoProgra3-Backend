package com.example.aereopuerto.model;

import com.example.aereopuerto.model.enums.estadoVuelo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
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
    @Column(nullable = false, unique = true)
    private Long vuelo_id;

    @ManyToOne
    @JoinColumn(name = "aeropuerto_id", nullable = false)
    private Aeropuerto aeropuerto_origen;

    @ManyToOne
    @JoinColumn(name = "aeropuerto_id", nullable = false)
    private Aeropuerto aeropuerto_destino;

    @ManyToOne
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion_id;

    @ManyToOne
    @JoinColumn(name = "pista_id", nullable = false)
    private Pista pista_id;

    @ManyToOne
    @JoinColumn(name = "puerta_embarque_id", nullable = false)
    private PuertaEmbarque puertaEmbarque;

    @Column(nullable = false)
    private LocalDateTime fecha_salida;

    @Column(nullable = false)
    private LocalDateTime fecha_salida_real;

    @Column(nullable = false)
    private LocalDateTime fecha_llegada;

    @Column(nullable = false)
    private LocalDateTime fecha_llegada_real;

    @Column(nullable = false)
    private LocalDateTime hora_salida;

    private LocalDateTime hora_salida_real;
    @Column(nullable = false)
    private LocalDateTime hora_llegada;

    @Column(nullable = false)
    private LocalDateTime hora_llegada_real;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private estadoVuelo estado;

    private boolean escala;
}