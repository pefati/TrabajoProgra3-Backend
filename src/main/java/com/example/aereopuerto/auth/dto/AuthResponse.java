package com.example.aereopuerto.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private Integer userId;
    private Boolean perfilCompleto;

    public AuthResponse(String nuevoToken) {
        this.token = nuevoToken;
    }
}
