
package gestoralumnos;

import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorAlumnos gestor = new GestorAlumnos();

        System.out.println("--- BIENVENIDO AL SISTEMA ---");
        System.out.print("¿Eres 'admin' o 'alumno'?: ");
        String tipo = sc.nextLine();

        switch (tipo) {
            case "admin":
                System.out.print("Introduce la contraseña de administrador: ");
                String contrasena = sc.nextLine();

                if (contrasena.equals("1234")) {
                    System.out.println("Acceso concedido como ADMIN.");
                    MainAdmin.main(null);
                } else {
                    System.out.println("Contraseña incorrecta. Acceso denegado.");
                }
                break;

            case "alumno":
                System.out.print("Introduce tu DNI: ");
                String dni = sc.nextLine();

                Alumno alumno = gestor.buscarAlumnoPorDni(dni);

                if (alumno != null) {

                    AlumnoApp.iniciarSesion(alumno); 
                } else {
                    System.out.println("DNI no encontrado. Acceso denegado.");
                }
                break;

            default:
                System.out.println("Opcion no reconocida.");
        }
    }
}
