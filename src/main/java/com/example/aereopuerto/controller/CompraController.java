package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.CompraDTO;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @Operation(
            summary = "Confirmar compra",
            description = "Realiza la compra de vuelos del usuario autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuario no verificado")
    })
    @PostMapping("/confirmar")
    public ResponseEntity<String> confirmar(@RequestBody CompraDTO dto, @AuthenticationPrincipal User usuarioAutenticado) {
        if (!Boolean.TRUE.equals(usuarioAutenticado.getIsVerified())) {
            throw new IllegalArgumentException("Debes verificar tu cuenta desde tu correo electrónico para poder comprar vuelos.");
        }
        compraService.confirmarCompra(dto, usuarioAutenticado);
        return ResponseEntity.ok("Compra realizada");
    }
}
