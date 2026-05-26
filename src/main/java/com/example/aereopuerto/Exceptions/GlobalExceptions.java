package com.example.aereopuerto.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(AereolineaInvalidaException.class)
    public ResponseEntity<String> AereolineaInvalidaException(AereolineaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(AsignacionInvalidaException.class)
    public ResponseEntity<String> AsignacionInvalidaException(AsignacionInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(AvionInvalidoException.class)
    public ResponseEntity<String> AvionInvalidoException(AvionInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(ClienteInvalidoException.class)
    public ResponseEntity<String> ClienteInvalidoException(ClienteInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(EmpleadoInvalidoException.class)
    public ResponseEntity<String> EmpleadoInvalidoException(EmpleadoInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(FacturaInvalidaException.class)
    public ResponseEntity<String> FacturaInvalidaException(FacturaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(PistaInvalidaException.class)
    public ResponseEntity<String> PistaInvalidaException(PistaInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(PuertaEmbarqueInvalidoException.class)
    public ResponseEntity<String> PuertaEmbarqueInvalidoException(PuertaEmbarqueInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> AllErrors(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
