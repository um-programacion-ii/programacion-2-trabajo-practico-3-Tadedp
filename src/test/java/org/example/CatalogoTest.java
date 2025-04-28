package org.example;

import org.example.enums.EstadoLibro;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

public class CatalogoTest {
    Catalogo catalogo;
    Libro libro1;
    Libro libro2;

    @BeforeEach
    public void setUp() {
        this.catalogo = new Catalogo();
        this.libro1 = new Libro("09788420471839", "Cien Años De Soledad", "Gabriel García Márquez");
        this.libro2 = new Libro("9788494220579", "Drácula", "Bram Stoker");
    }

    @Test
    void testObtenerLibrosDisponibles() {
        this.catalogo.agregarLibro(libro1);
        this.catalogo.agregarLibro(libro2);

        List<Libro> librosDisponibles = new ArrayList<>();
        librosDisponibles.add(libro1);
        librosDisponibles.add(libro2);

        Assertions.assertEquals(2, this.catalogo.getLibros().size());
        Assertions.assertEquals(librosDisponibles,this.catalogo.getLibros());

        this.libro2.setEstado(EstadoLibro.PRESTADO);
        Assertions.assertEquals(1, this.catalogo.getLibros().size());
        Assertions.assertNotEquals(librosDisponibles, this.catalogo.getLibros());
    }

    @Test
    void testAgregarLibro() {
        this.catalogo.agregarLibro(libro1);
        Assertions.assertTrue(this.catalogo.getLibros().contains(libro1));
        Assertions.assertEquals(libro1, this.catalogo.getLibros().getFirst());

        this.catalogo.agregarLibro(libro2);
        Assertions.assertTrue(this.catalogo.getLibros().contains(libro2));
        Assertions.assertEquals(libro2, this.catalogo.getLibros().get(1));
    }

    @Test
    void testEliminarLibro() {
        this.catalogo.agregarLibro(libro1);
        this.catalogo.agregarLibro(libro2);

        this.catalogo.eliminarLibro(libro1);
        Assertions.assertFalse(this.catalogo.getLibros().contains(libro1));
        Assertions.assertTrue(this.catalogo.getLibros().contains(libro2));

        this.catalogo.eliminarLibro(libro2);
        Assertions.assertFalse(this.catalogo.getLibros().contains(libro2));
    }

    @Test
    void testBuscarLibroExistentePorISBN() {
        this.catalogo.agregarLibro(libro1);
        Assertions.assertEquals(libro1, this.catalogo.buscarLibroPorISBN(libro1.getISBN()));

        this.catalogo.agregarLibro(libro2);
        Assertions.assertEquals(libro2, this.catalogo.buscarLibroPorISBN(libro2.getISBN()));

        Libro libro = this.catalogo.buscarLibroPorISBN(libro1.getISBN());
        Assertions.assertEquals("Cien Años De Soledad", libro.getTitulo());
    }

    @Test
    void testBuscarLibroInexistentePorISBN() {
        Assertions.assertNull(this.catalogo.buscarLibroPorISBN(libro1.getISBN()));
        Assertions.assertNull(this.catalogo.buscarLibroPorISBN(libro2.getISBN()));

        this.catalogo.agregarLibro(libro1);
        this.catalogo.agregarLibro(libro2);

        Assertions.assertNull(this.catalogo.buscarLibroPorISBN("012345678901234"));
    }
}
