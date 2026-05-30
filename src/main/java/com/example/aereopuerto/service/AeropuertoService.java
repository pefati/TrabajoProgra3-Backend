package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.repository.AeropuertoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AeropuertoService {

    @Autowired
    private final AeropuertoRepository aeropuertoRepository;

    @Cacheable(value = "aeropuertos", key = "#id")
    public Aeropuerto obtenerAeropuertoPorId(Integer id) {
        return aeropuertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeropuerto no encontrado. ID: " + id));
    }

    @Cacheable(value = "aeropuertos", key = "'todos'")
    public List<Aeropuerto> obtenerTodosLosAeropuertos() {
        return aeropuertoRepository.findAll();
    }

    @CachePut(value = "aeropuertos", key = "#result.id")
    public Aeropuerto crearAeropuerto(Aeropuerto aeropuerto) {

        return aeropuertoRepository.save(aeropuerto);
    }

    @CachePut(value = "aeropuertos", key = "#result.id")
    public Aeropuerto EditarAeropuerto(Integer id, Aeropuerto aeropuerto) {
        Aeropuerto a= aeropuertoRepository.findById(id).orElseThrow();
        a.setCiudad(aeropuerto.getCiudad());
        a.setPais(aeropuerto.getPais());
        a.setNombre(aeropuerto.getNombre());
        a.setCodigoIata(aeropuerto.getCodigoIata());

        return aeropuertoRepository.save(a);
    }

    @CacheEvict(value = "aeropuertos", key = "#id")
    public void eliminarAeropuerto(Integer id) {
        aeropuertoRepository.deleteById(id);
    }

    @CacheEvict(value = "aeropuertos", key = "'todos'")
    public void invalidarListaDeAeropuertos() {
    }
}
