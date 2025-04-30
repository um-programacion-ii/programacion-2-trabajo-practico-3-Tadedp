package org.example.gestores;

import org.example.enums.EstadoLibro;
import org.example.excepciones.LibroNoDisponibleException;
import org.example.excepciones.LibroNoExistenteException;
import org.example.excepciones.PrestamoNoExistenteException;
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

    public Prestamo buscarPrestamo(String isbn) throws LibroNoExistenteException {
        Libro libro = catalogo.buscarLibroPorISBN(isbn);
        for ( Prestamo prestamo : prestamosActivos ) {
            if ( prestamo.getLibro() == libro ) {
                return prestamo;
            }
        }
        throw new PrestamoNoExistenteException("No existe ningún préstamo para el libro de ISBN: " + isbn);
    }

    public Prestamo prestarLibro(String isbn) throws LibroNoExistenteException {
        Libro libro = catalogo.buscarLibroPorISBN(isbn);
        if (libro.getEstado() == EstadoLibro.DISPONIBLE) {
            Prestamo prestamo = new Prestamo(libro);
            prestamosActivos.add(prestamo);
            libro.setEstado(EstadoLibro.PRESTADO);
            return prestamo;
        }
        throw new LibroNoDisponibleException("No se encuentra disponible el libro de ISBN: " + isbn);
    }

    public Prestamo devolverLibro(String isbn) throws LibroNoExistenteException, PrestamoNoExistenteException {
        Prestamo prestamo = buscarPrestamo(isbn);
        prestamosActivos.remove(prestamo);
        prestamo.getLibro().setEstado(EstadoLibro.DISPONIBLE);
        return prestamo;
    }
}
