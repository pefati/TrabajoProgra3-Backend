package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AereolineaInvalidaException;
import com.example.aereopuerto.model.Aereolinea;
import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.enums.estadoAvion;
import com.example.aereopuerto.repository.AereolineaRepository;
import com.example.aereopuerto.repository.AvionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AvionService {

    private final AvionRepository avionRepository;
    private final AereolineaRepository aereolineaRepository;

    @Cacheable(value = "aviones", key = "#id")
    public Avion obtenerAvionPorId(Long id) {
        return avionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion no encontrado. ID: " + id));
    }

    @Cacheable(value = "aviones", key = "'todos'")
    public List<Avion> obtenerTodosLosAviones() {
        return avionRepository.findAll();
    }

    @CachePut(value = "aviones", key = "#result.avion_id")
    public Avion crearAvion(String identificador, float capacidadTanque, int capacidadPasajeros, String modelo, Long aereolinea_id) {


        Aereolinea al = aereolineaRepository.findById(aereolinea_id)
                .orElseThrow(() -> new AereolineaInvalidaException("La aereolinea no existe"));

        Avion a = new Avion();

        a.setAerolinea(al);
        a.setEstado(estadoAvion.DISPONIBLE);
        a.setModelo(modelo);
        a.setCapacidadTanque(capacidadTanque);
        a.setCapacidadPasajeros(capacidadPasajeros);
        a.setIdentificador(identificador);

        return avionRepository.save(a);
    }

    @CachePut(value = "aviones", key = "#result.avion_id")
    public Avion EditarAvion(Long id, Avion av) {
        Avion a = obtenerAvionPorId(id);

        a.setAerolinea(av.getAerolinea());
        a.setEstado(av.getEstado());
        a.setModelo(av.getModelo());
        a.setCapacidadTanque(av.getCapacidadTanque());
        a.setCapacidadPasajeros(av.getCapacidadPasajeros());
        a.setIdentificador(av.getIdentificador());

        return avionRepository.save(a);
    }

    @CacheEvict(value = "aviones", key = "#id")
    public void eliminarAvion(Long id) {
        avionRepository.deleteById(id);
    }

    @CacheEvict(value = "aviones", key = "'todos'")
    public void invalidarListaDeAviones() {
    }
}
