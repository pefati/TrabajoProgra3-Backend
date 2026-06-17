package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AeropuertoInvalidoException;
import com.example.aereopuerto.Specifications.AeropuertoSpecification;
import com.example.aereopuerto.dto.AeropuertoDTO;
import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.repository.AeropuertoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
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
                    .orElseThrow(() -> new AeropuertoInvalidoException("Aeropuerto no encontrado. ID: " + id));
    }

    @Cacheable(value = "aeropuertos", key = "'todos'")
    public List<Aeropuerto> obtenerTodosLosAeropuertos() {
        return aeropuertoRepository.findAll();
    }

    @CachePut(value = "aeropuertos", key = "#result.id")
    @CacheEvict(value = "aeropuertos", key = "'todos'")
    public Aeropuerto crearAeropuerto(AeropuertoDTO dto) {
        validarCodigoIataUnico(dto.getCodigoIata(), null);

        Aeropuerto aeropuerto = Aeropuerto.builder()
                .nombre(dto.getNombre())
                .codigoIata(dto.getCodigoIata().toUpperCase())
                .ciudad(dto.getCiudad())
                .pais(dto.getPais())
                .build();

        return aeropuertoRepository.save(aeropuerto);
    }

    @CachePut(value = "aeropuertos", key = "#result.id")
    @CacheEvict(value = "aeropuertos", key = "'todos'")
    public Aeropuerto EditarAeropuerto(Integer id, AeropuertoDTO dto) {
        Aeropuerto a = aeropuertoRepository.findById(id)
                .orElseThrow(() -> new AeropuertoInvalidoException("Aeropuerto no encontrado. ID: " + id));

        validarCodigoIataUnico(dto.getCodigoIata(), id);

        a.setNombre(dto.getNombre());
        a.setCodigoIata(dto.getCodigoIata().toUpperCase());
        a.setCiudad(dto.getCiudad());
        a.setPais(dto.getPais());

        return aeropuertoRepository.save(a);
    }

    // Valida que el código IATA no esté en uso por otro aeropuerto
    private void validarCodigoIataUnico(String codigoIata, Integer excludeId) {
        aeropuertoRepository.findByCodigoIata(codigoIata.toUpperCase())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        throw new AeropuertoInvalidoException(
                                "Ya existe un aeropuerto con el código IATA: " + codigoIata.toUpperCase()
                        );
                    }
                });
    }

    @Caching(evict = {
            @CacheEvict(value = "aeropuertos", key = "#id"),
            @CacheEvict(value = "aeropuertos", key = "'todos'")
    })
    public void eliminarAeropuerto(Integer id) {
        aeropuertoRepository.deleteById(id);
    }

    public List<Aeropuerto> buscarAeropuertosConFiltros(
            String nombre,
            String codigoIata,
            String ciudad,
            String pais) {

        Specification<Aeropuerto> spec = Specification
                .where(AeropuertoSpecification.porNombre(nombre))
                .and(AeropuertoSpecification.porCodigoIata(codigoIata))
                .and(AeropuertoSpecification.porCiudad(ciudad))
                .and(AeropuertoSpecification.porPais(pais));

        return aeropuertoRepository.findAll(spec);
    }

    @CacheEvict(value = "aeropuertos", key = "'todos'")
    public void invalidarListaDeAeropuertos() {
    }
}
