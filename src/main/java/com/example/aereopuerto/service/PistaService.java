package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Pista;
import com.example.aereopuerto.repository.PistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PistaService {

    private final PistaRepository pistaRepository;

    @Cacheable(value = "pistas", key = "#id")
    public Pista obtenerPistaPorId(Long id) {
        return pistaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pista no encontrada. ID: " + id));
    }

    @Cacheable(value = "pistas", key = "'todos'")
    public List<Pista> obtenerTodasLasPistas() {
        return pistaRepository.findAll();
    }

    @CachePut(value = "pistas", key = "#result.pista_id")
    public Pista crearOActualizarPista(Pista pista) {
        return pistaRepository.save(pista);
    }

    @CacheEvict(value = "pistas", key = "#id")
    public void eliminarPista(Long id) {
        pistaRepository.deleteById(id);
    }

    @CacheEvict(value = "pistas", key = "'todos'")
    public void invalidarListaDePistas() {
    }
}
