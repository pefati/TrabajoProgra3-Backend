package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AsientoDTO;
import com.example.aereopuerto.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoService asientoService;

    @GetMapping("/vuelo/{vueloId}")
    public ResponseEntity<List<AsientoDTO>> obtenerAsientosPorVuelo(
            @PathVariable Integer vueloId) {

        return ResponseEntity.ok(
                asientoService.obtenerAsientosPorVuelo(vueloId)
        );
    }
}