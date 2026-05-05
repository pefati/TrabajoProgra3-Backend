package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Pista;
import com.example.aereopuerto.service.PistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pistas")
@Tag(name = "Pistas", description = "Operaciones relacionadas con las pistas")
public class PistaController {

    private final PistaService pistaService;

    public PistaController(PistaService pistaService) {
        this.pistaService = pistaService;
    }

    @Operation(summary = "Obtener pista por ID", description = "Devuelve los datos de una pista.")
    @GetMapping("/{id}")
    public ResponseEntity<Pista> obtenerPista(@PathVariable Long id) {
        return ResponseEntity.ok(pistaService.obtenerPistaPorId(id));
    }

    @Operation(summary = "Obtener todas las pistas", description = "Devuelve todas las pistas disponibles.")
    @GetMapping
    public ResponseEntity<List<Pista>> obtenerTodas() {
        return ResponseEntity.ok(pistaService.obtenerTodasLasPistas());
    }

    @Operation(summary = "Crear pista", description = "Agrega una nueva pista.")
    @PostMapping
    public ResponseEntity<Pista> crearPista(@RequestBody Pista pista) {
        Pista nueva = pistaService.crearOActualizarPista(pista);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar pista", description = "Actualiza una pista existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Pista> actualizarPista(@PathVariable Long id, @RequestBody Pista pista) {
        pista.setPista_id(id);
        return ResponseEntity.ok(pistaService.crearOActualizarPista(pista));
    }

    @Operation(summary = "Eliminar pista", description = "Elimina una pista del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPista(@PathVariable Long id) {
        pistaService.eliminarPista(id);
        return ResponseEntity.noContent().build();
    }
}
