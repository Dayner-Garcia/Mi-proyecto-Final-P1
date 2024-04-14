package ReservasCitas.InterfazLogin.InterfazLogin;

import ReservasCitas.ConnectionDB.ConsultasBD;
import ReservasCitas.InterfazClientes.Interfaz.InterfazClientes;
import ReservasCitas.InterfazAdmin.Interfaz.InterfazAdmin;
import ReservasCitas.InterfazRegistro.Interfaz.InterfazRegistro;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class InterfazLogin extends JFrame{
    private final JFrame frame = new JFrame();
    private final URL logopng = InterfazLogin.class.getResource("/ReservasCitas/InterfazLogin/Icons/Logo.png");
    private final ImageIcon imgLogopng = new ImageIcon(logopng);
    private final URL logojpg = InterfazLogin.class.getResource("/ReservasCitas/InterfazLogin/Icons/Logo.jpeg");
    private final ImageIcon imgLogojpg = new ImageIcon(logojpg);
    private final URL usuario = InterfazLogin.class.getResource("/ReservasCitas/InterfazLogin/Icons/usuario.png");
    private final ImageIcon imgUsuario = new ImageIcon(usuario);
    private final URL ver = InterfazLogin.class.getResource("/ReservasCitas/InterfazRegistro/Icons/ver.png");
    private final ImageIcon imgver = new ImageIcon(ver);
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JButton iniciar = new JButton("Iniciar Seccion");
    private final JButton registrarse = new JButton("Registrarse");
    private final JButton verPassword = new JButton(imgver);

    public void frame() {
        frame.setSize(960, 540);
        frame.setTitle("Oasis Hotel");
        frame.setIconImage(imgLogopng.getImage());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void componentes() {
        JPanel panelLogo = new JPanel();
        panelLogo.setLayout(null);
        panelLogo.setBounds(0, 0, 480, 540);
        JLabel labelLogo = new JLabel(imgLogojpg);
        labelLogo.setBounds(0, 0, 480, 540);
        panelLogo.add(labelLogo);
        frame.add(panelLogo);

        JPanel panelComponentes = new JPanel();
        panelComponentes.setBackground(Color.white);
        panelComponentes.setLayout(null);
        panelComponentes.setBounds(480, 0, 480, 540);

        JLabel imgusuario = new JLabel(new ImageIcon(imgUsuario.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        imgusuario.setBounds(200, 20, 100, 100);
        panelComponentes.add(imgusuario);

        JLabel singIN = new JLabel("Iniciar seccion");
        singIN.setBounds(175, 120, 200, 25);
        singIN.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        panelComponentes.add(singIN);

        JLabel usuarioLabel = new JLabel("Usuario");
        usuarioLabel.setBounds(40, 190, 100, 20);
        usuarioLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panelComponentes.add(usuarioLabel);

        txtUsuario.setBounds(40, 210, 360, 20);
        txtUsuario.setBorder(null);
        txtUsuario.addActionListener(e -> txtPassword.requestFocusInWindow());
        panelComponentes.add(txtUsuario);

        JLabel linea1 = new JLabel("________________________________________________________");
        linea1.setBounds(40, 220, 500, 20);
        panelComponentes.add(linea1);

        JLabel password = new JLabel("Contraseña");
        password.setBounds(40, 280, 150, 20);
        password.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panelComponentes.add(password);

        txtPassword.setBounds(40, 300, 360, 20);
        txtPassword.setBorder(null);

        verPassword.addActionListener(e -> {
            char echoChar = txtPassword.getEchoChar();
            if (echoChar == '\0') {
                txtPassword.setEchoChar('•');
            } else {
                txtPassword.setEchoChar((char) 0);
            }
        });
        panelComponentes.add(txtPassword);

        verPassword.setBounds(400, 300, 25, 25);
        verPassword.setOpaque(false);
        verPassword.setBorderPainted(false);
        verPassword.setContentAreaFilled(false);
        panelComponentes.add(verPassword);

        JLabel linea2 = new JLabel("________________________________________________________");
        linea2.setBounds(40, 310, 500, 20);
        panelComponentes.add(linea2);

        iniciar.setBounds(40, 370, 150, 20);
        iniciar.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        iniciar.setOpaque(false);
        iniciar.setContentAreaFilled(false);
        panelComponentes.add(iniciar);

        JLabel notienes = new JLabel("        ¿No tienes una cuenta?");
        notienes.setForeground(new Color(0x00BBFF));
        notienes.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));
        notienes.setBounds(250, 342, 150, 20);
        panelComponentes.add(notienes);

        JLabel crearUna = new JLabel("! Haz clic en registrate primero !");
        crearUna.setForeground(new Color(0x00BBFF));
        crearUna.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10));
        crearUna.setBounds(250, 352, 150, 20);
        panelComponentes.add(crearUna);

        registrarse.setBounds(250, 370, 150, 20);
        registrarse.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        registrarse.setOpaque(false);
        registrarse.setContentAreaFilled(false);
        panelComponentes.add(registrarse);

        frame.add(panelComponentes);
    }
    public void accionesBotones() {
        iniciar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            String tipoUsuario = ConsultasBD.verificarCredenciales(usuario, password);

            if (tipoUsuario != null) {
                int idUsuario = ConsultasBD.obtenerIdUsuario(usuario);
                abrirInterfazSegunUsuario(tipoUsuario, usuario, idUsuario);
            } else {
                JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectas.");
            }
        });
        registrarse.addActionListener(e -> new InterfazRegistro());
    }
    public void abrirInterfazSegunUsuario(String tipoUsuario, String nombreUsuario, int idUsuario) {
        if (tipoUsuario != null) {
            if (tipoUsuario.equals("Cliente")) {
                InterfazClientes cliente = new InterfazClientes(idUsuario);
                cliente.establecerNombreUsuario(nombreUsuario);
            } else if (tipoUsuario.equals("Administrador")) {
                InterfazAdmin admin = new InterfazAdmin();
                admin.establecerNombreUsuario(nombreUsuario);
            }
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectas.");
        }
    }
    public InterfazLogin() {
        frame();
        componentes();
        accionesBotones();
    }
    public static void main(String[] args) {
        new InterfazLogin();
    }
}