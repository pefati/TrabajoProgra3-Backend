package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Empleado;
import com.example.aereopuerto.service.EmpleadoService;
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
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Tag(name = "Empleados", description = "Operaciones relacionadas con los empleados")
public class EmpleadoController {

    @Autowired
    private final EmpleadoService empleadoService;

    @Operation(summary = "Obtener empleado por ID", description = "Devuelve los datos de un empleado.")
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
    }

    @Operation(summary = "Obtener todos los empleados", description = "Devuelve la lista completa de empleados.")
    @GetMapping
    public ResponseEntity<List<Empleado>> obtenerTodos() {
        return ResponseEntity.ok(empleadoService.obtenerTodosLosEmpleados());
    }

    @Operation(summary = "Crear empleado", description = "Registra un nuevo empleado.")
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        Empleado nuevo = empleadoService.crearOActualizarEmpleado(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar empleado", description = "Actualiza los datos de un empleado existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return ResponseEntity.ok(empleadoService.crearOActualizarEmpleado(empleado));
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

}
