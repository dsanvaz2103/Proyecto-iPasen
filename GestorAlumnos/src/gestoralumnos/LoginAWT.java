package gestoralumnos;

import java.awt.*;
import java.awt.event.*;

public class LoginAWT extends Frame {

    private TextField tfInput;
    private Choice chTipoUsuario;
    private Label lblMensaje;

    public LoginAWT() {
        super("Login");

        setSize(280, 180);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(new Color(245, 245, 245));

        Font fuente = new Font("SansSerif", Font.PLAIN, 13);

        Label lblTipo = new Label("Usuario:");
        lblTipo.setBounds(30, 30, 70, 20);
        lblTipo.setFont(fuente);
        add(lblTipo);

        chTipoUsuario = new Choice();
        chTipoUsuario.add("admin");
        chTipoUsuario.add("alumno");
        chTipoUsuario.setBounds(110, 30, 120, 20);
        add(chTipoUsuario);

        Label lblInput = new Label("Pass/DNI:");
        lblInput.setBounds(30, 60, 70, 20);
        lblInput.setFont(fuente);
        add(lblInput);

        tfInput = new TextField();
        tfInput.setBounds(110, 60, 120, 20);
        add(tfInput);

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setBounds(90, 95, 100, 30);
        btnEntrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEntrar.setBackground(new Color(100, 149, 237));
        btnEntrar.setForeground(Color.WHITE);
        add(btnEntrar);

        lblMensaje = new Label("", Label.CENTER);
        lblMensaje.setBounds(30, 135, 220, 20);
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setFont(new Font("SansSerif", Font.ITALIC, 12));
        add(lblMensaje);

        btnEntrar.addActionListener(e -> hacerLogin());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    private void hacerLogin() {
        String tipo = chTipoUsuario.getSelectedItem();
        String input = tfInput.getText().trim();

        if (tipo.equals("admin")) {
            if ("1234".equals(input)) {
                lblMensaje.setText("");
                new GestorAlumnosAWT().setVisible(true);
                this.dispose();
            } else {
                lblMensaje.setText("Contraseña incorrecta.");
            }
        } else {
            GestorAlumnos gestor = new GestorAlumnos();
            Alumno alumno = gestor.buscarAlumnoPorDni(input);
            if (alumno != null) {
                lblMensaje.setText("");
                new AlumnoAWT(alumno).setVisible(true);
                this.dispose();
            } else {
                lblMensaje.setText("DNI no encontrado.");
            }
        }
    }

    public static void main(String[] args) {
        LoginAWT login = new LoginAWT();
        login.setVisible(true);
    }
}
