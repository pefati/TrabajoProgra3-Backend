package com.example.aereopuerto.auth.controller;

import com.example.aereopuerto.auth.dto.*;
import com.example.aereopuerto.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/completarPerfil/{userId}")
    public ResponseEntity<String> completarPerfil(@PathVariable Integer userId, @RequestBody CompletarPerfilRequest request) {
        authService.completarPerfil(userId, request);
        return ResponseEntity.ok("Perfil completado correctamente");
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
