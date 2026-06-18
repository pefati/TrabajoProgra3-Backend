package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AsientoDTO;
import com.example.aereopuerto.service.AsientoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
@Tag(name = "Asientos", description = "Operaciones relacionadas con los asientos")
public class AsientoController {

    private final AsientoService asientoService;

    @GetMapping("/avion/{avionId}")
    public ResponseEntity<List<AsientoDTO>> getAsientosPorAvion(@PathVariable Integer avionId) {
        return ResponseEntity.ok(asientoService.obtenerAsientosPorAvion(avionId));
    }
}
