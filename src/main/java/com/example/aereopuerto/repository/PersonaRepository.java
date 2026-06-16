package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer>, JpaSpecificationExecutor<Persona> {
    Optional<Persona> findByNumeroIdentificador(String numeroIdentificador);
}
