package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.repository.AvionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvionService {

    private final AvionRepository avionRepository;

    public AvionService(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    @Cacheable(value = "aviones", key = "#id")
    public Avion obtenerAvionPorId(Long id) {
        return avionRepository.findById(id).orElseThrow(() -> new RuntimeException("Avión no encontrado. ID: " + id));
    }

    @Cacheable(value = "aviones", key = "'todos'")
    public List<Avion> obtenerTodosLosAviones() {
        return avionRepository.findAll();
    }

    @CachePut(value = "aviones", key = "#result.avion_id")
    public Avion crearOActualizarAvion(Avion avion) {
        return avionRepository.save(avion);
    }

    @CacheEvict(value = "aviones", key = "#id")
    public void eliminarAvion(Long id) {
        avionRepository.deleteById(id);
    }

    @CacheEvict(value = "aviones", key = "'todos'")
    public void invalidarListaDeAviones() {
    }
}
