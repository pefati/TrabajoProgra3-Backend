package com.example.aereopuerto.controller;


import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.model.Asignacion;
import com.example.aereopuerto.service.AsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.AstGreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignacion")
@RequiredArgsConstructor
@Tag(name = "Asignaciones", description = "Roles de los empleados segun los vuelos")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @Operation(summary = "Obtener asignacion por ID", description = "Devuelve los datos de una asignacion.")
    @GetMapping("/{id}")
    public ResponseEntity<Asignacion> obtenerAsignacion(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionService.obtenerAsignacionPorId(id));
    }

    @Operation(summary = "Obtener todas las asignaciones", description = "Devuelve todas las asignaciones registradas.")
    @GetMapping
    public ResponseEntity<List<Asignacion>> obtenerTodos() {
        return ResponseEntity.ok(asignacionService.obtenerTodosLasAsignaciones());
    }

    @Operation(summary = "Crear asignacion", description = "Registra una nueva asignacion.")
    @PostMapping
    public ResponseEntity<Asignacion> crearAsignacion(@RequestBody Asignacion asignacion) {
        Asignacion nuevo = asignacionService.crearAsignacion(asignacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar asignacion", description = "Actualiza la información de una asignacion existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Asignacion> actualizarAsignacion(@PathVariable Integer id, @RequestBody Asignacion asignacion) {
        return ResponseEntity.ok(asignacionService.EditarAeropuerto(id, asignacion));
    }


    @Operation(summary = "Eliminar asignacion", description = "Elimina una asignacion del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Integer id) {
        asignacionService.eliminarAsignacion(id);
        return ResponseEntity.noContent().build();
    }




}
