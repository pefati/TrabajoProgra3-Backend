package com.example.aereopuerto.dto;

import lombok.Data;
import java.util.List;

@Data
public class CarritoDTO {
    private Integer id;
    private Integer personaId;
    private List<CarritoItemDTO> items;
}
