package org.example.gestores;

import org.example.enums.EstadoLibro;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;

import java.util.ArrayList;

public class SistemaPrestamos {
    private Catalogo catalogo;
    private ArrayList<Prestamo> prestamosActivos = new ArrayList<>();

    public SistemaPrestamos() {
        catalogo = new Catalogo();
    }

    public SistemaPrestamos(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public ArrayList<Prestamo> getPrestamosActivos() {
        return prestamosActivos;
    }

    public Prestamo buscarPrestamo(String isbn) {
        Libro libro = catalogo.buscarLibroPorISBN(isbn);
        if (libro != null) {
            for ( Prestamo prestamo : prestamosActivos ) {
                if ( prestamo.getLibro() == libro ) {
                    return prestamo;
                }
            }
        }
        return null;
    }

    public Prestamo prestarLibro(String isbn) {
        Libro libro = catalogo.buscarLibroPorISBN(isbn);
        if (libro  != null && libro.getEstado() == EstadoLibro.DISPONIBLE) {
            Prestamo prestamo = new Prestamo(libro);
            prestamosActivos.add(prestamo);
            libro.setEstado(EstadoLibro.PRESTADO);
            return prestamo;
        }
        return null;
    }

    public Prestamo devolverLibro(String isbn) {
        Libro libro = catalogo.buscarLibroPorISBN(isbn);
        if (libro  != null) {
            for ( Prestamo prestamo : prestamosActivos ) {
                if ( prestamo.getLibro() == libro ) {
                    prestamosActivos.remove(prestamo);
                    libro.setEstado(EstadoLibro.DISPONIBLE);
                    return prestamo;
                }
            }
        }
        return null;
    }
}
