package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Equipaje;
import com.example.aereopuerto.service.EquipajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipajes")
@RequiredArgsConstructor
@Tag(name = "Equipajes", description = "Operaciones relacionadas con los equipajes")
public class EquipajeController {

    @Autowired
    private final EquipajeService equipajeService;

    @Operation(
            summary = "Obtener equipaje por ID",
            description = "Devuelve la información de un equipaje específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipaje encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipaje no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipaje> obtenerEquipaje(@PathVariable Integer id) {
        return ResponseEntity.ok(equipajeService.obtenerEquipajePorId(id));
    }

    @Operation(
            summary = "Obtener todos los equipajes",
            description = "Devuelve la lista completa de equipajes registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Equipaje>> obtenerTodos() {
        return ResponseEntity.ok(equipajeService.obtenerTodosLosEquipajes());
    }

    @Operation(
            summary = "Crear equipaje",
            description = "Registra un nuevo equipaje en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipaje creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Equipaje> crearEquipaje(@RequestBody Equipaje equipaje) {
        Equipaje nuevo = equipajeService.crearEquipaje(equipaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(
            summary = "Actualizar equipaje",
            description = "Actualiza un equipaje existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipaje actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipaje no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipaje> actualizarEquipaje(@PathVariable Integer id, @RequestBody Equipaje equipaje) {
        return ResponseEntity.ok(equipajeService.actualizarEquipaje(id,equipaje));
    }

    @Operation(
            summary = "Eliminar equipaje",
            description = "Elimina un equipaje del sistema por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipaje eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipaje no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipaje(@PathVariable Integer id) {
        equipajeService.eliminarEquipaje(id);
        return ResponseEntity.noContent().build();
    }

}
