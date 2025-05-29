package gestoralumnos;

import java.io.*;
import java.util.ArrayList;

public class SerializadorAlumnos {

    private static final String NOMBRE_ARCHIVO_BIN = "alumnos.dat";

    // METODO PARA GUARDAR LISTA DE ALUMNOS CON SERIALIZACIÓN BINARIA
    public static void guardarAlumnos(ArrayList<Alumno> alumnos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(NOMBRE_ARCHIVO_BIN)))) {
            oos.writeObject(alumnos);
        } catch (IOException e) {
            System.err.println("Error al guardar alumnos (serialización): " + e.getMessage());
        }
    }

    // METODO PARA CARGAR LISTA DE ALUMNOS CON SERIALIZACIÓN BINARIA
    @SuppressWarnings("unchecked")
    public static ArrayList<Alumno> cargarAlumnos() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO_BIN);
        if (!archivo.exists()) {
            return alumnos;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo)))) {
            alumnos = (ArrayList<Alumno>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar alumnos (serialización): " + e.getMessage());
        }
        return alumnos;
    }
}