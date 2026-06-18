package com.example.aereopuerto.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${tarifas.impuesto:0.15}")
    private Double tasaImpuesto;

    @Value("${tarifas.servicio:0.025}")
    private Double tasaServicio;

    @GetMapping("/tarifas")
    public ResponseEntity<Map<String, Double>> obtenerTarifas() {
        return ResponseEntity.ok(Map.of(
                "tasaImpuesto", tasaImpuesto,
                "tasaServicio", tasaServicio));
    }
}
