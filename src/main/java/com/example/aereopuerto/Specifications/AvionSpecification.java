package com.example.aereopuerto.Specifications;

import com.example.aereopuerto.model.Avion;
import com.example.aereopuerto.model.enums.estadoAvion;
import org.springframework.data.jpa.domain.Specification;

public class AvionSpecification {

    public static Specification<Avion> porIdentificador(String identificador) {
        return (root, query, criteriaBuilder) -> {
            if (identificador == null || identificador.trim().isEmpty())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("identificador")),
                    "%" + identificador.toLowerCase() + "%");
        };
    }

    public static Specification<Avion> porModelo(String modelo) {
        return (root, query, criteriaBuilder) -> {
            if (modelo == null || modelo.trim().isEmpty())
                return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("modelo")),
                    "%" + modelo.toLowerCase() + "%");
        };
    }

    public static Specification<Avion> porEstado(estadoAvion estado) {
        return (root, query, criteriaBuilder) -> {
            if (estado == null)
                return null;
            return criteriaBuilder.equal(root.get("estado"), estado);
        };
    }

    public static Specification<Avion> capacidadPasajerosMinimaDesde(Integer minimo) {
        return (root, query, criteriaBuilder) -> {
            if (minimo == null)
                return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("capacidadPasajeros"), minimo);
        };
    }

    public static Specification<Avion> capacidadPasajerosMaximaHasta(Integer maximo) {
        return (root, query, criteriaBuilder) -> {
            if (maximo == null)
                return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("capacidadPasajeros"), maximo);
        };
    }

    public static Specification<Avion> capacidadBodegaMinimaDesde(Double minimo) {
        return (root, query, criteriaBuilder) -> {
            if (minimo == null)
                return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("capacidadBodega"), minimo);
        };
    }

    public static Specification<Avion> capacidadBodegaMaximaHasta(Double maximo) {
        return (root, query, criteriaBuilder) -> {
            if (maximo == null)
                return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("capacidadBodega"), maximo);
        };
    }
}