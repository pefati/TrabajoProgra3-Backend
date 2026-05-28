package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;

    @Cacheable(value = "reservas", key = "#id")
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada. ID: " + id));
    }

    @Cacheable(value = "reservas", key = "'todos'")
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    @CachePut(value = "reservas", key = "#result.id")
    public Reserva crearOActualizarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @CacheEvict(value = "reservas", key = "#id")
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    @CacheEvict(value = "reservas", key = "'todos'")
    public void invalidarListaDeReservas() {
    }
}
