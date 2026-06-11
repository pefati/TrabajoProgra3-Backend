package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Pasaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasajeRepository extends JpaRepository<Pasaje, Integer> {
    long countByVueloId(Integer vueloId);
}
