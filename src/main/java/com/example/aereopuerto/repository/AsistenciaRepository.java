package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.AsistenciaAlViajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaRepository extends JpaRepository<AsistenciaAlViajero, Integer> {
}
