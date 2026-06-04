package com.example.aereopuerto.service;

import com.example.aereopuerto.dto.CompraDTO;
import com.example.aereopuerto.model.*;
import com.example.aereopuerto.model.enums.EstadoReserva;
import com.example.aereopuerto.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CompraService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;

    private final ReservaRepository reservaRepository;
    private final PasajeRepository pasajeRepository;
    private final FacturaRepository facturaRepository;

    private final EquipajeRepository equipajeRepository;
    private final AsistenciaRepository asistenciaRepository;

    public void confirmarCompra(CompraDTO dto) {

        Carrito carrito = carritoRepository
                .findByPersonaId(dto.getPersonaId())
                .orElseThrow(() ->
                        new RuntimeException("Carrito no encontrado"));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Equipaje equipaje = equipajeRepository
                .findById(dto.getEquipajeId())
                .orElseThrow(() ->
                        new RuntimeException("Equipaje no encontrado"));

        AsistenciaAlViajero asistencia = asistenciaRepository
                .findById(dto.getAsistenciaId())
                .orElseThrow(() ->
                        new RuntimeException("Asistencia al viajero no encontrada"));

        validarDisponibilidad(carrito);

        Double total = calcularTotal(
                carrito,
                equipaje,
                asistencia);

        Reserva reserva = crearReserva(
                carrito,
                total);

        crearPasajes(
                carrito,
                reserva,
                equipaje,
                asistencia);

        crearFactura(
                dto,
                reserva);

        vaciarCarrito(carrito);
    }

    private void validarDisponibilidad(Carrito carrito) {

        for (CarritoItem item : carrito.getItems()) {

            int capacidad = item.getVuelo()
                    .getAvion()
                    .getCapacidadPasajeros();

            long vendidos = pasajeRepository
                    .countByVueloId(
                            item.getVuelo().getId());

            if (vendidos + item.getCantidad() > capacidad) {

                throw new RuntimeException(
                        "No hay lugares disponibles para el vuelo "
                                + item.getVuelo().getId());
            }
        }
    }

    private Double calcularTotal(
            Carrito carrito,
            Equipaje equipaje,
            AsistenciaAlViajero asistencia) {

        Double total = 0.0;

        for (CarritoItem item : carrito.getItems()) {

            total += item.getVuelo()
                    .getPrecioVuelo()
                    * item.getCantidad();
        }

        total += equipaje.getPrecio();
        total += asistencia.getPrecio();

        return total;
    }

    private Reserva crearReserva(
            Carrito carrito,
            Double total) {

        Reserva reserva = Reserva.builder()
                .persona(carrito.getPersona())
                .fechaReserva(LocalDateTime.now())
                .estadoReserva(EstadoReserva.CONFIRMADO)
                .cantidadPasajes(
                        carrito.getItems()
                                .stream()
                                .mapToInt(CarritoItem::getCantidad)
                                .sum())
                .valor(total.floatValue())
                .build();

        return reservaRepository.save(reserva);
    }

    private void crearPasajes(
            Carrito carrito,
            Reserva reserva,
            Equipaje equipaje,
            AsistenciaAlViajero asistencia) {

        for (CarritoItem item : carrito.getItems()) {

            for (int i = 0; i < item.getCantidad(); i++) {

                Pasaje pasaje = Pasaje.builder()
                        .codigoPasaje(
                                UUID.randomUUID().toString())
                        .clasesVuelo(
                                item.getClaseVuelo())
                        .vuelo(
                                item.getVuelo())
                        .equipaje(
                                equipaje)
                        .asistenciaAlViajero(
                                asistencia)
                        .reserva(
                                reserva)
                        .build();

                pasajeRepository.save(pasaje);
            }
        }
    }

    private void crearFactura(
            CompraDTO dto,
            Reserva reserva) {

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

        carritoItemRepository.deleteAll(
                carrito.getItems());

        carrito.getItems().clear();

        carritoRepository.save(carrito);
    }
}
