import java.awt.*;
import java.awt.event.*;

public class LoginAWT extends Frame {

    private TextField tfInput;
    private Choice chTipoUsuario;
    private Label lblMensaje;

    public LoginAWT() {
        super("Login al sistema");

        setSize(300, 200);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        Label lblTipo = new Label("Tipo de usuario:");
        lblTipo.setBounds(30, 30, 100, 20);
        add(lblTipo);

        chTipoUsuario = new Choice();
        chTipoUsuario.add("admin");
        chTipoUsuario.add("alumno");
        chTipoUsuario.setBounds(140, 30, 100, 20);
        add(chTipoUsuario);

        Label lblInput = new Label("DNI o contraseña:");
        lblInput.setBounds(30, 70, 100, 20);
        add(lblInput);

        tfInput = new TextField();
        tfInput.setBounds(140, 70, 100, 20);
        add(tfInput);

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setBounds(100, 110, 80, 30);
        add(btnEntrar);

        lblMensaje = new Label("");
        lblMensaje.setBounds(30, 150, 250, 20);
        lblMensaje.setForeground(Color.RED);
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
                // Abrir gestor admin AWT
                GestorAlumnosAWT gestor = new GestorAlumnosAWT();
                gestor.setVisible(true);
                this.dispose();
            } else {
                lblMensaje.setText("Contraseña incorrecta");
            }
        } else if (tipo.equals("alumno")) {
            GestorAlumnos gestor = new GestorAlumnos();
            Alumno alumno = gestor.buscarAlumnoPorDni(input);
            if (alumno != null) {
                lblMensaje.setText("");
                new AlumnoAWT(alumno).setVisible(true);  // 👈 LÍNEA REEMPLAZADA
                this.dispose();
            } else {
                lblMensaje.setText("DNI no encontrado");
            }
        }
    }

    public static void main(String[] args) {
        LoginAWT login = new LoginAWT();
        login.setVisible(true);
    }
}
