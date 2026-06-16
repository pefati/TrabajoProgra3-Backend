package com.example.aereopuerto.controller;

import com.example.aereopuerto.dto.FavoritoDTO;
import com.example.aereopuerto.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping("/{personaId}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<List<FavoritoDTO>> getFavoritos(@PathVariable Integer personaId) {
        return ResponseEntity.ok(favoritoService.getFavoritosByPersonaId(personaId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<FavoritoDTO> addFavorito(@RequestParam Integer personaId, @RequestParam Integer vueloId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.addFavorito(personaId, vueloId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavorito(@PathVariable Integer id, Authentication authentication) {
        favoritoService.removeFavoritoPorToken(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/vuelos/{vueloId}")
    public ResponseEntity<FavoritoDTO> addFavorito(
            @PathVariable Integer vueloId,
            Authentication authentication) {
        String emailUsuario = authentication.getName();
        FavoritoDTO nuevoFavorito = favoritoService.addFavoritoPorToken(emailUsuario, vueloId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFavorito);
    }

    @GetMapping
    public ResponseEntity<List<FavoritoDTO>> getMisFavoritos(Authentication authentication) {

        String emailUsuario = authentication.getName();

        List<FavoritoDTO> favoritos = favoritoService.getFavoritosPorToken(emailUsuario);

        return ResponseEntity.ok(favoritos);
    }
}
