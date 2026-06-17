package com.example.aereopuerto.auth.dto;

import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CompletarPerfilRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El nombre solo puede contener letras y espacios.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$", message = "El apellido solo puede contener letras y espacios.")
    private String apellido;

    @NotBlank(message = "El número de documento es obligatorio.")
    @Pattern(regexp = "^[0-9]+$", message = "El número de documento solo puede contener números positivos.")
    private String numeroIdentificador;

    @NotNull(message = "El tipo de identificador es obligatorio.")
    private Identificador identificador;

    @NotNull(message = "El sexo es obligatorio.")
    private Sexo sexo;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual.")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefono;
}