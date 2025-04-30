package org.example.modelos;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private ArrayList<Prestamo> historialPrestamos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Prestamo> getHistorialPrestamos() {
        return historialPrestamos;
    }

    public void registrarPrestamo(Prestamo prestamo) {
        this.historialPrestamos.add(prestamo);
    }
}
