package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    Optional<Aeropuerto> findByCodigoIata(String codigoIata);
}
