package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AeropuertoDTO;
import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.service.AeropuertoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aeropuertos")
@RequiredArgsConstructor
@Tag(name = "Aeropuertos", description = "Operaciones relacionadas con los aeropuertos")
public class AeropuertoController {

    @Autowired
    private final AeropuertoService aeropuertoService;

    @Operation(summary = "Obtener aeropuerto por ID", description = "Devuelve los datos de un aeropuerto.")
    @GetMapping("/{id}")
    public ResponseEntity<Aeropuerto> obtenerAeropuerto(@PathVariable Integer id) {
        return ResponseEntity.ok(aeropuertoService.obtenerAeropuertoPorId(id));
    }

    @Operation(summary = "Obtener todos los aeropuertos", description = "Devuelve todos los aeropuertos registrados.")
    @GetMapping
    public ResponseEntity<List<Aeropuerto>> obtenerTodos() {
        return ResponseEntity.ok(aeropuertoService.obtenerTodosLosAeropuertos());
    }

    @Operation(summary = "Crear aeropuerto", description = "Registra un nuevo aeropuerto.")
    @PostMapping
    public ResponseEntity<Aeropuerto> crearAeropuerto(@Valid @RequestBody AeropuertoDTO aeropuerto) {
        Aeropuerto nuevo = aeropuertoService.crearAeropuerto(aeropuerto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar aeropuerto", description = "Registra cambios en un aeropuerto.")
    @PutMapping("/{id}")
    public ResponseEntity<Aeropuerto> actualizarAeropuerto(
            @PathVariable Integer id,
            @Valid @RequestBody AeropuertoDTO aeropuerto) {
        return ResponseEntity.ok(aeropuertoService.EditarAeropuerto(id, aeropuerto));
    }

    @Operation(summary = "Eliminar aeropuerto", description = "Elimina un aeropuerto del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAeropuerto(@PathVariable Integer id) {
       aeropuertoService.eliminarAeropuerto(id);
        return ResponseEntity.noContent().build();
    }

}
