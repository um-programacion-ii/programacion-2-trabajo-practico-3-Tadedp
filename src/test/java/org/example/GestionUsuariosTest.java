package org.example;

import org.example.excepciones.UsuarioNoExistenteException;
import org.example.gestores.GestionUsuarios;
import org.example.gestores.SistemaPrestamos;
import org.example.modelos.Catalogo;
import org.example.modelos.Libro;
import org.example.modelos.Prestamo;
import org.example.modelos.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GestionUsuariosTest {
    Libro libro1;
    Libro libro2;

    @Mock
    Catalogo catalogo;

    @Mock
    SistemaPrestamos sistemaPrestamos;

    @InjectMocks
    GestionUsuarios gestionUsuarios;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.libro1 = new Libro("09788420471839", "Cien Años De Soledad", "Gabriel García Márquez");
        this.libro2 = new Libro("9788494220579", "Drácula", "Bram Stoker");
    }

    @Test
    void testRegistrarPrestamoExito() {
        when(catalogo.buscarLibroPorISBN("09788420471839")).thenReturn(libro1);

        Prestamo prestamo = new Prestamo(libro1);
        when(sistemaPrestamos.prestarLibro("09788420471839")).thenReturn(prestamo);
        when(sistemaPrestamos.buscarPrestamo("09788420471839")).thenReturn(prestamo);

        Usuario usuarioCreado = gestionUsuarios.agregarUsuario("Tadeo");
        Prestamo prestamoCreado = sistemaPrestamos.prestarLibro("09788420471839");
        gestionUsuarios.registrarPrestamo("Tadeo", prestamoCreado);

        Prestamo prestamoBuscado = sistemaPrestamos.buscarPrestamo("09788420471839");

        Assertions.assertEquals(prestamoCreado, usuarioCreado.getHistorialPrestamos().getFirst());
        Assertions.assertEquals(prestamoCreado, prestamoBuscado);
    }

    @Test
    void testBuscarUsuarioExito() {
        Usuario usuarioCreado = gestionUsuarios.agregarUsuario("Tadeo");
        Usuario usuarioBuscado = gestionUsuarios.buscarUsuario("Tadeo");

        Assertions.assertEquals(usuarioBuscado.getNombre(), usuarioCreado.getNombre());
    }

    @Test
    void testEliminarUsuarioExito() {
        Usuario usuarioCreado = gestionUsuarios.agregarUsuario("Tadeo");
        Usuario usuarioEliminado = gestionUsuarios.eliminarUsuario("Tadeo");

        Assertions.assertEquals(usuarioEliminado.getNombre(), usuarioCreado.getNombre());
        Assertions.assertTrue(gestionUsuarios.getUsuariosActivos().isEmpty());
    }

    @Test
    void testUsuarioNoExistenteException() {
        UsuarioNoExistenteException e1 = Assertions.assertThrows(UsuarioNoExistenteException.class, () -> {
            gestionUsuarios.buscarUsuario("Tadeo");
        });

        Assertions.assertEquals("No existe ningún Usuario con nombre: Tadeo", e1.getMessage());

        Prestamo prestamo = new Prestamo(libro1);

        UsuarioNoExistenteException e2 = Assertions.assertThrows(UsuarioNoExistenteException.class, () -> {
            gestionUsuarios.registrarPrestamo("Tadeo", prestamo);
        });

        Assertions.assertEquals("No existe ningún Usuario con nombre: Tadeo", e2.getMessage());

        UsuarioNoExistenteException e3 = Assertions.assertThrows(UsuarioNoExistenteException.class, () -> {
            gestionUsuarios.eliminarUsuario("Tadeo");
        });

        Assertions.assertEquals("No existe ningún Usuario con nombre: Tadeo", e3.getMessage());
    }
}
