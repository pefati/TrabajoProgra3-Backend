package com.example.aereopuerto.auth.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer cliente_id;
    private String email;
    private String telefono;
    private String password;
}
