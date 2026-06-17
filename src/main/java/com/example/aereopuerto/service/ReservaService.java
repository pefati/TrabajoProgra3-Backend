package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.PersonaInvalidaException;
import com.example.aereopuerto.Exceptions.ReservaInvalidaException;
import com.example.aereopuerto.Specifications.ReservaSpecification;
import com.example.aereopuerto.auth.entity.Role;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.auth.repository.UserRepository;
import com.example.aereopuerto.dto.ReservaDTO;
import com.example.aereopuerto.model.Pasaje;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.model.enums.EstadoReserva;
import com.example.aereopuerto.repository.PasajeRepository;
import com.example.aereopuerto.repository.PersonaRepository;
import com.example.aereopuerto.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;
    private final PersonaRepository personaRepository;
    private final PasajeRepository pasajeRepository;
    private final UserRepository userRepository;
    private final PoliticaDevolucionService politicaService;

    @Cacheable(value = "reservas", key = "#id")
    public Reserva obtenerReservaPorId(Integer id) {
        return reservaRepository.findById(id).orElseThrow(() -> new ReservaInvalidaException("Reserva no encontrada. ID: " + id));
    }

    @Cacheable(value = "reservas", key = "'todos'")
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    @Cacheable(value = "reservas", key = "'persona_' + #usuarioAutenticado.persona.id")
    public List<Reserva> obtenerMisReservas(User usuarioAutenticado) {
        Persona persona = usuarioAutenticado.getPersona();
        return reservaRepository.findByPersona(persona);
    }

    @CachePut(value = "reservas", key = "#result.id")
    @CacheEvict(value = "reservas", key = "'todos'")
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @CachePut(value = "reservas", key = "#result.id")
    @CacheEvict(value = "reservas", key = "'todos'")
    public Reserva actualizarReserva(Integer id, ReservaDTO reservaDTO) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaInvalidaException(
                        "Reserva no encontrada. ID: " + id));

        Persona persona = personaRepository.findById(reservaDTO.getClienteId())
                .orElseThrow(() -> new PersonaInvalidaException(
                        "Usuario no encontrado. ID: " + reservaDTO.getClienteId()));

        reserva.setPersona(persona);
        reserva.setValor(BigDecimal.valueOf(reservaDTO.getValor()));
        reserva.setCantidadPasajes(reservaDTO.getCantidadPasajes());
        reserva.setEstadoReserva(reservaDTO.getEstadoReserva());
        reserva.setFechaReserva(reservaDTO.getFechaReserva());

        return reservaRepository.save(reserva);
    }

    @CacheEvict(value = "reservas", allEntries = true)
    public void cancelarReserva(Integer id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ReservaInvalidaException("Reserva no encontrada"));

        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new IllegalArgumentException(
                    "La reserva ya se encuentra cancelada"
            );
        }

        BigDecimal reembolso = politicaService.calcular(reserva);
        if (reembolso.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("No corresponde reembolso para la reserva ID: " + id);
        } else if (reembolso.compareTo(reserva.getValor()) == 0) {
            System.out.println("Reembolso total de $" + reembolso + " para la reserva ID: " + id);
        } else {
            System.out.println("Reembolso parcial de $" + reembolso + " para la reserva ID: " + id);
        }

        List<Pasaje> pasajes =
                pasajeRepository.findByReservaId(reserva.getId());
        pasajeRepository.deleteAll(pasajes);
        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }

    @CacheEvict(value = "reservas", allEntries = true)
    public void cancelarReservaPorUsuario(Integer id, User usuarioAutenticado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaInvalidaException("Reserva no encontrada"));
        boolean esAdminOEmpleado = usuarioAutenticado.getRole() == Role.ROLE_ADMIN
                || usuarioAutenticado.getRole() == Role.ROLE_EMPLEADO;
        if (!esAdminOEmpleado && !reserva.getPersona().getId().equals(usuarioAutenticado.getPersona().getId())) {
            throw new ReservaInvalidaException("La reserva no pertenece al usuario autenticado.");
        }
        cancelarReserva(id);
    }

    @Caching(evict = {
            @CacheEvict(value = "reservas", key = "#id"),
            @CacheEvict(value = "reservas", key = "'todos'")
    })
    public void eliminarReserva(Integer id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> buscarTodasReservasConFiltros(
            String email,
            EstadoReserva estado,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Double valorMinimo,
            Double valorMaximo,
            Integer cantidadPasajes) {

        Integer personaId = null;
        if (email != null && !email.trim().isEmpty()) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new PersonaInvalidaException("Usuario no encontrado con email: " + email));
            personaId = user.getPersona().getId();
        }

        Specification<Reserva> spec = Specification
                .where(ReservaSpecification.porPersonaId(personaId))
                .and(ReservaSpecification.porEstado(estado))
                .and(ReservaSpecification.porFechaDesde(fechaDesde))
                .and(ReservaSpecification.porFechaHasta(fechaHasta))
                .and(ReservaSpecification.porValorMinimo(valorMinimo))
                .and(ReservaSpecification.porValorMaximo(valorMaximo))
                .and(ReservaSpecification.porCantidadPasajes(cantidadPasajes));

        return reservaRepository.findAll(spec);
    }

    public List<Reserva> buscarMisReservasConFiltros(
            Persona persona,
            EstadoReserva estado,
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta,
            Double valorMinimo,
            Double valorMaximo,
            Integer cantidadPasajes) {

        Specification<Reserva> spec = Specification
                .where(ReservaSpecification.porPersonaId(persona.getId()))
                .and(ReservaSpecification.porEstado(estado))
                .and(ReservaSpecification.porFechaDesde(fechaDesde))
                .and(ReservaSpecification.porFechaHasta(fechaHasta))
                .and(ReservaSpecification.porValorMinimo(valorMinimo))
                .and(ReservaSpecification.porValorMaximo(valorMaximo))
                .and(ReservaSpecification.porCantidadPasajes(cantidadPasajes));

        return reservaRepository.findAll(spec);
    }

    @CacheEvict(value = "reservas", key = "'todos'")
    public void invalidarListaDeReservas() {
    }

    @CacheEvict(value = "reservas", allEntries = true)
    public void hacerCheckin(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaInvalidaException("Reserva no encontrada"));
        if (reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new IllegalArgumentException("No se puede hacer check-in de una reserva cancelada");
        }
        reserva.setEstadoReserva(EstadoReserva.ACTIVO);
        reservaRepository.save(reserva);
    }
}
