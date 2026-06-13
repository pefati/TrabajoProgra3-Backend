package com.example.aereopuerto.auth.dto;

import com.example.aereopuerto.model.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfilResponse {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String numeroIdentificador;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
}
