package gestoralumnos;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorAlumnos {

    private ArrayList<Alumno> alumnos;

    public GestorAlumnos() {
        alumnos = ArchivoAlumnos.cargarAlumnos();
    }

    // Método para agregar un alumno
    public void agregarAlumno(Alumno alumno)throws DniDuplicadoException {
        // Verificar si el alumno ya existe por su DNI
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(alumno.getDni())) {
                try {
                    throw new DniDuplicadoException("Ya existe un alumno con el DNI: " + alumno.getDni());
                } catch (DniDuplicadoException e) {
                    System.err.println(e.getMessage()); // Mostrar el mensaje de error
                    return;
                }
            }
        }

        // Si el DNI no está duplicado, agregar el alumno
        alumnos.add(alumno);
        ArchivoAlumnos.guardarAlumnos(alumnos);
        System.out.println("Alumno añadido.");
    }

    public void mostrarAlumnos() {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }

        for (Alumno a : alumnos) {
            System.out.println(a);
        }
    }

    public void verAlumnoPorDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(dni)) {
                System.out.println(a);
                return;
            }
        }
        System.out.println("Alumno no encontrado.");
    }

    public void modificarAlumno(String dni, Scanner sc) {
        Alumno alumno = buscarAlumnoPorDni(dni);
        if (alumno != null) {
            System.out.println("Alumno encontrado: " + alumno);

            System.out.println("Modifica los siguientes datos (deja en blanco para no modificar):");

            System.out.print("Nuevo nombre: ");
            String nuevoNombre = sc.nextLine();
            if (!nuevoNombre.isEmpty()) {
                alumno.setNombre(nuevoNombre);
            }

            System.out.print("Nuevo apellido: ");
            String nuevoApellido = sc.nextLine();
            if (!nuevoApellido.isEmpty()) {
                alumno.setApellido(nuevoApellido);
            }

            System.out.print("Nueva nota media (deja en blanco para no modificar): ");
            String nuevaNota = sc.nextLine();
            if (!nuevaNota.isEmpty()) {
                alumno.setNotaMedia(Double.parseDouble(nuevaNota));
            }

            System.out.print("Nuevo horario (deja en blanco para no modificar): ");
            String nuevoHorario = sc.nextLine();
            if (!nuevoHorario.isEmpty()) {
                alumno.setHorario(nuevoHorario);
            }

            System.out.print("Nueva agenda (deja en blanco para no modificar): ");
            String nuevaAgenda = sc.nextLine();
            if (!nuevaAgenda.isEmpty()) {
                alumno.setAgenda(nuevaAgenda);
            }

            ArchivoAlumnos.guardarAlumnos(alumnos);
            System.out.println("Alumno modificado: " + alumno);
        } else {
            System.out.println("Alumno no encontrado.");
        }
    }

    public void eliminarAlumno(String dni) {
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno a = alumnos.get(i);
            if (a.getDni().equalsIgnoreCase(dni)) {
                alumnos.remove(i);
                ArchivoAlumnos.guardarAlumnos(alumnos);
                System.out.println("Alumno eliminado.");
                return;
            }
        }
        System.out.println("Alumno no encontrado.");
    }

    public Alumno buscarAlumnoPorDni(String dni) {
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(dni)) {
                return a;
            }
        }
        return null;
    }
}
