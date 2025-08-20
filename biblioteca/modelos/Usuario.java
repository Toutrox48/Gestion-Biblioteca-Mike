package com.biblioteca.modelos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String idUsuario;
    private List<Libro> librosPrestados;

    public Usuario(String nombre, String idUsuario) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.librosPrestados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public List<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    public void agregarPrestamo(Libro libro) {
        if (librosPrestados.size() < 3) {
            librosPrestados.add(libro);
        } else {
            System.out.println("🚫 Límite de 3 libros prestados alcanzado. No puedes pedir más.");
        }
    }

    public void devolverLibro(Libro libro) {
        librosPrestados.remove(libro);
    }

    public void mostrarDatos() {
        System.out.println("--- Datos del Usuario ---");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID de Usuario: " + idUsuario);
        System.out.println("Libros prestados: " + librosPrestados.size());
        if (!librosPrestados.isEmpty()) {
            System.out.println("Libros actuales:");
            for (Libro libro : librosPrestados) {
                System.out.println("  - " + libro.getTitulo() + " (Código: " + libro.getCodigo() + ")");
            }
        }
        System.out.println("-------------------------");
    }
}