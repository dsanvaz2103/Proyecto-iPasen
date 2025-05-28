import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchivoAlumnos {
    private static final String NOMBRE_ARCHIVO = "alumnos.txt";

    // METODO PARA GUARDAR ALUMNOS EN UN FICHERO DE TEXTO
    public static void guardarAlumnos(ArrayList<Alumno> alumnos) {
        // try-with-resources asegura cerrar el recurso automáticamente
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO)))) { // USO DE BufferedWriter PARA MEJOR RENDIMIENTO
            for (Alumno a : alumnos) {
                writer.println(a.toArchivo());
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo crear o abrir el archivo para escritura: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // MÉTODO PARA CARGAR ALUMNOS DESDE UN FICHERO DE TEXTO
    public static ArrayList<Alumno> cargarAlumnos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            // No hay archivo para cargar, devuelve lista vacía
            return alumnos;
        }

        // try-with-resources para asegurar cierre automático del Scanner
        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(";");

                if (datos.length == 8) { // VALIDACIÓN DEL FORMATO DE LÍNEA
                    try {
                        String nombre = datos[0];
                        String apellido = datos[1];
                        String dni = datos[2];
                        int edad = Integer.parseInt(datos[3]);
                        String curso = datos[4];
                        double notaMedia = Double.parseDouble(datos[5]);
                        String horario = datos[6];
                        String agenda = datos[7];

                        Alumno alumno = new Alumno(nombre, apellido, dni, edad, curso, notaMedia);
                        alumno.setHorario(horario);
                        alumno.setAgenda(agenda);

                        if (!alumnos.contains(alumno)) { // EVITAR ALUMNOS DUPLICADOS
                            alumnos.add(alumno);
                        } else {
                            System.out.println("Alumno duplicado no añadido: " + dni);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error en formato numérico en línea: " + linea);
                    }
                } else {
                    System.err.println("Línea con formato incorrecto ignorada: " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado al cargar alumnos: " + e.getMessage());
        }

        return alumnos;
    }
}