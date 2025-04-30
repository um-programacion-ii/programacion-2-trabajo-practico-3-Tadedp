package org.example;

import org.example.enums.EstadoLibro;
import org.example.excepciones.LibroNoDisponibleException;
import org.example.excepciones.LibroNoExistenteException;
import org.example.excepciones.PrestamoNoExistenteException;
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
    void testPrestarLibroNoExistente() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenThrow(LibroNoExistenteException.class);
        LibroNoExistenteException e1 = Assertions.assertThrows(LibroNoExistenteException.class, () -> {
            sistemaPrestamos.prestarLibro("09788420471839");
        });
    }

    @Test
    void testPrestarLibroNoDisponible() {
        libro1.setEstado(EstadoLibro.PRESTADO);
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);

        LibroNoDisponibleException e1 = Assertions.assertThrows(LibroNoDisponibleException.class, () -> {
            sistemaPrestamos.prestarLibro("09788420471839");
        });
        Assertions.assertEquals("No se encuentra disponible el libro de ISBN: 09788420471839", e1.getMessage());
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
    void testDevolverLibroNoExistente() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenThrow(LibroNoExistenteException.class);
        LibroNoExistenteException e1 = Assertions.assertThrows(LibroNoExistenteException.class, () -> {
            sistemaPrestamos.devolverLibro("09788420471839");
        });
    }

    @Test
    void testDevolverLibroNoPrestado() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);

        PrestamoNoExistenteException e1 = Assertions.assertThrows(PrestamoNoExistenteException.class, () -> {
            sistemaPrestamos.devolverLibro("09788420471839");
        });
        Assertions.assertEquals("No existe ningún préstamo para el libro de ISBN: 09788420471839", e1.getMessage());
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
    void testBuscarPrestamoLibroNoExistente() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenThrow(LibroNoExistenteException.class);
        LibroNoExistenteException e1 = Assertions.assertThrows(LibroNoExistenteException.class, () -> {
            sistemaPrestamos.buscarPrestamo("09788420471839");
        });
    }

    @Test
    void testBuscarPrestamoNoExistente() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);

        PrestamoNoExistenteException e1 = Assertions.assertThrows(PrestamoNoExistenteException.class, () -> {
            sistemaPrestamos.buscarPrestamo("09788420471839");
        });
        Assertions.assertEquals("No existe ningún préstamo para el libro de ISBN: 09788420471839", e1.getMessage());
    }
}
