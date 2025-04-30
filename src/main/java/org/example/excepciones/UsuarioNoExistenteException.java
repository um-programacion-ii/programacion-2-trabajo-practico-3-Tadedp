package org.example.excepciones;

public class UsuarioNoExistenteException extends RuntimeException {
    public UsuarioNoExistenteException(String message) {
        super(message);
    }
}
