package com.example.aereopuerto.controller;

import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.dto.CompraDTO;
import com.example.aereopuerto.dto.ProcesarPagoDTO;
import com.example.aereopuerto.model.enums.MetodosDePago;
import com.example.aereopuerto.service.CompraService;
import com.example.aereopuerto.service.MercadoPagoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mercadopago")
@RequiredArgsConstructor
public class MercadoPagoController {

    private final MercadoPagoService mpService;
    private final CompraService compraService;

    @Value("${mercadopago.public-key:APP_USR-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}")
    private String publicKey;

    @GetMapping("/public-key")
    public ResponseEntity<Map<String, String>> getPublicKey() {
        return ResponseEntity.ok(Map.of("publicKey", publicKey));
    }

    @PostMapping("/crear-preferencia")
    public ResponseEntity<Map<String, Object>> crearPreferencia(
            @AuthenticationPrincipal User user,
            HttpServletRequest request) {
        double total = compraService.calcularTotalCarrito(user.getPersona().getId());
        String origin = request.getHeader("Origin");
        if (origin == null || origin.isBlank()) {
            origin = "http://localhost:5173";
        }
        var pref = mpService.crearPreferencia(
                total,
                "Compra AeroGest",
                null,
                user,
                String.valueOf(user.getId()),
                origin + "/pago.html"
        );
        String preferenceId = (String) pref.get("id");
        return ResponseEntity.ok(Map.of(
                "preferenceId", preferenceId,
                "publicKey", publicKey
        ));
    }

    @PostMapping("/procesar-pago")
    public ResponseEntity<Map<String, Object>> procesarPago(
            @RequestBody ProcesarPagoDTO dto,
            @AuthenticationPrincipal User user) {
        try {
            Map<String, Object> payment = mpService.procesarPago(
                    dto.getToken(),
                    dto.getAmount(),
                    dto.getInstallments(),
                    dto.getPaymentMethodId(),
                    dto.getIssuerId(),
                    dto.getPayerEmail(),
                    dto.getPayerDocType(),
                    dto.getPayerDocNumber()
            );

            if ("error".equals(payment.get("status"))) {
                return ResponseEntity.ok(Map.of(
                        "status", "rejected",
                        "paymentId", "",
                        "message", "Error al procesar el pago: " + payment.getOrDefault("error", "intentá de nuevo")
                ));
            }

            String mpStatus = (String) payment.get("status");
            String paymentId = payment.get("id") != null ? String.valueOf(payment.get("id")) : null;

            String bookingCode = null;
            if ("approved".equals(mpStatus)) {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setEquipajeId(dto.getEquipajeId());
                compraDTO.setAsistenciaId(dto.getAsistenciaId());
                compraDTO.setCuil(dto.getCuil());
                compraDTO.setSituacionFiscal(dto.getSituacionFiscal());
                compraDTO.setMetodoPago(MetodosDePago.MERCADOPAGO);
                compraDTO.setAsientosSeleccionados(dto.getAsientosSeleccionados());
                bookingCode = compraService.confirmarCompra(compraDTO, user);
            }

            return ResponseEntity.ok(Map.of(
                    "status", mpStatus != null ? mpStatus : "rejected",
                    "paymentId", paymentId != null ? paymentId : "",
                    "bookingCode", bookingCode != null ? bookingCode : "",
                    "message", obtenerMensajeStatus(mpStatus)
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "status", "rejected",
                    "paymentId", "",
                    "message", "Error interno: " + e.getMessage()
            ));
        }
    }

    private String obtenerMensajeStatus(String status) {
        if ("approved".equals(status)) return "Pago aprobado";
        if ("rejected".equals(status)) return "Pago rechazado";
        if ("pending".equals(status)) return "Pago pendiente";
        if ("in_process".equals(status)) return "Pago en proceso";
        return "Estado desconocido";
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> body) {
        String type = (String) body.get("type");
        if ("payment".equals(type)) {
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            String paymentId = data != null ? String.valueOf(data.get("id")) : null;
            if (paymentId != null) {
                Map<String, Object> payment = mpService.obtenerPago(paymentId);
                String status = (String) payment.get("status");
                if ("approved".equals(status)) {
                    String externalReference = (String) payment.get("external_reference");
                    compraService.confirmarPagoMP(externalReference, paymentId);
                }
            }
        }
        return ResponseEntity.ok("OK");
    }
}
