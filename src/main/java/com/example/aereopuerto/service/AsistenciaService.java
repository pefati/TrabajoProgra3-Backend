package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AsistenciaInvalidaException;
import com.example.aereopuerto.model.AsistenciaAlViajero;
import com.example.aereopuerto.model.Equipaje;
import com.example.aereopuerto.repository.AsistenciaRepository;
import com.example.aereopuerto.repository.EquipajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsistenciaService {

    @Autowired
    private final AsistenciaRepository asistenciaRepository;

    @Cacheable(value = "asistenciaAlViajero", key = "#id")
    public AsistenciaAlViajero obtenerAsistenciaPorId(Integer id) {
        return asistenciaRepository.findById(id).orElseThrow(() -> new AsistenciaInvalidaException("Asistencia al viajero no encontrada. ID: " + id));
    }

    @Cacheable(value = "asistenciasAlViajero", key = "'todos'")
    public List<AsistenciaAlViajero> obtenerTodosLasAsistencias() {
        return asistenciaRepository.findAll();
    }

    @CachePut(value = "asistenciaAlViajero", key = "#result.id")
    @CacheEvict(value = "asistenciaAlViajero", key = "'todos'")
    public AsistenciaAlViajero crearAsistencia(AsistenciaAlViajero asistenciaAlViajero) {
        return asistenciaRepository.save(asistenciaAlViajero);
    }

    @CachePut(value = "asistenciaAlViajero", key = "#result.id")
    @CacheEvict(value = "asistenciaAlViajero", key = "'todos'")
    public AsistenciaAlViajero actualizarAsistencia(Integer id, AsistenciaAlViajero asistenciaAlViajero) {

        AsistenciaAlViajero asistencia = asistenciaRepository.findById(id) .orElseThrow(() -> new AsistenciaInvalidaException("Asistencia al viajero no encontrada. ID: " + id));
        asistencia.setDescripcion(asistenciaAlViajero.getDescripcion());
        asistencia.setNombrePlan(asistenciaAlViajero.getNombrePlan());
        asistencia.setPrecio(asistenciaAlViajero.getPrecio());

        return asistenciaRepository.save(asistencia);
    }


    @Caching(evict = {
            @CacheEvict(value = "asistenciaAlViajero", key = "#id"),
            @CacheEvict(value = "asistenciaAlViajero", key = "'todos'")
    })
    public void eliminarAsistencia(Integer id) {
        asistenciaRepository.deleteById(id);
    }

    @CacheEvict(value = "asistenciaAlViajero", key = "'todos'")
    public void invalidarListaDeAsistenciasAlViajero() {
    }
}
