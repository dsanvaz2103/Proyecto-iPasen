import java.awt.*;
import java.awt.event.*;

public class AlumnoAWT extends Frame {

    private Alumno alumno;
    private TextArea textArea;

    public AlumnoAWT(Alumno alumno) {
        super("Panel del Alumno");
        this.alumno = alumno;

        setSize(400, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Área de información
        textArea = new TextArea();
        textArea.setEditable(false);
        add(textArea, BorderLayout.CENTER);

        // Panel con botones
        Panel panelBotones = new Panel(new GridLayout(3, 2, 10, 10));
        panelBotones.setBackground(new Color(235, 235, 250));
        panelBotones.setPreferredSize(new Dimension(400, 150));
        panelBotones.setFont(new Font("Arial", Font.PLAIN, 13));

        Button btnDatos = new Button("Mis datos");
        Button btnNotas = new Button("Mis notas");
        Button btnHorario = new Button("Horario");
        Button btnAgenda = new Button("Agenda");
        Button btnFaltas = new Button("Faltas de asistencia");
        Button btnSalir = new Button("Salir");

        panelBotones.add(btnDatos);
        panelBotones.add(btnNotas);
        panelBotones.add(btnHorario);
        panelBotones.add(btnAgenda);
        panelBotones.add(btnFaltas);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones de botones
        btnDatos.addActionListener(e -> mostrarDatos());
        btnNotas.addActionListener(e -> mostrarNotas());
        btnHorario.addActionListener(e -> mostrarHorario());
        btnAgenda.addActionListener(e -> mostrarAgenda());
        btnFaltas.addActionListener(e -> mostrarFaltas());
        btnSalir.addActionListener(e -> cerrarSesion());

        // Cierre de ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cerrarSesion();
            }
        });

        mostrarBienvenida();
    }

    private void mostrarBienvenida() {
        textArea.setText("Bienvenido, " + alumno.getNombre() + " " + alumno.getApellido() + "\n" +
                "Selecciona una opción del menú inferior.");
    }

    private void mostrarDatos() {
        textArea.setText("--- Tus datos ---\n" + alumno.toString());
    }

    private void mostrarNotas() {
        textArea.setText(String.format("--- Tus notas ---\nNota media: %.2f", alumno.getNotaMedia()));
    }

    private void mostrarHorario() {
        textArea.setText("--- Tu horario ---\n" + alumno.getHorario());
    }

    private void mostrarAgenda() {
        textArea.setText("--- Tu agenda ---\n" + alumno.getAgenda());
    }

    private void mostrarFaltas() {
        textArea.setText("--- Tus faltas de asistencia ---\n" + alumno.getFaltas());
    }

    private void cerrarSesion() {
        dispose();
        System.exit(0); // o volver al login si prefieres
    }
}
