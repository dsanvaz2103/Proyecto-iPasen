package gestoralumnos;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestorAlumnosAWT extends Frame {

    private GestorAlumnos gestor;
    private TextArea areaTexto;
    private TextField tfInput;
    private Label lblInput;
    private Button btnEjecutar;
    private int opcionSeleccionada = 0;

    public GestorAlumnosAWT() {
        super("Gestor de Alumnos - Admin");

        gestor = new GestorAlumnos();

        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // Botones
        Button btnVerAlumnos = new Button("1. Ver alumnos");
        btnVerAlumnos.setBounds(30, 30, 120, 30);
        add(btnVerAlumnos);

        Button btnVerAlumnoDNI = new Button("2. Ver alumno por DNI");
        btnVerAlumnoDNI.setBounds(160, 30, 150, 30);
        add(btnVerAlumnoDNI);

        Button btnAñadirAlumno = new Button("3. Añadir alumno");
        btnAñadirAlumno.setBounds(320, 30, 120, 30);
        add(btnAñadirAlumno);

        Button btnModificarAlumno = new Button("4. Modificar alumno");
        btnModificarAlumno.setBounds(30, 70, 150, 30);
        add(btnModificarAlumno);

        Button btnEliminarAlumno = new Button("5. Eliminar alumno");
        btnEliminarAlumno.setBounds(190, 70, 150, 30);
        add(btnEliminarAlumno);

        Button btnSalir = new Button("6. Salir");
        btnSalir.setBounds(350, 70, 90, 30);
        add(btnSalir);

        lblInput = new Label("");
        lblInput.setBounds(30, 120, 200, 25);
        lblInput.setVisible(false);
        add(lblInput);

        tfInput = new TextField();
        tfInput.setBounds(240, 120, 200, 25);
        tfInput.setVisible(false);
        add(tfInput);

        btnEjecutar = new Button("Ejecutar");
        btnEjecutar.setBounds(200, 160, 100, 30);
        btnEjecutar.setVisible(false);
        add(btnEjecutar);

        areaTexto = new TextArea();
        areaTexto.setBounds(30, 210, 440, 160);
        areaTexto.setEditable(false);
        add(areaTexto);

        btnVerAlumnos.addActionListener(e -> {
            opcionSeleccionada = 1;
            ocultarInput();
            mostrarAlumnos();
        });

        btnVerAlumnoDNI.addActionListener(e -> {
            opcionSeleccionada = 2;
            mostrarInput("Introduce DNI para consulta:");
        });

        btnAñadirAlumno.addActionListener(e -> {
            opcionSeleccionada = 3;
            mostrarDialogoAñadirAlumno();
        });

        btnModificarAlumno.addActionListener(e -> {
            opcionSeleccionada = 4;
            mostrarInput("Introduce DNI del alumno a modificar:");
        });

        btnEliminarAlumno.addActionListener(e -> {
            opcionSeleccionada = 5;
            mostrarInput("Introduce DNI del alumno a eliminar:");
        });

        btnSalir.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

        btnEjecutar.addActionListener(e -> ejecutarOpcion());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    private void mostrarInput(String texto) {
        lblInput.setText(texto);
        lblInput.setVisible(true);
        tfInput.setText("");
        tfInput.setVisible(true);
        btnEjecutar.setVisible(true);
        areaTexto.setText("");
    }

    private void ocultarInput() {
        lblInput.setVisible(false);
        tfInput.setVisible(false);
        btnEjecutar.setVisible(false);
    }

    private void ejecutarOpcion() {
        String input = tfInput.getText().trim();

        switch (opcionSeleccionada) {
            case 2:
                Alumno a = gestor.buscarAlumnoPorDni(input);
                areaTexto.setText(a != null ? a.toString() : "Alumno no encontrado con DNI: " + input);
                ocultarInput();
                break;

            case 4:
                Alumno alumnoMod = gestor.buscarAlumnoPorDni(input);
                if (alumnoMod != null) {
                    ocultarInput();
                    mostrarDialogoModificarAlumno(alumnoMod);
                } else {
                    areaTexto.setText("Alumno no encontrado con DNI: " + input);
                    ocultarInput();
                }
                break;

            case 5:
                try {
                    gestor.eliminarAlumno(input);
                    ArchivoAlumnos.guardarAlumnos(gestor.getAlumnos());
                    areaTexto.setText("Alumno con DNI " + input + " eliminado correctamente.");
                } catch (Exception ex) {
                    areaTexto.setText("Error al eliminar alumno: " + ex.getMessage());
                }
                ocultarInput();
                break;

            default:
                areaTexto.setText("Opción inválida o no requiere input.");
                ocultarInput();
                break;
        }
    }

    private void mostrarAlumnos() {
        StringBuilder sb = new StringBuilder();
        if (gestor.getAlumnos().isEmpty()) {
            sb.append("No hay alumnos registrados.\n");
        } else {
            for (Alumno a : gestor.getAlumnos()) {
                sb.append(a).append("\n----------------------\n");
            }
        }
        areaTexto.setText(sb.toString());
    }

    private void mostrarDialogoAñadirAlumno() {
        Frame dialog = new Frame("Añadir Alumno");
        dialog.setSize(350, 350);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        Label[] labels = {
            new Label("Nombre:"), new Label("Apellido:"), new Label("DNI:"), new Label("Edad:"),
            new Label("Curso:"), new Label("Nota media:"), new Label("Horario:"), new Label("Agenda:")
        };
        TextField[] fields = new TextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBounds(20, 30 + i * 30, 80, 20);
            dialog.add(labels[i]);
            fields[i] = new TextField();
            fields[i].setBounds(110, 30 + i * 30, 200, 20);
            dialog.add(fields[i]);
        }

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setBounds(50, 280, 80, 30);
        dialog.add(btnGuardar);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setBounds(180, 280, 80, 30);
        dialog.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                Alumno nuevo = new Alumno(
                    fields[0].getText().trim(),
                    fields[1].getText().trim(),
                    fields[2].getText().trim(),
                    Integer.parseInt(fields[3].getText().trim()),
                    fields[4].getText().trim(),
                    Double.parseDouble(fields[5].getText().trim())
                );
                nuevo.setHorario(fields[6].getText().trim());
                nuevo.setAgenda(fields[7].getText().trim());

                gestor.agregarAlumno(nuevo);
                ArchivoAlumnos.guardarAlumnos(gestor.getAlumnos());

                areaTexto.setText("Alumno añadido correctamente.\n");
                mostrarAlumnos();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                areaTexto.setText("Error: Edad o nota no válidas.\n");
            } catch (DniDuplicadoException ex) {
                areaTexto.setText("Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void mostrarDialogoModificarAlumno(Alumno alumno) {
        Frame dialog = new Frame("Modificar Alumno");
        dialog.setSize(350, 350);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(this);

        TextField[] fields = {
            new TextField(alumno.getNombre()), new TextField(alumno.getApellido()),
            new TextField(String.valueOf(alumno.getEdad())), new TextField(alumno.getCurso()),
            new TextField(String.valueOf(alumno.getNotaMedia())), new TextField(alumno.getHorario()),
            new TextField(alumno.getAgenda())
        };

        String[] labels = {
            "Nombre:", "Apellido:", "Edad:", "Curso:",
            "Nota media:", "Horario:", "Agenda:"
        };

        for (int i = 0; i < labels.length; i++) {
            Label l = new Label(labels[i]);
            l.setBounds(20, 30 + i * 30, 80, 20);
            dialog.add(l);
            fields[i].setBounds(110, 30 + i * 30, 200, 20);
            dialog.add(fields[i]);
        }

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setBounds(50, 250, 80, 30);
        dialog.add(btnGuardar);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setBounds(180, 250, 80, 30);
        dialog.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                alumno.setNombre(fields[0].getText().trim());
                alumno.setApellido(fields[1].getText().trim());
                alumno.setEdad(Integer.parseInt(fields[2].getText().trim()));
                alumno.setCurso(fields[3].getText().trim());
                alumno.setNotaMedia(Double.parseDouble(fields[4].getText().trim()));
                alumno.setHorario(fields[5].getText().trim());
                alumno.setAgenda(fields[6].getText().trim());

                ArchivoAlumnos.guardarAlumnos(gestor.getAlumnos());

                // Guardar también en la base de datos
                try {
                    Connection conn = ConexionMariaDB.conectar();
                    String sql = "UPDATE alumnos SET nombre = ?, apellido = ?, edad = ?, curso = ?, nota_media = ?, horario = ?, agenda = ? WHERE dni = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, alumno.getNombre());
                    stmt.setString(2, alumno.getApellido());
                    stmt.setInt(3, alumno.getEdad());
                    stmt.setString(4, alumno.getCurso());
                    stmt.setDouble(5, alumno.getNotaMedia());
                    stmt.setString(6, alumno.getHorario());
                    stmt.setString(7, alumno.getAgenda());
                    stmt.setString(8, alumno.getDni());
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    areaTexto.setText("Error al actualizar la base de datos: " + ex.getMessage());
                }

                areaTexto.setText("Alumno modificado correctamente.\n");
                mostrarAlumnos();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                areaTexto.setText("Error: Edad o nota no válidas.\n");
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        GestorAlumnosAWT app = new GestorAlumnosAWT();
        app.setVisible(true);
    }
}
