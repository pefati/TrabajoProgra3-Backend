package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.AvionInvalidoException;
import com.example.aereopuerto.Specifications.AvionSpecification;
import com.example.aereopuerto.dto.AvionDTO;
import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.enums.estadoAvion;
import com.example.aereopuerto.repository.AvionRepository;
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

public class AvionService {

    @Autowired
    private final AvionRepository avionRepository;

    @Cacheable(value = "aviones", key = "#id")
    public Avion obtenerAvionPorId(Integer id) {
        return avionRepository.findById(id)
                .orElseThrow(() -> new AvionInvalidoException("Avion no encontrado. ID: " + id));
    }

    @Cacheable(value = "aviones", key = "'todos'")
    public List<Avion> obtenerTodosLosAviones() {
        return avionRepository.findAll();
    }

    @CachePut(value = "aviones", key = "#result.id")
    @CacheEvict(value = "aviones", key = "'todos'")
    public Avion crearAvion(Avion avion) {
        validarAvion(avion);
        return avionRepository.save(avion);
    }

    @CachePut(value = "aviones", key = "#result.id")
    @CacheEvict(value = "aviones", key = "'todos'")
    public Avion EditarAvion(Integer id, AvionDTO av) {
        Avion a = obtenerAvionPorId(id);
        a.setCapacidadBodega(av.getCapacidadBodega());
        a.setCapacidadPasajeros(av.getCapacidadPasajeros());
        a.setIdentificador(av.getIdentificador());
        a.setEstado(av.getEstado());
        a.setModelo(av.getModelo());

        validarAvion(a);

        return avionRepository.save(a);
    }

    private void validarAvion(Avion avion) {

        // Capacidad de pasajeros positiva
        if (avion.getCapacidadPasajeros() == null || avion.getCapacidadPasajeros() <= 0) {
            throw new AvionInvalidoException(
                    "La capacidad de pasajeros debe ser mayor a 0.");
        }

        // Capacidad de bodega positiva
        if (avion.getCapacidadBodega() == null || avion.getCapacidadBodega() <= 0) {
            throw new AvionInvalidoException(
                    "La capacidad de bodega debe ser mayor a 0.");
        }

        // Formato LV-A001
        String identificador = avion.getIdentificador();

        if (identificador == null ||
                !identificador.matches("^LV-A\\d+$")) {

            throw new AvionInvalidoException(
                    "El identificador debe comenzar con LV-A seguido de números. Ej: LV-A1, LV-A25, LV-A001.");
        }

    }


    @Caching(evict = {
            @CacheEvict(value = "aviones", key = "#id"),
            @CacheEvict(value = "aviones", key = "'todos'")
    })
    public void eliminarAvion(Integer id) {
        avionRepository.deleteById(id);
    }


    public List<Avion> buscarAvionesConFiltros(
            String identificador,
            String modelo,
            estadoAvion estado,
            Integer capacidadPasajerosMin,
            Integer capacidadPasajerosMax,
            Float capacidadBodegaMin,
            Float capacidadBodegaMax) {

        Specification<Avion> spec = Specification
                .where(AvionSpecification.porIdentificador(identificador))
                .and(AvionSpecification.porModelo(modelo))
                .and(AvionSpecification.porEstado(estado))
                .and(AvionSpecification.capacidadPasajerosMinimaDesde(capacidadPasajerosMin))
                .and(AvionSpecification.capacidadPasajerosMaximaHasta(capacidadPasajerosMax))
                .and(AvionSpecification.capacidadBodegaMinimaDesde(capacidadBodegaMin))
                .and(AvionSpecification.capacidadBodegaMaximaHasta(capacidadBodegaMax));

        return avionRepository.findAll(spec);
    }

    @CacheEvict(value = "aviones", key = "'todos'")
    public void invalidarListaDeAviones() {
    }
}
