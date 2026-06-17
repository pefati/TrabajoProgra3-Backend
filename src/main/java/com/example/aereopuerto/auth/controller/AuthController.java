package com.example.aereopuerto.auth.controller;

import com.example.aereopuerto.auth.dto.*;
import com.example.aereopuerto.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    private void setTokenCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);
    }

    private void setEmailCookie(HttpServletResponse response, String email) {
        Cookie cookie = new Cookie("user_email", email);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
    }

    private void clearEmailCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("user_email", null);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica un usuario y devuelve un JWT.")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);
        if (authResponse.getToken() != null) {
            setTokenCookie(response, authResponse.getToken());
            setEmailCookie(response, authResponse.getEmail());
        }
        return ResponseEntity.ok(authResponse);
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
    public ResponseEntity<AuthResponse> completarPerfil(@Valid @RequestBody CompletarPerfilRequest request, HttpServletResponse response) {
        ResponseEntity<AuthResponse> res = authService.completarPerfil(request);
        if (res.getBody() != null && res.getBody().getToken() != null) {
            setTokenCookie(response, res.getBody().getToken());
            setEmailCookie(response, res.getBody().getEmail());
        }
        return res;
    }

    @PutMapping("/perfil")
    public ResponseEntity<AuthResponse> actualizarPerfil(@Valid @RequestBody CompletarPerfilRequest request, HttpServletResponse response) {
        ResponseEntity<AuthResponse> res = authService.actualizarPerfil(request);
        if (res.getBody() != null && res.getBody().getToken() != null) {
            setTokenCookie(response, res.getBody().getToken());
            setEmailCookie(response, res.getBody().getEmail());
        }
        return res;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
    }

    @GetMapping("/verify")
    public ResponseEntity<AuthResponse> verifyEmail(@RequestParam String token, HttpServletResponse response) {
        AuthResponse authResponse = authService.verifyEmail(token);
        if (authResponse.getToken() != null) {
            setTokenCookie(response, authResponse.getToken());
            setEmailCookie(response, authResponse.getEmail());
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<AuthResponse> verify2fa(@Valid @RequestBody Verify2FARequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.verify2fa(request);
        if (authResponse.getToken() != null) {
            setTokenCookie(response, authResponse.getToken());
            setEmailCookie(response, authResponse.getEmail());
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/toggle-2fa")
    public ResponseEntity<String> toggle2fa() {
        return authService.toggle2fa();
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        clearTokenCookie(response);
        clearEmailCookie(response);
        return ResponseEntity.ok(Map.of("message", "Sesi\u00f3n cerrada exitosamente"));
    }

    /*@PostMapping("/register/limpio")
    @Operation(summary = "Registro", description = "Registra un nuevo usuario vinculado a un Cliente existente.")
    public ResponseEntity<AuthResponse> registerSinPersona(@Valid @RequestBody RegisterRequestSinPersona request) {
        return ResponseEntity.ok(authService.registerSinPersona(request));
    }
     */

}
