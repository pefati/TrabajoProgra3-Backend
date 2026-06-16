package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Factura;
import com.example.aereopuerto.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByReservaPersona(Persona persona);
}
