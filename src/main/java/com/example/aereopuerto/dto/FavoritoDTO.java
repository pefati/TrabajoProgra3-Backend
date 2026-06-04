package com.example.aereopuerto.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoritoDTO {
    private Integer id;
    private Integer personaId;
    private Integer vueloId;
    private LocalDateTime fechaAgregado;
}
