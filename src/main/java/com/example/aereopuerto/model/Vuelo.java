package com.example.aereopuerto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroVuelo;
    @Column(nullable = false)

    private String origen;
    @Column(nullable = false)

    private String destino;
    @Column(nullable = false)

    private String aerolinea;
    @Column(nullable = false)

    private LocalDateTime fechaSalida;
    @Column(nullable = false)

    private String estado; //cambiar a enum;

    public Vuelo() {
    }

    public Vuelo(String numeroVuelo, String origen, String destino, String aerolinea, LocalDateTime fechaSalida, String estado) {
        this.numeroVuelo = numeroVuelo;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
