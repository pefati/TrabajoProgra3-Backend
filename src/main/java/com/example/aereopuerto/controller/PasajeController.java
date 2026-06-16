package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.FacturaDTO;
import com.example.aereopuerto.dto.PasajeDTO;
import com.example.aereopuerto.model.Factura;
import com.example.aereopuerto.model.Pasaje;
import com.example.aereopuerto.service.FacturaService;
import com.example.aereopuerto.service.PasajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pasajes")
@RequiredArgsConstructor
@Tag(name = "Pasajes", description = "Operaciones relacionadas con los pasajes")
public class PasajeController {

    @Autowired
    private final PasajeService pasajeService;

    @Operation(summary = "Obtener pasaje por ID", description = "Devuelve los datos de una pasaje.")
    @GetMapping("/{id}")
    public ResponseEntity<Pasaje> obtenerPasaje(@PathVariable Integer id) {
        return ResponseEntity.ok(pasajeService.obtenerPasajePorId(id));
    }

    @Operation(summary = "Obtener todos los pasajes", description = "Devuelve todos los pasajes registrados.")
    @GetMapping
    public ResponseEntity<List<Pasaje>> obtenerTodas() {
        return ResponseEntity.ok(pasajeService.obtenerTodasLosPasajes());
    }

    @Operation(summary = "Crear pasaje", description = "Registra un nuevo pasaje.")
    @PostMapping
    public ResponseEntity<Pasaje> crearPasaje(@RequestBody Pasaje pasaje) {
        Pasaje nuevo = pasajeService.crearPasaje(pasaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar pasaje", description = "Actualiza un pasaje existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Pasaje> actualizarPasaje(@PathVariable Integer id, @RequestBody PasajeDTO pasajeDTO) {
        return ResponseEntity.ok(pasajeService.editarPasaje(id, pasajeDTO));
    }

    @Operation(summary = "Eliminar pasaje", description = "Elimina un pasaje.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPasaje(@PathVariable Integer id) {
        pasajeService.eliminarPasaje(id);
        return ResponseEntity.noContent().build();
    }
}
