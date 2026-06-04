package com.example.aereopuerto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmarCompra(
            @RequestBody CompraDTO dto){

        compraService.confirmarCompra(dto);

        return ResponseEntity.ok("Compra realizada correctamente");
    }
}
