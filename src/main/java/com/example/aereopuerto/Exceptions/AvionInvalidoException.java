package com.example.aereopuerto.Exceptions;

public class AvionInvalidoException extends RuntimeException {
    public AvionInvalidoException(String message) {
        super(message);
    }
}
