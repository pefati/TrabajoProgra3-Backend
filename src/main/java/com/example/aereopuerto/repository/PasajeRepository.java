package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Pasaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasajeRepository extends JpaRepository<Pasaje, Integer> {
    long countByVueloId(Integer vueloId);
    List<Pasaje> findByReservaId(Integer reservaId);
    @Query("SELECT p.vuelo.id AS vueloId, COUNT(p) AS cnt FROM Pasaje p WHERE p.vuelo.id IN :ids GROUP BY p.vuelo.id")
    List<Object[]> countGroupedByVueloIdIn(@Param("ids") List<Integer> ids);
}
