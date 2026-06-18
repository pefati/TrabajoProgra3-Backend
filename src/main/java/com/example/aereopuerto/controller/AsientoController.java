package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AsientoDTO;
import com.example.aereopuerto.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService asientoService;

    @GetMapping("/avion/{avionId}")
    public ResponseEntity<List<AsientoDTO>> getAsientosPorAvion(@PathVariable Integer avionId) {
        return ResponseEntity.ok(asientoService.obtenerAsientosPorAvion(avionId));
    }
}
