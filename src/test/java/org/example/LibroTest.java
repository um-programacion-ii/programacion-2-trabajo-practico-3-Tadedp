package org.example;

import org.example.enums.EstadoLibro;
import org.example.modelos.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class LibroTest {
    Libro libro;

    @BeforeEach
    public void setUp() {
        this.libro = new Libro("09788420471839", "Cien Años De Soledad", "Gabriel García Márquez");
    }

    @Test
    void testCrearLibro() {
        Assertions.assertEquals("09788420471839", this.libro.getISBN());
        Assertions.assertEquals("Cien Años De Soledad", this.libro.getTitulo());
        Assertions.assertEquals("Gabriel García Márquez", this.libro.getAutor());
        Assertions.assertEquals(EstadoLibro.DISPONIBLE, this.libro.getEstado());
    }

    @Test
    void testSetISBN() {
        this.libro.setISBN("12345678901234");
        Assertions.assertEquals("12345678901234", this.libro.getISBN());
    }

    @Test
    void testSetTitulo() {
        this.libro.setTitulo("Drácula");
        Assertions.assertEquals("Drácula", this.libro.getTitulo());
    }

    @Test
    void testSetAutor() {
        this.libro.setAutor("Bram Stoker");
        Assertions.assertEquals("Bram Stoker", this.libro.getAutor());
    }

    @Test
    void testCambioEstadoLibro() {
        Assertions.assertEquals(EstadoLibro.DISPONIBLE, this.libro.getEstado());

        this.libro.setEstado(EstadoLibro.PRESTADO);
        Assertions.assertEquals(EstadoLibro.PRESTADO, this.libro.getEstado());

        this.libro.setEstado(EstadoLibro.DISPONIBLE);
        Assertions.assertEquals(EstadoLibro.DISPONIBLE, this.libro.getEstado());
    }
}
