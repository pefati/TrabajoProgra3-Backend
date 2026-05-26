package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Aereolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AereolineaRepository extends JpaRepository<Aereolinea, Long> {
}
