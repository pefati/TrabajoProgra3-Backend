package com.example.aereopuerto.service;

import com.example.aereopuerto.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final RestClient restClient;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    private HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        return headers;
    }

    public Map<String, Object> crearPreferencia(double total, String descripcion, List<String> items, User user, String externalReference, String backUrl) {
        var backUrls = Map.of(
            "success", backUrl,
            "failure", backUrl,
            "pending", backUrl
        );

        var body = Map.of(
            "items", List.of(Map.of(
                "title", descripcion,
                "quantity", 1,
                "currency_id", "ARS",
                "unit_price", (float) total
            )),
            "payer", Map.of("email", user.getEmail()),
            "external_reference", externalReference,
            "back_urls", backUrls,
            "auto_return", "approved",
            "statement_descriptor", "AEROGEST"
        );

        return restClient.post()
                .uri("https://api.mercadopago.com/checkout/preferences")
                .headers(h -> h.addAll(authHeaders()))
                .body(body)
                .retrieve()
                .body(Map.class);
    }

    public Map<String, Object> obtenerPago(String paymentId) {
        return restClient.get()
                .uri("https://api.mercadopago.com/v1/payments/{paymentId}", paymentId)
                .headers(h -> h.addAll(authHeaders()))
                .retrieve()
                .body(Map.class);
    }

    public Map<String, Object> procesarPago(String token, double amount, int installments,
                                              String paymentMethodId, String issuerId,
                                              String payerEmail, String payerDocType,
                                              String payerDocNumber) {
        // simulando pago de momentop
        Map<String, Object> mock = new HashMap<>();
        mock.put("status", "approved");
        mock.put("id", 123456789);
        return mock;
    }
}
