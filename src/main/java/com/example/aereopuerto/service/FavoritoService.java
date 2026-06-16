package com.example.aereopuerto.service;

import com.example.aereopuerto.Exceptions.FavoritoInvalidoException;
import com.example.aereopuerto.Exceptions.PersonaInvalidaException;
import com.example.aereopuerto.Exceptions.VueloInvalidoException;
import com.example.aereopuerto.auth.entity.User;
import com.example.aereopuerto.auth.repository.UserRepository;
import com.example.aereopuerto.dto.FavoritoDTO;
import com.example.aereopuerto.model.Favorito;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.repository.FavoritoRepository;
import com.example.aereopuerto.repository.PersonaRepository;
import com.example.aereopuerto.repository.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final PersonaRepository personaRepository;
    private final VueloRepository vueloRepository;
    private final UserRepository userRepository;


    @Cacheable(value = "favoritos", key = "#personaId")
    public List<FavoritoDTO> getFavoritosByPersonaId(Integer personaId) {
        return favoritoRepository.findByPersonaId(personaId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @CacheEvict(value = "favoritos", key = "#personaId")
    public FavoritoDTO addFavorito(Integer personaId, Integer vueloId) {
        if (favoritoRepository.findByPersonaIdAndVueloId(personaId, vueloId).isPresent()) {
            throw new FavoritoInvalidoException("El vuelo ya se encuentra en favoritos para este usuario.");
        }

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new PersonaInvalidaException("Usuario no encontrado con id: " + personaId));

        Vuelo vuelo = vueloRepository.findById(vueloId)
                .orElseThrow(() -> new VueloInvalidoException("Vuelo no encontrado con id: " + vueloId));

        Favorito favorito = Favorito.builder()
                .persona(persona)
                .vuelo(vuelo)
                .fechaAgregado(LocalDateTime.now())
                .build();

        return mapToDTO(favoritoRepository.save(favorito));
    }

    @CacheEvict(value = "favoritos", key = "#personaId")
    public void removeFavorito(Integer personaId, Integer favoritoId) {
        Favorito favorito = favoritoRepository.findById(favoritoId)
                .orElseThrow(() -> new FavoritoInvalidoException("Favorito no encontrado con id: " + favoritoId));
        if (!favorito.getPersona().getId().equals(personaId)) {
            throw new FavoritoInvalidoException("El favorito no pertenece a esta persona.");
        }

        favoritoRepository.delete(favorito);
    }

    private FavoritoDTO mapToDTO(Favorito favorito) {
        FavoritoDTO dto = new FavoritoDTO();
        dto.setId(favorito.getId());
        dto.setPersonaId(favorito.getPersona().getId());
        dto.setVueloId(favorito.getVuelo().getId());
        dto.setFechaAgregado(favorito.getFechaAgregado());
        return dto;
    }

    @CacheEvict(value = "favoritos", key = "#result.personaId")
    public FavoritoDTO addFavoritoPorToken(String email, Integer vueloId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new PersonaInvalidaException("No se encontro al usuario"));

        Persona persona = user.getPersona();
        if (persona == null) {
            throw new PersonaInvalidaException("El usuario no tiene una persona asociada.");
        }

        if (favoritoRepository.findByPersonaIdAndVueloId(persona.getId(), vueloId).isPresent()) {
            throw new FavoritoInvalidoException("El vuelo ya se encuentra en favoritos.");
        }

        Vuelo vuelo = vueloRepository.findById(vueloId)
                .orElseThrow(() -> new VueloInvalidoException("Vuelo no encontrado con id: " + vueloId));

        Favorito favorito = Favorito.builder()
                .persona(persona)
                .vuelo(vuelo)
                .fechaAgregado(LocalDateTime.now())
                .build();

        return mapToDTO(favoritoRepository.save(favorito));
    }

    @Cacheable(value = "favoritos", key = "#result != null && !#result.isEmpty() ? #result[0].personaId : #email")
    public List<FavoritoDTO> getFavoritosPorToken(String email) {

        User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new PersonaInvalidaException("Usuario no encontrado para el token provisto."));

        Persona persona = usuario.getPersona();
        if (persona == null) {
            throw new PersonaInvalidaException("El usuario no tiene una persona asociada.");
        }

        return favoritoRepository.findByPersonaId(persona.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

}


