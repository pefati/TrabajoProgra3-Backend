package com.example.aereopuerto.auth.service;

import com.example.aereopuerto.auth.entity.Role;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void cambiarRol(Integer userId, Role nuevoRol) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (nuevoRol == Role.ROLE_INCOMPLETO) {
            throw new IllegalArgumentException("No se puede asignar el rol INCOMPLETO manualmente");
        }

        user.setRole(nuevoRol);
        userRepository.save(user);
    }
}