package com.example.aereopuerto.Exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {


    @ExceptionHandler(AsignacionInvalidaException.class)
    public ResponseEntity<String> AsignacionInvalidaException(AsignacionInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(AvionInvalidoException.class)
    public ResponseEntity<String> AvionInvalidoException(AvionInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler(PersonaInvalidaException.class)
    public ResponseEntity<String> PersonaInvalidoException(PersonaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FacturaInvalidaException.class)
    public ResponseEntity<String> FacturaInvalidaException(FacturaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ReservaInvalidaException.class)
    public ResponseEntity<String> ReservaInvalidaException(ReservaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(VueloInvalidoException.class)
    public ResponseEntity<String> VueloInvalidoException(VueloInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CarritoInvalidoException.class)
    public ResponseEntity<String> CarritoInvalidoException(CarritoInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FavoritoInvalidoException.class)
    public ResponseEntity<String> FavoritoInvalidoException(FavoritoInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<java.util.Map<String, String>> DataIntegrityViolation(DataIntegrityViolationException ex) {
        String msg = "Ya existe un registro con esos datos. Verificá que el número de documento no esté en uso.";
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(java.util.Map.of("message", msg));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> AllErrors(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AeropuertoInvalidoException.class)
    public ResponseEntity<String> AeropuertoInvalidoException(AeropuertoInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PasajeInvalidoException.class)
    public ResponseEntity<String> PasajeInvalidoException(PasajeInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EquipajeInvalidoException.class)
    public ResponseEntity<String> EquipajeInvalidoException(PasajeInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AsistenciaInvalidaException.class)
    public ResponseEntity<String> AsistenciaInvalidoException(AsistenciaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AsientoInvalidoException.class)
    public ResponseEntity<String> AsientoInvalidoException(AsientoInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
