package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonaService {

    @Autowired
    private final PersonaRepository personaRepository;

    @Cacheable(value = "clientes", key = "#id")
    public Persona obtenerClientePorId(Integer id) {
        return personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado. ID: " + id));
    }

    @Cacheable(value = "clientes", key = "'todos'")
    public List<Persona> obtenerTodosLosClientes() {
        return personaRepository.findAll();
    }

    @CachePut(value = "clientes", key = "#result.id")
    @CacheEvict(value = "clientes", key = "'todos'")
    public Persona crearCliente(Persona persona) {
        return personaRepository.save(persona);
    }

    @CachePut(value = "clientes", key = "#result.id")
    @CacheEvict(value = "clientes", key = "'todos'")
    public Persona actualizarCliente(Integer id, Persona persona) {

        Persona c = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado. ID: " + id));

        c.setNombre(persona.getNombre());
        c.setApellido(persona.getApellido());
//        c.setMail(cliente.getMail());
        c.setIdentificador(persona.getIdentificador());
  //      c.setTelefono(cliente.getTelefono());
        c.setNumeroIdentificador(persona.getNumeroIdentificador());
  //      c.setFecha_nacimiento(cliente.getFecha_nacimiento());
        c.setSexo(persona.getSexo());

        return personaRepository.save(c);
    }

    @Caching(evict = {
            @CacheEvict(value = "clientes", key = "#id"),
            @CacheEvict(value = "clientes", key = "'todos'")
    })
    public void eliminarCliente(Integer id) {
        personaRepository.deleteById(id);
    }

    @CacheEvict(value = "clientes", key = "'todos'")
    public void invalidarListaDeClientes() {
    }


}
