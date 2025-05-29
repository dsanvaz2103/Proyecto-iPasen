package gestoralumnos;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorAlumnos {

    private ArrayList<Alumno> alumnos;

    public GestorAlumnos() {
        alumnos = ArchivoAlumnos.cargarAlumnos();
    }

    // Método para agregar un alumno
    public void agregarAlumno(Alumno alumno) throws DniDuplicadoException {
        for (Alumno a : alumnos) {
            if (a.getDni().equalsIgnoreCase(alumno.getDni())) {
                throw new DniDuplicadoException("Ya existe un alumno con el DNI: " + alumno.getDni());
            }
        }

        try {
            if (alumno.getDni() == null || alumno.getDni().isEmpty() ||
                alumno.getNombre() == null || alumno.getNombre().isEmpty() ||
                alumno.getApellido() == null || alumno.getApellido().isEmpty() ||
                alumno.getNotaMedia() < 0) {
                throw new DatoInvalidoException("Datos del alumno inválidos. Verifica nombre, apellido, DNI o nota.");
            }

            // Añadir a lista y archivo
            alumnos.add(alumno);
            ArchivoAlumnos.guardarAlumnos(alumnos);
            System.out.println("Alumno añadido al archivo.");

            // Añadir a la base de datos
            Connection conn = ConexionMariaDB.conectar();
            String sql = "INSERT INTO alumnos (dni, nombre, apellido, edad, curso, nota_media, horario, agenda, faltas) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, alumno.getDni());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getApellido());
            stmt.setInt(4, alumno.getEdad());
            stmt.setString(5, alumno.getCurso());
            stmt.setDouble(6, alumno.getNotaMedia());
            stmt.setString(7, alumno.getHorario());
            stmt.setString(8, alumno.getAgenda());
            stmt.setString(9, alumno.getFaltas());

            stmt.executeUpdate();
            System.out.println("Alumno añadido a la base de datos.");

        } catch (DatoInvalidoException e) {
            System.err.println("ERROR: " + e.getMessage());
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DniDuplicadoException("El alumno con ese DNI ya existe en la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        // Guardar cambios en archivo
        ArchivoAlumnos.guardarAlumnos(alumnos);
        System.out.println("Alumno modificado localmente: " + alumno);

        // Actualizar también en la base de datos
        try {
            Connection conn = ConexionMariaDB.conectar();
            String sql = "UPDATE alumnos SET nombre = ?, apellido = ?, nota_media = ?, horario = ?, agenda = ? WHERE dni = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellido());
            stmt.setDouble(3, alumno.getNotaMedia());
            stmt.setString(4, alumno.getHorario());
            stmt.setString(5, alumno.getAgenda());
            stmt.setString(6, alumno.getDni());
            stmt.executeUpdate();
            System.out.println("Alumno actualizado en la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar el alumno en la base de datos:");
            e.printStackTrace();
        }

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

                // También eliminar de la base de datos
                try {
                    Connection conn = ConexionMariaDB.conectar();
                    String sql = "DELETE FROM alumnos WHERE dni = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, dni);
                    stmt.executeUpdate();
                    System.out.println("Alumno eliminado de la base de datos.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

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

    public ArrayList<Alumno> getAlumnos() {
    return alumnos;
}

}
