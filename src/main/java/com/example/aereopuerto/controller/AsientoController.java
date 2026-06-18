package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Asiento;
import com.example.aereopuerto.repository.AsientoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asientos")
@RequiredArgsConstructor
public class AsientoController {

    private final AsientoRepository asientoRepository;

    @Operation(
            summary = "Obtener asientos por vuelo",
            description = "Devuelve la lista de asientos asociados a un vuelo específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de asientos obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Vuelo no encontrado o sin asientos")
    })
    @GetMapping("/vuelo/{vueloId}")
    public ResponseEntity<List<Asiento>> getAsientosPorVuelo(@PathVariable Integer vueloId) {
        return ResponseEntity.ok(asientoRepository.findByVueloId(vueloId));
    }
}