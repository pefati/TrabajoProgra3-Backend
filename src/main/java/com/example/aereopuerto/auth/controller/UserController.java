package com.example.aereopuerto.auth.controller;

import com.example.aereopuerto.auth.entity.Role;
import com.example.aereopuerto.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Usuarios Admin", description = "Gestión de roles de usuarios (solo ADMIN)")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Convertir usuario en empleado",
            description = "Cambia el rol del usuario a EMPLEADO. Solo accesible por ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PatchMapping("/emplear/{userId}")
    public ResponseEntity<String> hacerEmpleado(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_EMPLEADO);
        return ResponseEntity.ok("El usuario ahora es empleado");
    }

    @Operation(
            summary = "Convertir usuario en administrador",
            description = "Cambia el rol del usuario a ADMIN. Solo accesible por ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PatchMapping("/haceradmin/{userId}")
    public ResponseEntity<String> hacerAdmin(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_ADMIN);
        return ResponseEntity.ok("El usuario ahora es administrador");
    }

    @Operation(
            summary = "Convertir usuario en usuario regular",
            description = "Cambia el rol del usuario a USUARIO. Solo accesible por ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PatchMapping("/hacerusuario/{userId}")
    public ResponseEntity<String> hacerUsuario(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_USUARIO);
        return ResponseEntity.ok("El usuario ahora es usuario regular");
    }
}