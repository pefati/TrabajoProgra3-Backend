package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Cliente;
import com.example.aereopuerto.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Cacheable(value = "clientes", key = "#id")
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado. ID: " + id));
    }

    @Cacheable(value = "clientes", key = "'todos'")
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @CachePut(value = "clientes", key = "#result.id")
    public Cliente crearOActualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = "clientes", key = "#id")
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @CacheEvict(value = "clientes", key = "'todos'")
    public void invalidarListaDeClientes() {
    }
}
