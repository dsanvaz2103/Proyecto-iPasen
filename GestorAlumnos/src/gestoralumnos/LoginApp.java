package GestorAlumnos;

import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorAlumnos gestor = new GestorAlumnos();

        System.out.println("--- BIENVENIDO AL SISTEMA ---");
        System.out.print("¿Eres 'admin' o 'alumno'?: ");
        String tipo = sc.nextLine().trim().toLowerCase(); // NORMALIZAR ENTRADA

        switch (tipo) {
            case "admin":
                int intentos = 0;
                String contrasena;
                do {
                    System.out.print("Introduce la contraseña de administrador: ");
                    contrasena = sc.nextLine();
                    intentos++;
                    if (intentos > 3) {
                        System.out.println("Demasiados intentos fallidos. Acceso denegado.");
                        return;
                    }
                } while (!contrasena.equals("1234"));

                System.out.println("Acceso concedido como ADMIN.");
                MainAdmin.main(null);
                break;

            case "alumno":
                System.out.print("Introduce tu DNI: ");
                String dni = sc.nextLine().trim();

                Alumno alumno = gestor.buscarAlumnoPorDni(dni);

                if (alumno != null) {
                    AlumnoApp.iniciarSesion(alumno);
                } else {
                    System.out.println("DNI no encontrado. Acceso denegado.");
                }
                break;

            default:
                System.out.println("Opción no reconocida.");
        }
    }
}
