package com.example.aereopuerto.Specifications;

import com.example.aereopuerto.model.Vuelo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class VueloSpecification {

    public static Specification<Vuelo> porCiudadOrigen(String ciudadOrigen) {
        return (root, query, criteriaBuilder) -> {
            if (ciudadOrigen == null || ciudadOrigen.trim().isEmpty()) return null;
            // Hacemos el join con la relación 'aeropuertoOrigen' y buscamos por su atributo 'ciudad'
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("aeropuertoOrigen").get("ciudad")),
                    "%" + ciudadOrigen.toLowerCase() + "%"
            );
        };
    }

    // 2. Filtrar por Ciudad de Destino (Atributo dentro de Aeropuerto)
    public static Specification<Vuelo> porCiudadDestino(String ciudadDestino) {
        return (root, query, criteriaBuilder) -> {
            if (ciudadDestino == null || ciudadDestino.trim().isEmpty()) return null;
            // Hacemos el join con la relación 'aeropuertoDestino' y buscamos por su atributo 'ciudad'
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("aeropuertoDestino").get("ciudad")),
                    "%" + ciudadDestino.toLowerCase() + "%"
            );
        };
    }

    // 3. Filtrar por Fecha de Salida (Busca vuelos que salgan en esa fecha o después)
    public static Specification<Vuelo> porFechaSalidaDesde(LocalDateTime fechaSalida) {
        return (root, query, criteriaBuilder) -> {
            if (fechaSalida == null) return null;
            return criteriaBuilder.greaterThanOrEqualTo(root.get("fechaSalida"), fechaSalida);
        };
    }

    // 4. Filtrar por Fecha de Llegada (Busca vuelos que lleguen en esa fecha o antes)
    public static Specification<Vuelo> porFechaLlegadaHasta(LocalDateTime fechaLlegada) {
        return (root, query, criteriaBuilder) -> {
            if (fechaLlegada == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("fechaLlegada"), fechaLlegada);
        };
    }

    // 5. Filtrar por Precio Máximo
    public static Specification<Vuelo> precioMenorOIgualA(Double precioMaximo) {
        return (root, query, criteriaBuilder) -> {
            if (precioMaximo == null) return null;
            return criteriaBuilder.lessThanOrEqualTo(root.get("precioVuelo"), precioMaximo);
        };
    }

    // 6. Filtrar si tiene Escala o es Directo
    public static Specification<Vuelo> porEscala(Boolean tieneEscala) {
        return (root, query, criteriaBuilder) -> {
            if (tieneEscala == null) return null;
            return criteriaBuilder.equal(root.get("escala"), tieneEscala);
        };
    }

}

