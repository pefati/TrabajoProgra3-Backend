package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Aeropuerto;
import com.example.aereopuerto.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer>, JpaSpecificationExecutor<Aeropuerto> {
    Optional<Aeropuerto> findByCodigoIata(String codigoIata);
}
