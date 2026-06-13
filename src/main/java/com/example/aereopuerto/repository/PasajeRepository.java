package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Pasaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasajeRepository extends JpaRepository<Pasaje, Integer> {
    long countByVueloId(Integer vueloId);
    List<Pasaje> findByReservaId(Integer reservaId);
}
