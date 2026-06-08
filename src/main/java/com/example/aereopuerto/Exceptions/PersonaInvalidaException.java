package com.example.aereopuerto.Exceptions;

public class PersonaInvalidaException extends RuntimeException {
    public PersonaInvalidaException(String message) {
        super(message);
    }
}