package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.service.AvionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviones")
@Tag(name = "Aviones", description = "Operaciones relacionadas con los aviones")
public class AvionController {

    private final AvionService avionService;

    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }

    @Operation(summary = "Obtener avión por ID", description = "Devuelve los datos de un avión.")
    @GetMapping("/{id}")
    public ResponseEntity<Avion> obtenerAvion(@PathVariable Long id) {
        return ResponseEntity.ok(avionService.obtenerAvionPorId(id));
    }

    @Operation(summary = "Obtener todos los aviones", description = "Devuelve todos los aviones registrados.")
    @GetMapping
    public ResponseEntity<List<Avion>> obtenerTodos() {
        return ResponseEntity.ok(avionService.obtenerTodosLosAviones());
    }

    @Operation(summary = "Crear avión", description = "Registra un nuevo avión.")
    @PostMapping
    public ResponseEntity<Avion> crearAvion(@RequestBody Avion avion) {
        Avion nuevo = avionService.crearOActualizarAvion(avion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar avión", description = "Actualiza la información de un avión existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Avion> actualizarAvion(@PathVariable Long id, @RequestBody Avion avion) {
        avion.setAvion_id(id);
        return ResponseEntity.ok(avionService.crearOActualizarAvion(avion));
    }

    @Operation(summary = "Eliminar avión", description = "Elimina un avión del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAvion(@PathVariable Long id) {
        avionService.eliminarAvion(id);
        return ResponseEntity.noContent().build();
    }
}
