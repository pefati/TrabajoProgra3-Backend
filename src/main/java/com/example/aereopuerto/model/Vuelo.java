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
@Table(name = "vuelo")

public class Vuelo implements Serializable {

    /**
     * SerialVersionUID es para objetos serializables
     * Esto es para que redis pueda identificar el objeto y los cambios en el objeto en java
     * que si bien java lo hace auto, puede fallar, asiq se define
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vuelo_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "aeropuerto_origen_id", nullable = false)
    private Aeropuerto aeropuertoOrigen;

    @ManyToOne
    @JoinColumn(name = "aeropuerto_destino_id", nullable = false)
    private Aeropuerto aeropuertoDestino;

    @ManyToOne
    @JoinColumn(name = "avion_id", nullable = false)
    private Avion avion;

    @Column(nullable = false, name= "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(nullable = false, name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Column(nullable = false, name = "hora_salida")
    private LocalDateTime horaSalida;

    @Column(nullable = false, name = "hora_llegada")
    private LocalDateTime horaLlegada;

    @Column(nullable = false, name = "estado")
    @Enumerated(EnumType.STRING)
    private estadoVuelo estado;

    @Column(nullable = false, name = "precio_vuelo")
    private Double precioVuelo;

    private Boolean escala;
}