package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.AsistenciaAlViajero;
import com.example.aereopuerto.model.Equipaje;
import com.example.aereopuerto.service.AsistenciaService;
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
@RequestMapping("/api/asistenciasAlViajero")
@RequiredArgsConstructor
@Tag(name = "AsistenciasAlViajero", description = "Operaciones relacionadas con las asistencias al viajero")
public class AsistenciaController {

    @Autowired
    private final AsistenciaService asistenciaService;

    @Operation(
            summary = "Obtener asistencia por ID",
            description = "Devuelve una asistencia al viajero específica según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asistencia encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaAlViajero> obtenerAsistenciaAlViajero(@PathVariable Integer id) {
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciaPorId(id));
    }

    @Operation(
            summary = "Obtener todas las asistencias",
            description = "Devuelve la lista completa de asistencias al viajero"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<AsistenciaAlViajero>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodosLasAsistencias());
    }

    @Operation(
            summary = "Crear asistencia al viajero",
            description = "Registra una nueva asistencia al viajero en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Asistencia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<AsistenciaAlViajero> crearAsistenciaAlViajero(@RequestBody AsistenciaAlViajero asistenciaAlViajero) {
        AsistenciaAlViajero nueva = asistenciaService.crearAsistencia(asistenciaAlViajero);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(
            summary = "Actualizar asistencia al viajero",
            description = "Actualiza los datos de una asistencia existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asistencia actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaAlViajero> actualizarAsistenciaAlViajero(@PathVariable Integer id, @RequestBody AsistenciaAlViajero asistenciaAlViajero) {
        return ResponseEntity.ok(asistenciaService.actualizarAsistencia(id,asistenciaAlViajero));
    }

    @Operation(
            summary = "Eliminar asistencia al viajero",
            description = "Elimina una asistencia al viajero por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asistencia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistenciaAlViajero(@PathVariable Integer id) {
        asistenciaService.eliminarAsistencia(id);
        return ResponseEntity.noContent().build();
    }

}
