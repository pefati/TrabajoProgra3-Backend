package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "Operaciones relacionadas con los usuarios")
public class PersonaController {

    @Autowired
    private final PersonaService personaService;

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve los datos de un usuario especifico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerCliente(@PathVariable Integer id) {
        return ResponseEntity.ok(personaService.obtenerClientePorId(id));
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista completa de usuarios.")
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodos() {
        return ResponseEntity.ok(personaService.obtenerTodosLosClientes());
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponse(responseCode = "201", description = "usuario creado exitosamente")
    @PostMapping
    public ResponseEntity<Persona> crearCliente(@RequestBody Persona persona) {
        Persona nuevo = personaService.crearCliente(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizarCliente(@PathVariable Integer id, @RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.actualizarCliente(id, persona));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        personaService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
