package com.example.aereopuerto.dto;

import com.example.aereopuerto.model.enums.RolEmpleado;
import lombok.Data;

@Data
public class AsignacionDTO {

    private Integer vueloId;
    private Integer empleadoId;
    private RolEmpleado rolEmpleado;


}
