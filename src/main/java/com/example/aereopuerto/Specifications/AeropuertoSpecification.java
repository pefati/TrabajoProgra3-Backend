package com.example.aereopuerto.Specifications;

import com.example.aereopuerto.model.Aeropuerto;
import org.springframework.data.jpa.domain.Specification;

public class AeropuertoSpecification {
    public static Specification<Aeropuerto> porNombre(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Aeropuerto> porCodigoIata(String codigoIata) {
        return (root, query, criteriaBuilder) -> {
            if (codigoIata == null || codigoIata.trim().isEmpty()) return null;
            return criteriaBuilder.equal(
                    criteriaBuilder.upper(root.get("codigoIata")),
                    codigoIata.trim().toUpperCase()
            );
        };
    }

    public static Specification<Aeropuerto> porCiudad(String ciudad) {
        return (root, query, criteriaBuilder) -> {
            if (ciudad == null || ciudad.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("ciudad")),
                    "%" + ciudad.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Aeropuerto> porPais(String pais) {
        return (root, query, criteriaBuilder) -> {
            if (pais == null || pais.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("pais")),
                    "%" + pais.toLowerCase() + "%"
            );
        };
    }
}

