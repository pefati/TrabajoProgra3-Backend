package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Asiento;
import com.example.aereopuerto.repository.AsientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoRepository asientoRepository;

    @GetMapping("/vuelo/{vueloId}")
    public ResponseEntity<List<Asiento>> getAsientosPorVuelo(@PathVariable Integer vueloId) {
        return ResponseEntity.ok(asientoRepository.findByVueloId(vueloId));
    }
}