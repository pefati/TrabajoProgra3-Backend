package com.example.aereopuerto.Specifications;

import com.example.aereopuerto.model.Reserva;
import com.example.aereopuerto.model.enums.EstadoReserva;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ReservaSpecification {

    public static Specification<Reserva> porPersonaId(Integer personaId) {
        return (root, query, criteriaBuilder) -> {
            if (personaId == null)
                return null;
            return criteriaBuilder.equal(root.get("persona").get("id"), personaId);
        };
    }

    public static Specification<Reserva> porEstado(EstadoReserva estado) {
        return (root, query, criteriaBuilder) -> {
            if (estado == null)
                return null;
            return criteriaBuilder.equal(root.get("estadoReserva"), estado);
        };
    }

    public static Specification<Reserva> porFechaDesde(LocalDateTime fechaDesde) {
        return (root, query, criteriaBuilder) -> {
            if (fechaDesde == null)
                return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaReserva"), fechaDesde);
        };
    }

    public static Specification<Reserva> porFechaHasta(LocalDateTime fechaHasta) {
        return (root, query, criteriaBuilder) -> {
            if (fechaHasta == null)
                return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("fechaReserva"), fechaHasta);
        };
    }

    public static Specification<Reserva> porValorMinimo(Double valorMinimo) {
        return (root, query, criteriaBuilder) -> {
            if (valorMinimo == null)
                return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("valor"), valorMinimo);
        };
    }

    public static Specification<Reserva> porValorMaximo(Double valorMaximo) {
        return (root, query, criteriaBuilder) -> {
            if (valorMaximo == null)
                return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("valor"), valorMaximo);
        };
    }

    public static Specification<Reserva> porCantidadPasajes(Integer cantidadPasajes) {
        return (root, query, criteriaBuilder) -> {
            if (cantidadPasajes == null)
                return null;
            return criteriaBuilder.equal(root.get("cantidadPasajes"), cantidadPasajes);
        };
    }
}