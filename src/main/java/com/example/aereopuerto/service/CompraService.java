package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.CarritoInvalidoException;
import com.example.aereopuerto.Exceptions.EquipajeInvalidoException;
import com.example.aereopuerto.Exceptions.VueloInvalidoException;
import com.example.aereopuerto.dto.CompraDTO;
import com.example.aereopuerto.model.*;
import com.example.aereopuerto.model.enums.EstadoReserva;
import com.example.aereopuerto.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CompraService {

    private final CarritoRepository carritoRepository;
    private final PasajeRepository pasajeRepository;
    private final ReservaRepository reservaRepository;
    private final FacturaRepository facturaRepository;
    private final EquipajeRepository equipajeRepository;
    private final AsistenciaRepository asistenciaRepository;


    public void confirmarCompra(CompraDTO dto) {
        Carrito carrito = obtenerCarrito(dto.getPersonaId());
        validarCarrito(carrito);
        validarDisponibilidad(carrito);
        Equipaje equipaje = obtenerEquipaje(dto.getEquipajeId());
        AsistenciaAlViajero asistencia = obtenerAsistencia(dto.getAsistenciaId());
        Reserva reserva = crearReserva(carrito);
        List<Pasaje> pasajes = generarPasajes(carrito, reserva, equipaje, asistencia);
        guardarPasajes(pasajes);
        crearFactura(dto, reserva);
        vaciarCarrito(carrito);
    }

    private Carrito obtenerCarrito(Integer personaId) {
        return carritoRepository.findByPersonaId(personaId)
                .orElseThrow(() -> new CarritoInvalidoException("Carrito no encontrado"));
    }

    private void validarCarrito(Carrito carrito) {
        if (carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new CarritoInvalidoException("El carrito está vacío");
        }
    }

    private void validarDisponibilidad(Carrito carrito) {

        for (CarritoItem item : carrito.getItems()) {
            Vuelo vuelo = item.getVuelo();
            int capacidad = vuelo.getAvion().getCapacidadPasajeros();
            long vendidos = pasajeRepository.countByVueloId(vuelo.getId());
            if (vendidos + item.getCantidad() > capacidad) {
                throw new VueloInvalidoException("Sin disponibilidad en vuelo " + vuelo.getId());
            }
        }
    }

    private Equipaje obtenerEquipaje(Integer equipajeId) {

        if (equipajeId == null) return null;
        return equipajeRepository.findById(equipajeId)
                .orElseThrow(() -> new EquipajeInvalidoException("Equipaje no encontrado"));
    }

    private AsistenciaAlViajero obtenerAsistencia(Integer asistenciaId) {
        if (asistenciaId == null) return null;
        return asistenciaRepository.findById(asistenciaId)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    private Reserva crearReserva(Carrito carrito) {

        int totalPasajes = carrito.getItems()
                .stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum();

        double total = carrito.getItems()
                .stream()
                .mapToDouble(i -> i.getVuelo().getPrecioVuelo() * i.getCantidad())
                .sum();

        Reserva reserva = Reserva.builder()
                .persona(carrito.getPersona())
                .cantidadPasajes(totalPasajes)
                .valor(total)
                .estadoReserva(EstadoReserva.CONFIRMADO)
                .fechaReserva(LocalDateTime.now())
                .build();

        return reservaRepository.save(reserva);
    }

    private List<Pasaje> generarPasajes(
            Carrito carrito,
            Reserva reserva,
            Equipaje equipaje,
            AsistenciaAlViajero asistencia) {

        List<Pasaje> pasajes = new java.util.ArrayList<>();

        for (CarritoItem item : carrito.getItems()) {

            for (int i = 0; i < item.getCantidad(); i++) {

                Pasaje p = Pasaje.builder()
                        .codigoPasaje(UUID.randomUUID().toString())
                        .clasesVuelo(item.getClaseVuelo())
                        .vuelo(item.getVuelo())
                        .reserva(reserva)
                        .equipaje(equipaje)
                        .build();

                pasajes.add(p);
            }
        }

        return pasajes;
    }

    private void guardarPasajes(List<Pasaje> pasajes) {
        pasajeRepository.saveAll(pasajes);
    }

    private void crearFactura(CompraDTO dto, Reserva reserva) {

        Factura factura = Factura.builder()
                .reserva(reserva)
                .CUIL(dto.getCuil())
                .situacionFiscal(dto.getSituacionFiscal())
                .metodoDePago(dto.getMetodoPago())
                .fechaEmision(LocalDateTime.now())
                .build();

        facturaRepository.save(factura);
    }

    private void vaciarCarrito(Carrito carrito) {
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
