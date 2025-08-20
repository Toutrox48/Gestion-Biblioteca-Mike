package com.biblioteca.main;

import com.biblioteca.servicios.Biblioteca;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Biblioteca miBiblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        miBiblioteca.registrarLibro(scanner);
                        break;
                    case 2:
                        miBiblioteca.registrarUsuario(scanner);
                        break;
                    case 3:
                        miBiblioteca.prestarLibro(scanner);
                        break;
                    case 4:
                        miBiblioteca.devolverLibro(scanner);
                        break;
                    case 5:
                        miBiblioteca.mostrarLibrosDisponibles();
                        break;
                    case 6:
                        miBiblioteca.mostrarUsuarios();
                        break;
                    case 7:
                        miBiblioteca.mostrarHistorialPrestamos();
                        break;
                    case 8:
                        System.out.println("👋 Saliendo del sistema. ¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("❌ Opción no válida. Por favor, intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
                opcion = 0;
            }
        } while (opcion != 8);
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== Sistema de Gestión de Biblioteca =====");
        System.out.println("1. Registrar nuevo libro");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Mostrar libros disponibles");
        System.out.println("6. Mostrar usuarios registrados");
        System.out.println("7. Mostrar historial de préstamos");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }
}