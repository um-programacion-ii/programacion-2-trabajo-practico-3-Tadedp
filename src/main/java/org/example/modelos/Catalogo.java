package org.example.modelos;

import org.example.enums.EstadoLibro;
import org.example.excepciones.LibroNoExistenteException;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private ArrayList<Libro> libros = new ArrayList<>();

    public Catalogo() {
    }

    public List<Libro> getLibros() {
        return libros.stream()
                .filter(libro -> libro.getEstado() == EstadoLibro.DISPONIBLE)
                .toList();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void eliminarLibro(Libro libro) {
        libros.remove(libro);
    }

    public Libro buscarLibroPorISBN(String ISBN) {
        for (Libro libro : libros) {
            if (libro.getISBN().equals(ISBN)) {
                return libro;
            }
        }
        throw new LibroNoExistenteException("No existe ningún libro con ISBN: " + ISBN);
    }
}
