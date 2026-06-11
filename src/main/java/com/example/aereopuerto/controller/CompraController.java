package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.CompraDTO;
import com.example.aereopuerto.service.CompraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmar(@RequestBody CompraDTO dto) {
        compraService.confirmarCompra(dto);
        return ResponseEntity.ok("Compra realizada");
    }
}
