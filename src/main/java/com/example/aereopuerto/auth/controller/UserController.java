package com.example.aereopuerto.auth.controller;

import com.example.aereopuerto.auth.entity.Role;
import com.example.aereopuerto.auth.service.UserService;
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
public class UserController {

    private final UserService userService;

    @PatchMapping("/emplear/{userId}")
    public ResponseEntity<String> hacerEmpleado(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_EMPLEADO);
        return ResponseEntity.ok("El usuario ahora es empleado");
    }

    @PatchMapping("/haceradmin/{userId}")
    public ResponseEntity<String> hacerAdmin(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_ADMIN);
        return ResponseEntity.ok("El usuario ahora es administrador");
    }

    @PatchMapping("/hacerusuario/{userId}")
    public ResponseEntity<String> hacerUsuario(@PathVariable Integer userId) {
        userService.cambiarRol(userId, Role.ROLE_USUARIO);
        return ResponseEntity.ok("El usuario ahora es usuario regular");
    }
}