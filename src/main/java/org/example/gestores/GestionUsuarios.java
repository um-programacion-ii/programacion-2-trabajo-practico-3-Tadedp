package org.example.gestores;

import org.example.excepciones.UsuarioNoExistenteException;
import org.example.modelos.Prestamo;
import org.example.modelos.Usuario;

import java.util.ArrayList;

public class GestionUsuarios {
    private ArrayList<Usuario> usuariosActivos = new ArrayList<>();

    public GestionUsuarios() {
    }

    public ArrayList<Usuario> getUsuariosActivos() {
        return usuariosActivos;
    }

    public Usuario buscarUsuario(String nombre) {
        for ( Usuario usuario : usuariosActivos ) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        throw new UsuarioNoExistenteException("No existe ningún Usuario con nombre: " + nombre);
    }

    public Usuario agregarUsuario(String nombre) {
        Usuario usuario = new Usuario(nombre);
        this.usuariosActivos.add(usuario);
        return usuario;
    }

    public Usuario eliminarUsuario(String nombre) throws UsuarioNoExistenteException {
        Usuario usuario = buscarUsuario(nombre);
        this.usuariosActivos.remove(usuario);
        return usuario;
    }

    public Usuario registrarPrestamo(String nombre, Prestamo prestamo) throws UsuarioNoExistenteException {
        Usuario usuario = buscarUsuario(nombre);
        if (usuario != null) {
            usuario.registrarPrestamo(prestamo);
            return usuario;
        }
        return null;
    }
}
