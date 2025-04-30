package org.example.excepciones;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String message) {
        super(message);
    }
}
