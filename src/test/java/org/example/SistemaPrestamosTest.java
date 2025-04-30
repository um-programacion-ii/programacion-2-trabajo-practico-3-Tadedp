package org.example;

import org.example.enums.EstadoLibro;
import org.example.gestores.SistemaPrestamos;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SistemaPrestamosTest {
    Libro libro1;
    Libro libro2;

    @Mock
    Catalogo catalogo;

    @InjectMocks
    SistemaPrestamos sistemaPrestamos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.libro1 = new Libro("09788420471839", "Cien Años De Soledad", "Gabriel García Márquez");
        this.libro2 = new Libro("9788494220579", "Drácula", "Bram Stoker");
    }

    @Test
    void testPrestarLibroExito() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);
        Prestamo prestamo = sistemaPrestamos.prestarLibro("09788420471839");

        verify(catalogo).buscarLibroPorISBN("09788420471839");
        Assertions.assertEquals(libro1, prestamo.getLibro());
        Assertions.assertEquals(EstadoLibro.PRESTADO, libro1.getEstado());
    }

    @Test
    void testPrestarLibroFallo() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(null);
        Prestamo prestamo1 = sistemaPrestamos.prestarLibro("09788420471839");

        Assertions.assertNull(prestamo1);

        libro1.setEstado(EstadoLibro.PRESTADO);
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);
        Prestamo prestamo2 = sistemaPrestamos.prestarLibro("09788420471839");

        verify(catalogo, times(2)).buscarLibroPorISBN("09788420471839");
        Assertions.assertNull(prestamo2);
        Assertions.assertEquals(EstadoLibro.PRESTADO, libro1.getEstado());
    }

    @Test
    void testDevolverLibroExito() {
        when(catalogo.buscarLibroPorISBN("9788494220579")).thenReturn(libro2);
        Prestamo prestamoCreado = sistemaPrestamos.prestarLibro("9788494220579");

        Prestamo prestamoFinalizado = sistemaPrestamos.devolverLibro("9788494220579");

        verify(catalogo, times(2)).buscarLibroPorISBN("9788494220579");
        Assertions.assertEquals(prestamoCreado.getLibro(), prestamoFinalizado.getLibro());
        Assertions.assertEquals(libro2, prestamoFinalizado.getLibro());
        Assertions.assertTrue(sistemaPrestamos.getPrestamosActivos().isEmpty());
        Assertions.assertEquals(EstadoLibro.DISPONIBLE, libro2.getEstado());
    }

    @Test
    void testDevolverLibroFallo() {
        when(catalogo.buscarLibroPorISBN("9788494220579")).thenReturn(null);
        Prestamo prestamo1 = sistemaPrestamos.devolverLibro("9788494220579");

        Assertions.assertNull(prestamo1);

        when(catalogo.buscarLibroPorISBN("9788494220579")).thenReturn(libro2);
        Prestamo prestamo2 = sistemaPrestamos.devolverLibro("9788494220579");

        verify(catalogo, times(2)).buscarLibroPorISBN("9788494220579");
        Assertions.assertNull(prestamo2);
        Assertions.assertTrue(sistemaPrestamos.getPrestamosActivos().isEmpty());
        Assertions.assertEquals(EstadoLibro.DISPONIBLE, libro2.getEstado());
    }

    @Test
    void testBuscarPrestamoExito() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);
        Prestamo prestamoCreado = sistemaPrestamos.prestarLibro("09788420471839");
        Prestamo prestamoBuscado = sistemaPrestamos.buscarPrestamo("09788420471839");

        verify(catalogo, times(2)).buscarLibroPorISBN("09788420471839");
        Assertions.assertEquals(prestamoCreado.getLibro(), prestamoBuscado.getLibro());
        Assertions.assertEquals(libro1, prestamoBuscado.getLibro());
    }

    @Test
    void testBuscarPrestamoFallo() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(null);
        Prestamo prestamo1 = sistemaPrestamos.buscarPrestamo("09788420471839");

        Assertions.assertNull(prestamo1);

        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);
        Prestamo prestamo2 = sistemaPrestamos.buscarPrestamo("09788420471839");

        verify(catalogo, times(2)).buscarLibroPorISBN("09788420471839");
        Assertions.assertNull(prestamo2);
    }
}
