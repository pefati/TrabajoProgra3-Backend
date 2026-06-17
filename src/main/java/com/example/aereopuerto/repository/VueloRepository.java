package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer>, JpaSpecificationExecutor<Vuelo> {

    @Query("""
    SELECT COUNT(v) > 0 FROM Vuelo v
    WHERE v.avion.id = :avionId
    AND v.fechaSalida = :fechaSalida
    AND v.id <> :excludeId
    AND (
        (v.horaSalida < :horaLlegada AND v.horaLlegada > :horaSalida)
    )
""")
    boolean existeConflictoHorario(
            @Param("avionId") Integer avionId,
            @Param("fechaSalida") LocalDate fechaSalida,
            @Param("horaSalida") LocalTime horaSalida,
            @Param("horaLlegada") LocalTime horaLlegada,
            @Param("excludeId") Integer excludeId
    );

}
