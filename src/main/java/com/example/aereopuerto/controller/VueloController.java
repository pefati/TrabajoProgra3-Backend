package com.example.aereopuerto.controller;

import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.service.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
@Tag(name = "Vuelos", description = "Operaciones relacionadas con los vuelos")
public class VueloController {

    @Autowired
    private final VueloService vueloService;

    @Operation(summary = "Obtener un vuelo por su ID", description = "Devuelve los detalles de un vuelo. Utiliza caché Redis para respuestas ultra rapidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vuelo encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vuelo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> obtenerVuelo(@PathVariable Integer id) {
        // En la consola verás si va a MySQL o lo saca directamente de Redis
        Vuelo vuelo = vueloService.obtenerVueloPorId(id);
        return ResponseEntity.ok(vuelo);
    }

    @Operation(summary = "Obtener todos los vuelos", description = "Devuelve una lista de todos los vuelos programados.")
    @ApiResponse(responseCode = "200", description = "Lista devuelta exitosamente")
    @GetMapping
    public ResponseEntity<List<Vuelo>> obtenerTodos() {
        return ResponseEntity.ok(vueloService.obtenerTodosLosVuelos());
    }

    @Operation(summary = "Crear un nuevo vuelo", description = "Crea un vuelo en MySQL y actualiza automáticamente la caché de Redis.")
    @ApiResponse(responseCode = "201", description = "Vuelo creado exitosamente")
    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {
        // Al crear, se debe limpiar la caché general de la lista
        vueloService.invalidarListaDeVuelos();
        Vuelo nuevoVuelo = vueloService.crearOActualizarVuelo(vuelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVuelo);
    }

    @Operation(summary = "Actualizar un vuelo", description = "Actualiza los datos de un vuelo en MySQL y refresca la cache de Redis para no perder sincronia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vuelo actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Vuelo a actualizar no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarVuelo(@PathVariable Integer id, @RequestBody Vuelo vuelo) {
        vuelo.setId(id);
        vueloService.invalidarListaDeVuelos();
        Vuelo actualizado = vueloService.crearOActualizarVuelo(vuelo);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un vuelo", description = "Borra el vuelo de la base de datos e invalida su entrada en la cache Redis.")
    @ApiResponse(responseCode = "204", description = "Vuelo eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVuelo(@PathVariable Integer id) {
        vueloService.invalidarListaDeVuelos();
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }
}
