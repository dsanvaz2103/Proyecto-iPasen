package gestoralumnos;

public class Alumno {

    private String nombre, apellido, dni, curso, horario, agenda, faltas;
    private int edad;
    private double notaMedia;

    public Alumno(String nombre, String apellido, String dni, int edad, String curso, double notaMedia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
        this.curso = curso;
        this.notaMedia = 0.0;
        this.horario = "Lunes a Viernes de 8:00 a 14:30";
        this.agenda = "Sin eventos programados.";
        this.faltas = "No tienes faltas de asistencia";
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - DNI: " + dni + " - Edad: " + edad + " - Curso: " + curso;
    }

    public String toArchivo() {
        return nombre + ";" + apellido + ";" + dni + ";" + edad + ";" + curso + ";" + notaMedia + ";" + horario + ";" + agenda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getFaltas() {
        return faltas;
    }

    public void setFaltas(String faltas) {
        this.faltas = faltas;
    }

}
