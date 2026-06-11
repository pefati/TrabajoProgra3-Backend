package com.example.aereopuerto.Specifications;

import com.example.aereopuerto.model.Vuelo;
import com.example.aereopuerto.model.enums.estadoVuelo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class VueloSpecification {

    public static Specification<Vuelo> porCiudadOrigen(String ciudadOrigen) {
        return (root, query, criteriaBuilder) -> {
            if (ciudadOrigen == null || ciudadOrigen.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("aeropuertoOrigen").get("ciudad")),
                    "%" + ciudadOrigen.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Vuelo> porCiudadDestino(String ciudadDestino) {
        return (root, query, criteriaBuilder) -> {
            if (ciudadDestino == null || ciudadDestino.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("aeropuertoDestino").get("ciudad")),
                    "%" + ciudadDestino.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Vuelo> porFechaSalidaDesde(LocalDateTime fechaSalida) {
        return (root, query, criteriaBuilder) -> {
            if (fechaSalida == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaSalida"), fechaSalida);
        };
    }

    public static Specification<Vuelo> porFechaLlegadaHasta(LocalDateTime fechaLlegada) {
        return (root, query, criteriaBuilder) -> {
            if (fechaLlegada == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("fechaLlegada"), fechaLlegada);
        };
    }

    public static Specification<Vuelo> precioMenorOIgualA(Double precioMaximo) {
        return (root, query, criteriaBuilder) -> {
            if (precioMaximo == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("precioVuelo"), precioMaximo);
        };
    }

    public static Specification<Vuelo> porEscala(Boolean tieneEscala) {
        return (root, query, criteriaBuilder) -> {
            if (tieneEscala == null) return null;
            return criteriaBuilder.equal(root.get("escala"), tieneEscala);
        };
    }

    public static Specification<Vuelo> porEstado(estadoVuelo estado) {
        return (root, query, criteriaBuilder) -> {
            if (estado == null) return null;
            return criteriaBuilder.equal(root.get("estado"), estado);
        };
    }
}