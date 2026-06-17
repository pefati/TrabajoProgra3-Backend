package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AvionDTO;
import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.enums.estadoAvion;
import com.example.aereopuerto.service.AvionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviones")
@RequiredArgsConstructor
@Tag(name = "Aviones", description = "Operaciones relacionadas con los aviones")
public class AvionController {

    @Autowired
    private final AvionService avionService;


    @Operation(summary = "Obtener avion por ID", description = "Devuelve los datos de un avión.")
    @GetMapping("/{id}")
    public ResponseEntity<Avion> obtenerAvion(@PathVariable Integer id) {
        return ResponseEntity.ok(avionService.obtenerAvionPorId(id));
    }

    @Operation(summary = "Obtener todos los aviones", description = "Devuelve todos los aviones registrados.")
    @GetMapping
    public ResponseEntity<List<Avion>> obtenerTodos() {
        return ResponseEntity.ok(avionService.obtenerTodosLosAviones());
    }

    @Operation(summary = "Crear avion", description = "Registra un nuevo avión.")
    @PostMapping
    public ResponseEntity<Avion> crearAvion(@RequestBody Avion avion) {
        Avion nuevo = avionService.crearAvion(avion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar avion", description = "Actualiza la información de un avión existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Avion> actualizarAvion(@PathVariable Integer id, @RequestBody AvionDTO avion) {
        return ResponseEntity.ok(avionService.EditarAvion(id, avion));
    }

    @Operation(summary = "Eliminar avion", description = "Elimina un avión del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAvion(@PathVariable Integer id) {
        avionService.eliminarAvion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtrar avion", description = "Filtra los aviones segun los criterios enviados.")
    @GetMapping("/filtraraviones")
    public ResponseEntity<List<Avion>> filtrarAviones(
            @RequestParam(required = false) String identificador,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) estadoAvion estado,
            @RequestParam(required = false) Integer capacidadPasajerosMin,
            @RequestParam(required = false) Integer capacidadPasajerosMax,
            @RequestParam(required = false) Float capacidadBodegaMin,
            @RequestParam(required = false) Float capacidadBodegaMax) {

        return ResponseEntity.ok(avionService.buscarAvionesConFiltros(
                identificador,
                modelo,
                estado,
                capacidadPasajerosMin,
                capacidadPasajerosMax,
                capacidadBodegaMin,
                capacidadBodegaMax
        ));
    }

}
