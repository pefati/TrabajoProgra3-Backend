package com.example.aereopuerto.Specifications;
import com.example.aereopuerto.model.Persona;
import com.example.aereopuerto.model.enums.Identificador;
import com.example.aereopuerto.model.enums.Sexo;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;

public class PersonaSpecification {

    public static Specification<Persona> porNombre(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Persona> porApellido(String apellido) {
        return (root, query, criteriaBuilder) -> {
            if (apellido == null || apellido.trim().isEmpty()) return null;
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("apellido")),
                    "%" + apellido.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Persona> porIdentificador(Identificador identificador) {
        return (root, query, criteriaBuilder) -> {
            if (identificador == null) return null;
            return criteriaBuilder.equal(root.get("identificador"), identificador);
        };
    }

    public static Specification<Persona> porNumeroIdentificador(String numeroIdentificador) {
        return (root, query, criteriaBuilder) -> {
            if (numeroIdentificador == null || numeroIdentificador.trim().isEmpty()) return null;
            return criteriaBuilder.equal(root.get("numeroIdentificador"), numeroIdentificador.trim());
        };
    }

    public static Specification<Persona> porSexo(Sexo sexo) {
        return (root, query, criteriaBuilder) -> {
            if (sexo == null) return null;
            return criteriaBuilder.equal(root.get("sexo"), sexo);
        };
    }

    public static Specification<Persona> porFechaNacimiento(Date fechaNacimiento) {
        return (root, query, criteriaBuilder) -> {
            if (fechaNacimiento == null) return null;
            return criteriaBuilder.equal(root.get("fechaNacimiento"), fechaNacimiento);
        };
    }
}