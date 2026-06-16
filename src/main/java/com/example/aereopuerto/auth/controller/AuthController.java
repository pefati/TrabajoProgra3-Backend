package com.example.aereopuerto.auth.controller;

import com.example.aereopuerto.auth.dto.*;
import com.example.aereopuerto.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registro e inicio de sesión de usuarios.")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica un usuario y devuelve un JWT.")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Registro", description = "Registra un nuevo usuario vinculado a un Cliente existente.")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/perfil")
    public ResponseEntity<PerfilResponse> obtenerPerfil() {
        return ResponseEntity.ok(authService.obtenerPerfil());
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {
        return ResponseEntity.ok(authService.obtenerUsuarios());
    }

    @GetMapping("/usuarios/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(
            @PathVariable Integer id) {
        return ResponseEntity.ok(authService.obtenerUsuario(id));
    }

    @PutMapping("/completarPerfil")
    public ResponseEntity<AuthResponse> completarPerfil(@Valid @RequestBody CompletarPerfilRequest request) {
        return authService.completarPerfil(request);
    }

    @PutMapping("/perfil")
    public ResponseEntity<AuthResponse> actualizarPerfil(@Valid @RequestBody CompletarPerfilRequest request) {
        return authService.actualizarPerfil(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
    }

    /*@PostMapping("/register/limpio")
    @Operation(summary = "Registro", description = "Registra un nuevo usuario vinculado a un Cliente existente.")
    public ResponseEntity<AuthResponse> registerSinPersona(@Valid @RequestBody RegisterRequestSinPersona request) {
        return ResponseEntity.ok(authService.registerSinPersona(request));
    }
     */

}
