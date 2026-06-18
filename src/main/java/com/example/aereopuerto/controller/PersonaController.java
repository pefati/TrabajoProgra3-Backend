package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import com.example.aereopuerto.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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

    @Operation(
            summary = "Filtrar usuarios",
            description = "Permite filtrar usuarios por nombre, apellido, documento, sexo y fecha de nacimiento."
    )
    @ApiResponse(responseCode = "200", description = "Resultados obtenidos correctamente")
    @GetMapping("/filtrar")
    public ResponseEntity<List<Persona>> buscarPersonas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) Identificador identificador,
            @RequestParam(required = false) String numeroIdentificador,
            @RequestParam(required = false) Sexo sexo,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento) {

        List<Persona> personas = personaService.buscarPersonasConFiltros(
                nombre, apellido, identificador, numeroIdentificador, sexo, fechaNacimiento
        );

        return ResponseEntity.ok(personas);
    }

}
