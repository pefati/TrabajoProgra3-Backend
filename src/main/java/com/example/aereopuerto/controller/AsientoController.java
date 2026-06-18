package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.AsientoDTO;
import com.example.aereopuerto.service.AsientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Obtener asientos por vuelo",
            description = "Devuelve la lista de asientos asociados a un vuelo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asientos obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Vuelo no encontrado o sin asientos")
    })
    @GetMapping("/vuelo/{vueloId}")
    public ResponseEntity<List<AsientoDTO>> obtenerAsientosPorVuelo(
            @PathVariable Integer vueloId) {

        return ResponseEntity.ok(
                asientoService.obtenerAsientosPorVuelo(vueloId)
        );
    }
}