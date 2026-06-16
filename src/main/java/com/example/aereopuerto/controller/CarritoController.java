package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.CarritoDTO;
import com.example.aereopuerto.dto.CarritoItemDTO;
import com.example.aereopuerto.model.enums.ClasesVuelo;
import com.example.aereopuerto.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping
    public ResponseEntity<CarritoDTO> getMiCarrito(Authentication authentication) {
        return ResponseEntity.ok(carritoService.getCarritoPorToken(authentication.getName()));
    }

    @GetMapping("/{personaId}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<CarritoDTO> getCarrito(@PathVariable Integer personaId) {
        return ResponseEntity.ok(carritoService.getCarritoByPersonaId(personaId));
    }

    @PostMapping("/items")
    public ResponseEntity<CarritoItemDTO> addItemToCarrito(
            @RequestParam Integer vueloId,
            @RequestParam int cantidad,
            @RequestParam ClasesVuelo clase,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.addItemPorToken(authentication.getName(), vueloId, cantidad, clase));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCarrito(@PathVariable Integer itemId, Authentication authentication) {
        carritoService.removeItemPorToken(authentication.getName(), itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carritoId}/clear")
    public ResponseEntity<Void> clearCarrito(@PathVariable Integer carritoId, Authentication authentication) {
        carritoService.clearCarritoPorToken(authentication.getName(), carritoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/personas/{personaId}/items")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<CarritoItemDTO> addItemToCarritoPorPersona(
            @PathVariable Integer personaId,
            @RequestParam Integer vueloId,
            @RequestParam int cantidad,
            @RequestParam ClasesVuelo clase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoService.addItem(personaId, vueloId, cantidad, clase));
    }
}
