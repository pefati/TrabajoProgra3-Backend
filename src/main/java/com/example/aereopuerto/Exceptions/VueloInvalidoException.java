package com.example.aereopuerto.Exceptions;

public class VueloInvalidoException extends RuntimeException {
    public VueloInvalidoException(String message) {
        super(message);
    }
}
