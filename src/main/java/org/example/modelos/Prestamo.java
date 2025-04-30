package org.example.modelos;

import java.time.LocalDate;

public class Prestamo {
    private LocalDate fechaPrestamo = LocalDate.now();
    private Libro libro;

    public Prestamo() {
    }

    public Prestamo(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
