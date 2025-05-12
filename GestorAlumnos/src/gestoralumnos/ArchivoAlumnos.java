package gestoralumnos;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchivoAlumnos {
    private static final String NOMBRE_ARCHIVO = "alumnos.txt";

    public static void guardarAlumnos(ArrayList<Alumno> alumnos) {
        try {
            PrintWriter writer = new PrintWriter(NOMBRE_ARCHIVO);
            for (Alumno a : alumnos) {
                writer.println(a.toArchivo()); 
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar alumnos: " + e.getMessage());
        }
    }

    public static ArrayList<Alumno> cargarAlumnos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) return alumnos;

         try {
        Scanner scanner = new Scanner(archivo);
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            String[] datos = linea.split(";");
            
            if (datos.length == 8) {
                String nombre = datos[0];
                String apellido = datos[1];
                String dni = datos[2];
                int edad = Integer.parseInt(datos[3]);
                String curso = datos[4];
                double notaMedia = Double.parseDouble(datos[5]);
                String horario = datos[6];
                String agenda = datos[7];

                Alumno alumno = new Alumno(nombre, apellido, dni, edad, curso, notaMedia);
                alumno.setNotaMedia(notaMedia);
                alumno.setHorario(horario);
                alumno.setAgenda(agenda);

                // Evitar duplicados
                if (!alumnos.contains(alumno)) {
                    alumnos.add(alumno);
                } else {
                    System.out.println("Alumno duplicado no añadido: " + dni);
                }
            }
        }
        scanner.close();
    } catch (IOException e) {
        System.out.println("Error al cargar alumnos: " + e.getMessage());
    }

    return alumnos;
}
}