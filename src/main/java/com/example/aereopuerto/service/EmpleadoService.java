package com.example.aereopuerto.service;

import com.example.aereopuerto.model.Empleado;
import com.example.aereopuerto.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    @Autowired
    private final EmpleadoRepository empleadoRepository;

    @Cacheable(value = "empleados", key = "#id")
    public Empleado obtenerEmpleadoPorId(Integer id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado. ID: " + id));
    }

    @Cacheable(value = "empleados", key = "'todos'")
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    @CachePut(value = "empleados", key = "#result.id")
    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @CachePut(value = "empleados", key = "#result.id")
    public Empleado actualizarEmpleado(Integer id, Empleado empleado) {

        Empleado e = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado. ID: " + id));

        e.setNombre(empleado.getNombre());
        e.setApellido(empleado.getApellido());
        e.setFechaNacimiento(empleado.getFechaNacimiento());

        return empleadoRepository.save(e);
    }

    @CacheEvict(value = "empleados", key = "#id")
    public void eliminarEmpleado(Integer id) {
        empleadoRepository.deleteById(id);
    }

    @CacheEvict(value = "empleados", key = "'todos'")
    public void invalidarListaDeEmpleados() {
    }
}
