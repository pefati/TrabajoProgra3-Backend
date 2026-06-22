package com.example.aereopuerto.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
   // @NotNull(message = "El id de cliente es obligatorio")
    //private Integer clienteId;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

   // @NotBlank(message = "El teléfono es obligatorio")
   // private String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un carácter especial"
    )
    private String password;
}
