package com.example.aereopuerto.auth.service;

import com.example.aereopuerto.auth.dto.*;
import com.example.aereopuerto.auth.entity.Role;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.auth.repository.UserRepository;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import com.example.aereopuerto.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .role(Role.ROLE_INCOMPLETO)
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

    public PerfilResponse obtenerPerfil() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        Persona persona = user.getPersona();

        return PerfilResponse.builder()
                .personaId(persona.getId())
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .numeroIdentificador(persona.getNumeroIdentificador())
                .identificador(persona.getIdentificador())
                .fechaNacimiento(persona.getFechaNacimiento())
                .sexo(persona.getSexo())
                .build();
    }

    public List<UsuarioResponse> obtenerUsuarios() {

        List<User> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(user -> UsuarioResponse.builder()
                        .id(user.getId())
                        .nombre(user.getPersona().getNombre())
                        .apellido(user.getPersona().getApellido())
                        .email(user.getEmail())
                        .telefono(user.getTelefono())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    public UsuarioResponse obtenerUsuario (Integer id)
    {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        Persona persona = user.getPersona();

        return UsuarioResponse.builder()
                .id(user.getId())
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .role(user.getRole())
                .build();
    }


    public ResponseEntity<AuthResponse> completarPerfil(CompletarPerfilRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
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
        user.setRole(Role.ROLE_USUARIO);

        personaRepository.save(persona);
        userRepository.save(user);

        String nuevoToken = jwtService.generateToken(user, user.getId());
        return ResponseEntity.ok(new AuthResponse(nuevoToken));


    }

    public ResponseEntity<AuthResponse> actualizarPerfil(CompletarPerfilRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        validarPerfilRequest(request);

        Persona persona = user.getPersona();

        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setNumeroIdentificador(request.getNumeroIdentificador());
        persona.setIdentificador(request.getIdentificador());
        persona.setSexo(request.getSexo());
        persona.setFechaNacimiento(request.getFechaNacimiento());
        user.setTelefono(request.getTelefono());

        user.setPerfilCompleto(true);
        user.setRole(Role.ROLE_USUARIO);

        personaRepository.save(persona);
        userRepository.save(user);

        String nuevoToken = jwtService.generateToken(user, user.getId());
        return ResponseEntity.ok(new AuthResponse(nuevoToken));
    }

    private void validarPerfilRequest(CompletarPerfilRequest request) {
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
