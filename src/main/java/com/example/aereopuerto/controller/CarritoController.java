package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.CarritoDTO;
import com.example.aereopuerto.dto.CarritoItemDTO;
import com.example.aereopuerto.model.enums.ClasesVuelo;
import com.example.aereopuerto.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/{personaId}")
    public ResponseEntity<CarritoDTO> getCarrito(@PathVariable Integer personaId) {
        return ResponseEntity.ok(carritoService.getCarritoByPersonaId(personaId));
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoItemDTO> addItemToCarrito(
            @RequestParam Integer personaId,
            @RequestParam Integer vueloId,
            @RequestParam int cantidad,
            @RequestParam ClasesVuelo clase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.addItem(personaId, vueloId, cantidad, clase));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCarrito(@RequestParam Integer personaId, @PathVariable Integer itemId) {
        carritoService.removeItem(personaId, itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carritoId}/clear")
    public ResponseEntity<Void> clearCarrito(@RequestParam Integer personaId, @PathVariable Integer carritoId) {
        carritoService.clearCarrito(personaId, carritoId);
        return ResponseEntity.noContent().build();
    }
}
