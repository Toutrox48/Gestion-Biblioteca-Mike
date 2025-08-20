package biblioteca.servicios;

import biblioteca.modelos.Libro;
import biblioteca.modelos.Usuario;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Biblioteca {
    private List<Libro> listaDeLibros;
    private List<Usuario> listaDeUsuarios;
    private Map<String, LocalDate> registroDePrestamos; // Código de libro -> Fecha de préstamo

    public Biblioteca() {
        this.listaDeLibros = new ArrayList<>();
        this.listaDeUsuarios = new ArrayList<>();
        this.registroDePrestamos = new HashMap<>();
    }

    public void registrarLibro(Scanner scanner) {
        System.out.println("\n--- Registrar Nuevo Libro ---");
        System.out.print("Título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("Código del libro: ");
        String codigo = scanner.nextLine();

        if (buscarLibroPorCodigo(codigo) == null) {
            Libro nuevoLibro = new Libro(titulo, autor, codigo);
            listaDeLibros.add(nuevoLibro);
            System.out.println("✅ Libro registrado con éxito.");
        } else {
            System.out.println("❌ Error: Ya existe un libro con ese código.");
        }
    }

    public void registrarUsuario(Scanner scanner) {
        System.out.println("\n--- Registrar Nuevo Usuario ---");
        System.out.print("Nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("ID de usuario: ");
        String idUsuario = scanner.nextLine();

        if (buscarUsuarioPorId(idUsuario) == null) {
            Usuario nuevoUsuario = new Usuario(nombre, idUsuario);
            listaDeUsuarios.add(nuevoUsuario);
            System.out.println("✅ Usuario registrado con éxito.");
        } else {
            System.out.println("❌ Error: Ya existe un usuario con ese ID.");
        }
    }

    public void prestarLibro(Scanner scanner) {
        System.out.println("\n--- Realizar Préstamo ---");
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("❌ Error: Usuario no encontrado.");
            return;
        }

        if (usuario.getLibrosPrestados().size() >= 3) {
            System.out.println("🚫 El usuario ya tiene el límite de 3 libros prestados.");
            return;
        }

        System.out.print("Código del libro a prestar: ");
        String codigoLibro = scanner.nextLine();
        Libro libro = buscarLibroPorCodigo(codigoLibro);

        if (libro == null) {
            System.out.println("❌ Error: Libro no encontrado.");
            return;
        }

        if (!libro.isDisponible()) {
            System.out.println("❌ Error: El libro no está disponible.");
            return;
        }

        libro.marcarPrestado();
        usuario.agregarPrestamo(libro);
        registroDePrestamos.put(libro.getCodigo(), LocalDate.now());
        System.out.println("✅ Préstamo realizado con éxito.");
    }

    public void devolverLibro(Scanner scanner) {
        System.out.println("\n--- Devolver Libro ---");
        System.out.print("ID del usuario: ");
        String idUsuario = scanner.nextLine();
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (usuario == null) {
            System.out.println("❌ Error: Usuario no encontrado.");
            return;
        }

        System.out.print("Código del libro a devolver: ");
        String codigoLibro = scanner.nextLine();
        Libro libro = buscarLibroPorCodigo(codigoLibro);

        if (libro == null) {
            System.out.println("❌ Error: Libro no encontrado.");
            return;
        }

        if (usuario.getLibrosPrestados().contains(libro)) {
            libro.marcarDisponible();
            usuario.devolverLibro(libro);

            // Calcular multa
            LocalDate fechaPrestamo = registroDePrestamos.get(libro.getCodigo());
            LocalDate fechaLimite = fechaPrestamo.plusDays(7); // Por ejemplo, 7 días de préstamo
            LocalDate fechaDevolucion = LocalDate.now();

            long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);

            if (diasRetraso > 0) {
                double multa = diasRetraso * 500;
                System.out.println("⚠️ ¡Atención! El libro fue devuelto con " + diasRetraso + " día(s) de retraso.");
                System.out.println("💲 Multa a pagar: $" + multa);
            } else {
                System.out.println("✅ Libro devuelto a tiempo.");
            }

            registroDePrestamos.remove(libro.getCodigo());
        } else {
            System.out.println("❌ Error: El usuario no tiene este libro prestado.");
        }
    }

    public void mostrarLibrosDisponibles() {
        System.out.println("\n--- Libros Disponibles ---");
        boolean hayDisponibles = false;
        for (Libro libro : listaDeLibros) {
            if (libro.isDisponible()) {
                libro.mostrarDatos();
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay libros disponibles en este momento.");
        }
    }

    public void mostrarUsuarios() {
        System.out.println("\n--- Usuarios Registrados ---");
        if (listaDeUsuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario usuario : listaDeUsuarios) {
                usuario.mostrarDatos();
            }
        }
    }

    public void mostrarHistorialPrestamos() {
        System.out.println("\n--- Historial de Préstamos ---");
        if (registroDePrestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
        } else {
            for (Map.Entry<String, LocalDate> entry : registroDePrestamos.entrySet()) {
                String codigoLibro = entry.getKey();
                LocalDate fechaPrestamo = entry.getValue();
                Libro libro = buscarLibroPorCodigo(codigoLibro);

                if (libro != null) {
                    System.out.println("Título: " + libro.getTitulo() + " (Código: " + codigoLibro + ")");
                    System.out.println("Fecha de préstamo: " + fechaPrestamo);
                    System.out.println("-------------------------");
                }
            }
        }
    }

    private Libro buscarLibroPorCodigo(String codigo) {
        for (Libro libro : listaDeLibros) {
            if (libro.getCodigo().equalsIgnoreCase(codigo)) {
                return libro;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getIdUsuario().equalsIgnoreCase(id)) {
                return usuario;
            }
        }
        return null;
    }
}