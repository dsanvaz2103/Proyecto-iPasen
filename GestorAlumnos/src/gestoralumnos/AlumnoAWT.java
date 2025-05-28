package gestoralumnos;

import java.awt.*;
import java.awt.event.*;

public class AlumnoAWT extends Frame {

    private Alumno alumno;
    private TextArea textArea;

    public AlumnoAWT(Alumno alumno) {
        super("Panel del Alumno");
        this.alumno = alumno;

        setSize(420, 320);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(new Color(245, 245, 245));

        // Área de texto
        textArea = new TextArea("", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setBackground(new Color(255, 255, 255));
        add(textArea, BorderLayout.CENTER);

        // Panel inferior con botones
        Panel panelBotones = new Panel(new GridLayout(2, 3, 8, 8));
        panelBotones.setBackground(new Color(230, 230, 250));
        panelBotones.setPreferredSize(new Dimension(420, 100));
        panelBotones.setFont(new Font("SansSerif", Font.PLAIN, 12));

        String[] etiquetas = {
            "Mis datos", "Mis notas", "Horario",
            "Agenda", "Faltas", "Salir"
        };

        Button[] botones = new Button[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            botones[i] = new Button(etiquetas[i]);
            botones[i].setBackground(new Color(100, 149, 237));
            botones[i].setForeground(Color.WHITE);
            botones[i].setFont(new Font("SansSerif", Font.BOLD, 12));
            panelBotones.add(botones[i]);
        }

        // Asignar acciones
        botones[0].addActionListener(e -> mostrarDatos());
        botones[1].addActionListener(e -> mostrarNotas());
        botones[2].addActionListener(e -> mostrarHorario());
        botones[3].addActionListener(e -> mostrarAgenda());
        botones[4].addActionListener(e -> mostrarFaltas());
        botones[5].addActionListener(e -> cerrarSesion());

        add(panelBotones, BorderLayout.SOUTH);

        // Evento de cierre
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cerrarSesion();
            }
        });

        mostrarBienvenida();
    }

    private void mostrarBienvenida() {
        textArea.setText("👋 Bienvenido, " + alumno.getNombre() + " " + alumno.getApellido() + "\n\n" +
                         "Selecciona una opción del menú inferior para ver tu información.");
    }

    private void mostrarDatos() {
        textArea.setText("📋 TUS DATOS\n\n" + alumno.toString());
    }

    private void mostrarNotas() {
        textArea.setText(String.format("📊 TUS NOTAS\n\nNota media: %.2f", alumno.getNotaMedia()));
    }

    private void mostrarHorario() {
        textArea.setText("🗓️ TU HORARIO\n\n" + alumno.getHorario());
    }

    private void mostrarAgenda() {
        textArea.setText("📝 TU AGENDA\n\n" + alumno.getAgenda());
    }

    private void mostrarFaltas() {
        textArea.setText("🚫 TUS FALTAS DE ASISTENCIA\n\n" + alumno.getFaltas());
    }

    private void cerrarSesion() {
        dispose();
        System.exit(0); // También podrías volver al login aquí si quieres
    }
}
