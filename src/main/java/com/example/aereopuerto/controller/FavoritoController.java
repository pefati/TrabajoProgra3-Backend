package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.FavoritoDTO;
import com.example.aereopuerto.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping("/{personaId}")
    public ResponseEntity<List<FavoritoDTO>> getFavoritos(@PathVariable Integer personaId) {
        return ResponseEntity.ok(favoritoService.getFavoritosByPersonaId(personaId));
    }

    @PostMapping
    public ResponseEntity<FavoritoDTO> addFavorito(@RequestParam Integer personaId, @RequestParam Integer vueloId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.addFavorito(personaId, vueloId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavorito(@RequestParam Integer personaId, @PathVariable Integer id) {
        favoritoService.removeFavorito(personaId, id);
        return ResponseEntity.noContent().build();
    }
}
