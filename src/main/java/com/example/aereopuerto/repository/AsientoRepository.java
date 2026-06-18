package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
    List<Asiento> findByAvionId(Integer avionId);
}
