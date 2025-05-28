package gestoralumnos;



import java.util.InputMismatchException;
import java.util.Scanner;

public class AlumnoApp {

    public static void iniciarSesion(Alumno alumno) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\nBienvenido, " + alumno.getNombre() + " " + alumno.getApellido());
            System.out.println("1. Mis datos");
            System.out.println("2. Mis notas");
            System.out.println("3. Horario");
            System.out.println("4. Agenda");
            System.out.println("5. Faltas de asistencia");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.nextLine(); // limpiar buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Tus datos ---");
                    System.out.println(alumno);
                    break;
                case 2:
                    System.out.println("\n--- Tus notas ---");
                    System.out.printf("Nota media: %.2f\n", alumno.getNotaMedia()); // FORMATO CON 2 DECIMALES
                    break;
                case 3:
                    System.out.println("\n--- Tu horario ---");
                    System.out.println(alumno.getHorario());
                    break;
                case 4:
                    System.out.println("\n--- Tu agenda ---");
                    System.out.println(alumno.getAgenda());
                    break;
                case 5:
                    System.out.println("\n--- Tus faltas de asistencia ---");
                    System.out.println(alumno.getFaltas());
                    break;
                case 6:
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }
}