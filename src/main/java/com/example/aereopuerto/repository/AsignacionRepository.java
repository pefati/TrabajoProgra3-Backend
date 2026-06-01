package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {
}
