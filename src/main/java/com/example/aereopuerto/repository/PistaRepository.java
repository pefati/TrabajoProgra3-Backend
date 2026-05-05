package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Long> {
}
