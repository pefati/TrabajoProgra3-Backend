package com.example.aereopuerto.auth.dto;

import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletarPerfilRequest {
    private String nombre;
    private String apellido;
    private String numeroIdentificador;
    private Identificador identificador;
    private Sexo sexo;
    private Date fechaNacimiento;
    private String telefono;
}
