package org.example.excepciones;

public class LibroNoExistenteException extends RuntimeException {
    public LibroNoExistenteException(String message) {
        super(message);
    }
}
