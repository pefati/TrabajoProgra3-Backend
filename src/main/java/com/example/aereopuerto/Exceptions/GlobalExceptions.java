package com.example.aereopuerto.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> AllErrors(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
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
}
