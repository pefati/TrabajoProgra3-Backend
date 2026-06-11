package com.example.aereopuerto.auth.service;

import com.example.aereopuerto.auth.dto.*;
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
                .perfilCompleto(user.getPerfilCompleto())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        //Persona persona = personaRepository.findById(request.getClienteId())
        //        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + request.getClienteId()));

        Persona persona = new Persona();
        persona.setApellido("Pendiente");
        persona.setSexo(null);
        persona.setIdentificador(null);
        persona.setNombre("Pendiente");
        persona.setNumeroIdentificador(null);
        persona.setFechaNacimiento(null);
        personaRepository.save(persona);

        User user = User.builder()
                .persona(persona)
                .email(request.getEmail())
                .telefono("Pendiente")
                .password(passwordEncoder.encode(request.getPassword()))
                .perfilCompleto(false)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user, user.getId());
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .userId(user.getId())
                .perfilCompleto(user.getPerfilCompleto())
                .build();
    }


    public void completarPerfil(Integer userId, CompletarPerfilRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        if (Boolean.TRUE.equals(user.getPerfilCompleto())) {
            throw new IllegalArgumentException(
                    "El perfil ya fue completado"
            );
        }

        if (request.getNombre() == null || request.getNombre().isBlank() ||
                request.getApellido() == null || request.getApellido().isBlank() ||
                request.getNumeroIdentificador() == null || request.getNumeroIdentificador().isBlank() ||
                request.getIdentificador() == null ||
                request.getSexo() == null ||
                request.getFechaNacimiento() == null ||
                request.getTelefono() == null || request.getTelefono().isBlank()) {

            throw new IllegalArgumentException(
                    "Todos los campos son obligatorios"
            );
        }

        Persona persona = user.getPersona();

        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setNumeroIdentificador(request.getNumeroIdentificador());
        persona.setIdentificador(request.getIdentificador());
        persona.setSexo(request.getSexo());
        persona.setFechaNacimiento(request.getFechaNacimiento());
        user.setTelefono(request.getTelefono());

        user.setPerfilCompleto(true);

        personaRepository.save(persona);
        userRepository.save(user);
    }
/*
    public AuthResponse registerSinPersona (RegisterRequestSinPersona request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }


        Persona persona = new Persona();
        persona.setApellido("Pendiente");
        persona.setSexo(Sexo.OTRO);  //por defecto puse que la creacion sea con Sexo.OTRO , el usuario lo cambiara al completar su perfil si lo desea
        persona.setIdentificador(Identificador.DNI); //por defecto puse que la creacion sea con DNI, el usuario lo cambiara al completar su perfil si lo desea
        persona.setNombre("Pendiente");
        persona.setNumeroIdentificador(request.getDni());
        persona.setFechaNacimiento(null);
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

 */

}
