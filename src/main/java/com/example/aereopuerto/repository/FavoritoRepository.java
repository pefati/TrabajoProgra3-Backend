package com.example.aereopuerto.repository;

import com.example.aereopuerto.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Integer> {
    List<Favorito> findByPersonaId(Integer personaId);
    Optional<Favorito> findByPersonaIdAndVueloId(Integer personaId, Integer vueloId);
}
