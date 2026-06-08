package com.example.aereopuerto.auth.service;

import com.example.aereopuerto.auth.dto.AuthResponse;
import com.example.aereopuerto.auth.dto.LoginRequest;
import com.example.aereopuerto.auth.dto.RegisterRequest;
import com.example.aereopuerto.auth.dto.RegisterRequestSinPersona;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.auth.repository.UserRepository;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import com.example.aereopuerto.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PersonaRepository personaRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Persona persona = personaRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + request.getClienteId()));

        User user = User.builder()
                .persona(persona)
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .build();
    }

    public AuthResponse registerSinPersona (RegisterRequestSinPersona request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }


        Persona persona = new Persona();
        persona.setApellido("Pendiente");
        persona.setSexo(Sexo.OTRO);  //por defecto puse que la creacion sea con Sexo.OTRO , el usuario lo cambiara al completar su perfil si lo desea
        persona.setIdentificador(Identificador.DNI); //por defecto puse que la creacion sea con DNI, el usuario lo cambiara al completar su perfil si lo desea
        persona.setNombre("Pendiente");
        persona.setNumeroIdentificador("Pendiente");
        persona.setFechaNacimiento(LocalDateTime.now());
        personaRepository.save(persona);

        User user = User.builder()
                .persona(persona)
                .email(request.getEmail())
                .telefono("Pendiente")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .build();
    }

}
