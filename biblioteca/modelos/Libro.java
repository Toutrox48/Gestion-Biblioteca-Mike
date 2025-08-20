package com.biblioteca.modelos;

public class Libro {
    private String titulo;
    private String autor;
    private String codigo;
    private boolean disponible;

    public Libro(String titulo, String autor, String codigo) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigo = codigo;
        this.disponible = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void marcarPrestado() {
        this.disponible = false;
    }

    public void marcarDisponible() {
        this.disponible = true;
    }

    public void mostrarDatos() {
        System.out.println("--- Datos del Libro ---");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Código: " + codigo);
        System.out.println("Estado: " + (disponible ? "Disponible" : "Prestado"));
        System.out.println("-----------------------");
    }
}