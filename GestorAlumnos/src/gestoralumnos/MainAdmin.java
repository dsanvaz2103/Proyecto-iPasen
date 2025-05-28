import java.util.Scanner;

public class MainAdmin {
    public static void main(String[] args) {
        GestorAlumnos gestor = new GestorAlumnos() ;
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- GESTOR DE ALUMNOS ---");
            System.out.println("1. Ver alumnos");
            System.out.println("2. Ver alumno por DNI");
            System.out.println("3. Añadir alumno");
            System.out.println("4. Modificar alumno");
            System.out.println("5. Eliminar alumno");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    gestor.mostrarAlumnos();
                    break;
                case 2:
                    System.out.print("DNI del alumno: ");
                    String dniConsulta = sc.nextLine();
                    gestor.verAlumnoPorDni(dniConsulta);
                    break;
                case 3:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Apellido: ");
                    String apellido = sc.nextLine();

                    System.out.print("DNI: ");
                    String dniNuevo = sc.nextLine();

                    System.out.print("Edad: ");
                    int edad = sc.nextInt();
                    sc.nextLine();  // Consumir el salto de línea

                    System.out.print("Curso: ");
                    String curso = sc.nextLine();

                    System.out.print("Nota media: ");
                    double notaMedia = sc.nextDouble();
                    sc.nextLine();  // Consumir el salto de línea

                    System.out.print("Horario: ");
                    String horario = sc.nextLine();

                    System.out.print("Agenda: ");
                    String agenda = sc.nextLine();

                    System.out.print("Faltas de asistencia: ");
                    String faltas = sc.nextLine();

                    Alumno nuevoAlumno = new Alumno(nombre, apellido, dniNuevo, edad, curso, notaMedia);
                    nuevoAlumno.setNotaMedia(notaMedia);
                    nuevoAlumno.setFaltas(faltas);
                    nuevoAlumno.setHorario(horario);
                    nuevoAlumno.setAgenda(agenda);

                    // Intentar agregar el alumno y manejar la excepción si el DNI está duplicado
                    try {
                        gestor.agregarAlumno(nuevoAlumno);
                    } catch (DniDuplicadoException e) {
                        System.err.println(e.getMessage()); // Mostrar el mensaje de la excepción
                    }
                    break;
                case 4:
                    System.out.print("DNI del alumno a modificar: ");
                    String dniModificar = sc.nextLine();
                    gestor.modificarAlumno(dniModificar, sc);
                    break;
                case 5:
                    System.out.print("DNI del alumno a eliminar: ");
                    String dniEliminar = sc.nextLine();
                    gestor.eliminarAlumno(dniEliminar);
                    break;
                case 6:
                    System.out.println("Saliendo del gestor...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        } while (opcion != 6);
    }
}