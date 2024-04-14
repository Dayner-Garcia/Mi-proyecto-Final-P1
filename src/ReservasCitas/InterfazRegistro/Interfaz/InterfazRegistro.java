package ReservasCitas.InterfazRegistro.Interfaz;

import ReservasCitas.ConnectionDB.ConsultasBD;
import ReservasCitas.InterfazLogin.InterfazLogin.InterfazLogin;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class InterfazRegistro extends JFrame {
    private final JFrame frame = new JFrame();
    private final URL registrarseIMG = InterfazRegistro.class.getResource("/ReservasCitas/InterfazRegistro/Icons/Registrarse.png");
    private final ImageIcon imgRegistrarse = new ImageIcon(registrarseIMG);
    private final URL registrarse2 = InterfazRegistro.class.getResource("/ReservasCitas/InterfazRegistro/Icons/Registrarse1.png");
    private final ImageIcon imgRegistrarse2 = new ImageIcon(registrarse2);
    private final URL ver = InterfazLogin.class.getResource("/ReservasCitas/InterfazRegistro/Icons/ver.png");
    private final ImageIcon imgver = new ImageIcon(ver);
    private final JTextField nombreCompletofield = new JTextField();
    private final JTextField nombreUsuariosfield = new JTextField();
    private final JPasswordField passwordfield = new JPasswordField();
    private final JTextField emailfield = new JTextField();
    private final JTextField telefonofield = new JTextField();
    private final JTextField cedulafield = new JTextField();
    private final JButton registraseBTN = new JButton("Registrarse");
    private final JButton mostrar = new JButton(imgver);

    public void frame() {
        frame.setSize(430, 500);
        frame.setTitle("Registrarse");
        frame.setIconImage(imgRegistrarse.getImage());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void componentes() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 480, 540);
        panel.setBackground(Color.white);
        panel.setLayout(null);

        JLabel lblRegistrarse = new JLabel("Registrarse");
        lblRegistrarse.setBounds(100, 40, 150, 30);
        lblRegistrarse.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        panel.add(lblRegistrarse);

        JLabel lblIMG = new JLabel(imgRegistrarse2);
        lblIMG.setBounds(240, 5, 96, 96);
        panel.add(lblIMG);

        JLabel nombreCompleto = new JLabel("Nombre Completo");
        nombreCompleto.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        nombreCompleto.setBounds(20, 90, 200, 20);
        panel.add(nombreCompleto);

        nombreCompletofield.setBounds(20, 110, 380, 20);
        nombreCompletofield.setBorder(null);
        panel.add(nombreCompletofield);

        JLabel linea1 = new JLabel("________________________________________________________");
        linea1.setBounds(20, 115, 400, 20);
        panel.add(linea1);

        JLabel nombreUsuarios = new JLabel("Nombre de usuario");
        nombreUsuarios.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        nombreUsuarios.setBounds(20, 145, 300, 20);
        panel.add(nombreUsuarios);

        nombreUsuariosfield.setBounds(20, 165, 380, 20);
        nombreUsuariosfield.setBorder(null);
        panel.add(nombreUsuariosfield);

        JLabel linea2 = new JLabel("________________________________________________________");
        linea2.setBounds(20, 170, 400, 20);
        panel.add(linea2);

        JLabel password = new JLabel("Contraseña");
        password.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        password.setBounds(20, 200, 200, 20);
        panel.add(password);

        mostrar.setBounds(370, 210, 24, 24);
        mostrar.setBorderPainted(false);
        mostrar.setContentAreaFilled(false);
        mostrar.setOpaque(false);
        panel.add(mostrar);

        passwordfield.setBounds(20, 220, 350, 20);
        passwordfield.setBorder(null);

        mostrar.addActionListener(e -> {
            char echoChar = passwordfield.getEchoChar();
            if (echoChar == '\0') {
                passwordfield.setEchoChar('•');
            } else {
                passwordfield.setEchoChar((char) 0);
            }
        });
        panel.add(passwordfield);

        JLabel linea3 = new JLabel("________________________________________________________");
        linea3.setBounds(20, 225, 400, 20);
        panel.add(linea3);

        JLabel email = new JLabel("Correo electronico");
        email.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        email.setBounds(20, 255, 300, 20);
        panel.add(email);

        emailfield.setBounds(20, 275, 380, 20);
        emailfield.setBorder(null);
        panel.add(emailfield);

        JLabel linea4 = new JLabel("________________________________________________________");
        linea4.setBounds(20, 280, 400, 20);
        panel.add(linea4);

        JLabel telefono = new JLabel("Telefono");
        telefono.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        telefono.setBounds(20, 310, 200, 20);
        panel.add(telefono);

        telefonofield.setBounds(20, 330, 380, 20);
        telefonofield.setBorder(null);
        panel.add(telefonofield);

        JLabel linea5 = new JLabel("________________________________________________________");
        linea5.setBounds(20, 335, 400, 20);
        panel.add(linea5);

        JLabel cedula = new JLabel("Cedula");
        cedula.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        cedula.setBounds(20, 365, 200, 20);
        panel.add(cedula);

        cedulafield.setBounds(20, 385, 380, 20);
        cedulafield.setBorder(null);
        panel.add(cedulafield);

        JLabel linea6 = new JLabel("________________________________________________________");
        linea6.setBounds(20, 390, 400, 20);
        panel.add(linea6);

        registraseBTN.setBounds(125, 425, 150, 20);
        registraseBTN.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        registraseBTN.setContentAreaFilled(false);
        registraseBTN.setOpaque(false);
        panel.add(registraseBTN);

        frame.add(panel);
    }

    public void textFieldsAcciones() {
        nombreCompletofield.addActionListener(e -> nombreUsuariosfield.requestFocusInWindow());
        nombreUsuariosfield.addActionListener(e -> passwordfield.requestFocusInWindow());
        passwordfield.addActionListener(e -> emailfield.requestFocusInWindow());
        emailfield.addActionListener(e -> telefonofield.requestFocusInWindow());
        telefonofield.addActionListener(e -> cedulafield.requestFocusInWindow());
        cedulafield.addActionListener(e -> registraseBTN.requestFocusInWindow());
    }

    public void accionesBotones() {
        registraseBTN.addActionListener(e -> {
            String nombreCompleto = nombreCompletofield.getText();
            String nombreUsuario = nombreUsuariosfield.getText();
            String password = new String(passwordfield.getPassword());
            String email = emailfield.getText();
            String telefono = telefonofield.getText();
            String cedula = cedulafield.getText();

            if (!nombreCompleto.isEmpty() && !nombreUsuario.isEmpty() && !password.isEmpty() && !email.isEmpty() && !telefono.isEmpty() && !cedula.isEmpty()) {
                if (ConsultasBD.registrarUsuario(nombreCompleto, nombreUsuario, password, email, telefono, cedula)) {
                    JOptionPane.showMessageDialog(frame, "Usted se ha registrado con éxito.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al registrarse.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.");
            }
        });
    }

    public InterfazRegistro() {
        frame();
        componentes();
        accionesBotones();
        textFieldsAcciones();
    }
}