package com.example.aereopuerto.service;

import com.example.aereopuerto.dto.ReservaDTO;
import com.example.aereopuerto.model.Cliente;
import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.repository.ClienteRepository;
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
    private final ClienteRepository clienteRepository;

    @Cacheable(value = "reservas", key = "#id")
    public Reserva obtenerReservaPorId(Integer id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada. ID: " + id));
    }

    @Cacheable(value = "reservas", key = "'todos'")
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    @CachePut(value = "reservas", key = "#result.id")
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @CachePut(value = "reservas", key = "#result.id")
    public Reserva actualizarReserva(Integer id, ReservaDTO reservaDTO) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Reserva no encontrada. ID: " + id));

        Cliente cliente = clienteRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado. ID: " + reservaDTO.getClienteId()));

        reserva.setCliente(cliente);
        reserva.setValor(reservaDTO.getValor());
        reserva.setCantidadPasajes(reservaDTO.getCantidadPasajes());
        reserva.setEstadoReserva(reservaDTO.getEstadoReserva());
        reserva.setFechaReserva(reservaDTO.getFechaReserva());

        return reservaRepository.save(reserva);
    }

    @CacheEvict(value = "reservas", key = "#id")
    public void eliminarReserva(Integer id) {
        reservaRepository.deleteById(id);
    }

    @CacheEvict(value = "reservas", key = "'todos'")
    public void invalidarListaDeReservas() {
    }
}
