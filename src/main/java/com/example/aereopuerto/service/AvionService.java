package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.repository.AvionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AvionService {

    @Autowired
    private final AvionRepository avionRepository;

    @Cacheable(value = "aviones", key = "#id")
    public Avion obtenerAvionPorId(Integer id) {
        return avionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avion no encontrado. ID: " + id));
    }

    @Cacheable(value = "aviones", key = "'todos'")
    public List<Avion> obtenerTodosLosAviones() {
        return avionRepository.findAll();
    }

    @CachePut(value = "aviones", key = "#result.id")
    public Avion crearAvion(Avion avion) {
        return avionRepository.save(avion);
    }

    @CachePut(value = "aviones", key = "#result.id")
    public Avion EditarAvion(Integer id, Avion av) {
        Avion a = obtenerAvionPorId(id);
        a.setCapacidadBodega(av.getCapacidadBodega());
        a.setCapacidadPasajeros(av.getCapacidadPasajeros());
        a.setIdentificador(av.getIdentificador());
        a.setEstado(av.getEstado());
        a.setModelo(av.getModelo());

        return avionRepository.save(a);
    }

    @CacheEvict(value = "aviones", key = "#id")
    public void eliminarAvion(Integer id) {
        avionRepository.deleteById(id);
    }

    @CacheEvict(value = "aviones", key = "'todos'")
    public void invalidarListaDeAviones() {
    }
}
