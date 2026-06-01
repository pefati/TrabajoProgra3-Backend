package com.example.aereopuerto.service;

import com.example.aereopuerto.dto.FacturaDTO;
import com.example.aereopuerto.dto.PasajeDTO;
import com.example.aereopuerto.model.*;
import com.example.aereopuerto.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasajeService {
    @Autowired
    private final PasajeRepository pasajeRepository;
    private final VueloRepository vueloRepository;
    private final EquipajeRepository  equipajeRepository;
    private final ReservaRepository reservaRepository;

    @Cacheable(value = "pasajes", key = "#id")
    public Pasaje obtenerPasajePorId(Integer id) {
        return pasajeRepository.findById(id).orElseThrow(() -> new RuntimeException("Pasaje no encontrado. ID: " + id));
    }

    @Cacheable(value = "pasajes", key = "'todos'")
    public List<Pasaje> obtenerTodasLosPasajes() {
        return pasajeRepository.findAll();
    }

    @CachePut(value = "pasajes", key = "#result.id")
    public Pasaje crearPasaje(Pasaje pasaje) {
        return pasajeRepository.save(pasaje);
    }

    @CachePut(value = "pasajes", key = "#result.id")
    public Pasaje editarPasaje(Integer id, PasajeDTO pasajeDTO) {

        Pasaje pasajeExistente = pasajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasaje no encontrado"));

        Vuelo vuelo = vueloRepository.findById(pasajeDTO.getVueloId())
                .orElseThrow(() -> new RuntimeException("Vuelo no encontrado"));

        Equipaje equipaje = equipajeRepository.findById(pasajeDTO.getEquipajeId())
                .orElseThrow(() -> new RuntimeException("Equipaje no encontrado"));

        Reserva reserva = reservaRepository.findById(pasajeDTO.getReservaId())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        pasajeExistente.setCodigoPasaje(pasajeDTO.getCodigoPasaje());
        pasajeExistente.setAsiento(pasajeDTO.getAsiento());
        pasajeExistente.setClasesVuelo(pasajeDTO.getClasesVuelo());
        pasajeExistente.setVuelo(vuelo);
        pasajeExistente.setEquipaje(equipaje);
        pasajeExistente.setReserva(reserva);

        return pasajeRepository.save(pasajeExistente);
    }

    @CacheEvict(value = "pasajes", key = "#id")
    public void eliminarPasaje(Integer id) {
        pasajeRepository.deleteById(id);
    }

    @CacheEvict(value = "pasajes", key = "'todos'")
    public void invalidarListaDePasajes() {
    }
}
