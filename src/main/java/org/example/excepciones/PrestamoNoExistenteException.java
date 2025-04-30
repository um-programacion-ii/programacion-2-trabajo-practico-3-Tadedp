package org.example.excepciones;

public class PrestamoNoExistenteException extends RuntimeException {
    public PrestamoNoExistenteException(String message) {
        super(message);
    }
}
