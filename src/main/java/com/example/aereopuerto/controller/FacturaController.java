package com.example.aereopuerto.controller;

import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.dto.FacturaDTO;
import com.example.aereopuerto.model.Factura;
import com.example.aereopuerto.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "Operaciones relacionadas con las facturas")
public class FacturaController {

    @Autowired
    private final FacturaService facturaService;

    @Operation(summary = "Obtener factura por ID", description = "Devuelve los datos de una factura.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Integer id) {
        return ResponseEntity.ok(facturaService.obtenerFacturaPorId(id));
    }

    @Operation(summary = "Obtener mis facturas", description = "Devuelve las facturas del usuario autenticado.")
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerMisFacturas(@AuthenticationPrincipal User usuarioAutenticado) {
        return ResponseEntity.ok(facturaService.obtenerFacturasPorPersona(usuarioAutenticado.getPersona()));
    }

    @Operation(summary = "Obtener todas las facturas", description = "Devuelve todas las facturas registradas.")
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<List<Factura>> obtenerTodas() {
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @Operation(summary = "Crear factura", description = "Registra una nueva factura.")
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura nueva = facturaService.crearFactura(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar factura", description = "Actualiza una factura existente.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Integer id, @RequestBody FacturaDTO factura) {
        return ResponseEntity.ok(facturaService.actualizarFactura(id,factura));
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMIN')")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
