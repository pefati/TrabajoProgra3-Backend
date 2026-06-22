package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
    List<Asiento> findByAvionId(Integer avionId);

    @Query("SELECT a FROM Asiento a WHERE a.avion.id = :avionId AND (a.ocupado IS NULL OR a.ocupado = false)")
    List<Asiento> findAvailableByAvionId(Integer avionId);
}
