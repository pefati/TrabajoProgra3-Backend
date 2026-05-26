package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Obtener reserva por ID", description = "Devuelve los detalles de una reserva.")
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    }

    @Operation(summary = "Obtener todas las reservas", description = "Devuelve la lista completa de reservas.")
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodasLasReservas());
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva.")
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva nueva = reservaService.crearOActualizarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar reserva", description = "Actualiza una reserva existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        return ResponseEntity.ok(reservaService.crearOActualizarReserva(reserva));
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
