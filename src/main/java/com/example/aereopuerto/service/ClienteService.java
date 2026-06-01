package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Cliente;
import com.example.aereopuerto.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Cacheable(value = "clientes", key = "#id")
    public Cliente obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado. ID: " + id));
    }

    @Cacheable(value = "clientes", key = "'todos'")
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @CachePut(value = "clientes", key = "#result.id")
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @CachePut(value = "clientes", key = "#result.id")
    public Cliente actualizarCliente(Integer id, Cliente cliente) {

        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado. ID: " + id));

        c.setNombre(cliente.getNombre());
        c.setApellido(cliente.getApellido());
//        c.setMail(cliente.getMail());
        c.setIdentificador(cliente.getIdentificador());
  //      c.setTelefono(cliente.getTelefono());
        c.setNumeroIdentificador(cliente.getNumeroIdentificador());
  //      c.setFecha_nacimiento(cliente.getFecha_nacimiento());
        c.setSexo(cliente.getSexo());

        return clienteRepository.save(c);
    }

    @CacheEvict(value = "clientes", key = "#id")
    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }

    @CacheEvict(value = "clientes", key = "'todos'")
    public void invalidarListaDeClientes() {
    }


}
