package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Empleado;
import com.example.aereopuerto.repository.EmpleadoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Cacheable(value = "empleados", key = "#id")
    public Empleado obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado. ID: " + id));
    }

    @Cacheable(value = "empleados", key = "'todos'")
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    @CachePut(value = "empleados", key = "#result.id")
    public Empleado crearOActualizarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @CacheEvict(value = "empleados", key = "#id")
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    @CacheEvict(value = "empleados", key = "'todos'")
    public void invalidarListaDeEmpleados() {
    }
}
