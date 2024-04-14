package ReservasCitas.InterfazAdmin.Interfaz;

import ReservasCitas.InterfazLogin.InterfazLogin.InterfazLogin;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class InterfazAdmin extends JFrame {
    private final JFrame frame = new JFrame();
    private final GestionHabitaciones gestionHabitaciones;
    private final GestionUsuarios gestionUsuarios;
    private final GestionReservas gestionReservas;
    private final URL iconAdmin = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Admin.png");
    private final ImageIcon imgAdmin = new ImageIcon(iconAdmin);
    private final URL logopng = InterfazLogin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Logo.png");
    private final ImageIcon imgLogopng = new ImageIcon(logopng);
    private final URL iconlogo = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/LogoR.png");
    private final ImageIcon LogoR = new ImageIcon(iconlogo);
    private final URL iconAgregar = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Agregar.png");
    private final ImageIcon imgAgregar = new ImageIcon(iconAgregar);
    private final URL iconCliente = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Clientes.png");
    private final ImageIcon imgClientes = new ImageIcon(iconCliente);
    private final URL iconReservaciones = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Reservaciones.png");
    private final ImageIcon imgReservacioes = new ImageIcon(iconReservaciones);
    private final JPanel panelUtilidades = new JPanel();
    private final JPanel panelLogo = new JPanel();
    private final JPanel panelprincipal = new JPanel();
    private final JLabel lblBienvenida = new JLabel();
    private final JButton btnhabitaciones = new JButton(imgAgregar);
    private final JButton btnUsuarios = new JButton(imgClientes);
    private final JButton btnReservaciones = new JButton(imgReservacioes);
    private final JButton btnCerrarSecion = new JButton("Cerrar sesión");

    public void frame() {
        frame.setSize(1920, 1080);
        frame.setTitle("Gestion administrador");
        frame.setIconImage(imgLogopng.getImage());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void componentes() {
        panelLogo.setBounds(205, 0, 1070, 100);
        panelLogo.setBackground(new Color(113, 197, 232));
        panelLogo.setBorder(BorderFactory.createLineBorder(Color.black));
        panelLogo.setLayout(null);

        JLabel lblTitulo = new JLabel("Oasis");
        lblTitulo.setBounds(450, 25, 200, 25);
        lblTitulo.setFont(new Font("ROG Fonts", Font.BOLD, 24));
        panelLogo.add(lblTitulo);

        JLabel lblSogan = new JLabel(" Hotel & resort ");
        lblSogan.setBounds(350, 50, 400, 22);
        lblSogan.setFont(new Font("ROG Fonts", Font.BOLD, 24));
        panelLogo.add(lblSogan);

        JLabel imgLogo = new JLabel(LogoR);
        imgLogo.setBounds(650, 0, 100, 100);
        panelLogo.add(imgLogo);
        frame.add(panelLogo);

        panelUtilidades.setBounds(5, 0, 195, 645);
        panelUtilidades.setBackground(new Color(113, 197, 232));
        panelUtilidades.setBorder(BorderFactory.createLineBorder(Color.black));
        panelUtilidades.setLayout(null);

        JLabel imgusuario = new JLabel(imgAdmin);
        imgusuario.setBounds(60, 0, 80, 100);
        panelUtilidades.add(imgusuario);

        lblBienvenida.setBounds(30, 110, 200, 20);
        lblBienvenida.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        panelUtilidades.add(lblBienvenida);

        btnhabitaciones.setBounds(70, 200, 64, 64);
        btnhabitaciones.setContentAreaFilled(false);
        btnhabitaciones.setBorderPainted(false);
        panelUtilidades.add(btnhabitaciones);

        JLabel lblhabitaciones = new JLabel("Habitaciones");
        lblhabitaciones.setBounds(65, 265, 100, 20);
        lblhabitaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        panelUtilidades.add(lblhabitaciones);

        btnUsuarios.setBounds(70, 300, 64, 64);
        btnUsuarios.setContentAreaFilled(false);
        btnUsuarios.setBorderPainted(false);
        panelUtilidades.add(btnUsuarios);

        JLabel lblClientes = new JLabel("Usuarios");
        lblClientes.setBounds(80, 365, 100, 20);
        lblClientes.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        panelUtilidades.add(lblClientes);

        btnReservaciones.setBounds(70, 400, 64, 64);
        btnReservaciones.setContentAreaFilled(false);
        btnReservaciones.setBorderPainted(false);
        panelUtilidades.add(btnReservaciones);

        JLabel lblReservaciones = new JLabel("Reservaciones");
        lblReservaciones.setBounds(62, 465, 100, 20);
        lblReservaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        panelUtilidades.add(lblReservaciones);

        btnCerrarSecion.setBounds(30, 590, 140, 25);
        btnCerrarSecion.setForeground(null);
        btnCerrarSecion.setContentAreaFilled(false);
        btnCerrarSecion.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        panelUtilidades.add(btnCerrarSecion);
        frame.add(panelUtilidades);

        panelprincipal.setBounds(205, 105, 1070, 540);
        panelprincipal.setBackground(new Color(113, 197, 232));
        panelprincipal.setBorder(BorderFactory.createLineBorder(Color.black));
        panelprincipal.setLayout(null);

        frame.add(panelprincipal);
    }
    public void abrirPanelSegunBoton() {
        btnhabitaciones.addActionListener(e -> gestionHabitaciones.mostrarHabitaciones());
        btnUsuarios.addActionListener(e -> gestionUsuarios.mostrarUsuarios());
        btnReservaciones.addActionListener(e -> gestionReservas.mostrarReservaciones());
    }
    public void establecerNombreUsuario(String nombreUsuario) {
        lblBienvenida.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblBienvenida.setText(" ! Bienvenido, " + nombreUsuario + " !");
    }
    public void cerrarsecion() {
        btnCerrarSecion.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Estas seguro que quieres cerrar la sesión?",
                    "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "sesión cerrada con exito.");
                frame.dispose();
                new InterfazLogin();
            }
        });
    }
    public InterfazAdmin() {
        frame();
        componentes();
        abrirPanelSegunBoton();
        cerrarsecion();
        JButton agregarH = new JButton("Agregar");
        JButton editarH = new JButton("Editar");
        JButton eliminarH = new JButton("Eliminar");
        JButton filtrarHPorTipo = new JButton("Filtrar");
        gestionHabitaciones = new GestionHabitaciones(panelprincipal, frame, agregarH, editarH, eliminarH, filtrarHPorTipo);
        JButton buscarU = new JButton("Buscar");
        JButton editarU = new JButton("Editar");
        JButton eliminarU = new JButton("Eliminar");
        JButton agregarU = new JButton("Agregar usuario");
        gestionUsuarios = new GestionUsuarios(frame,panelprincipal, agregarU, buscarU, editarU, eliminarU);
        JButton buscarR = new JButton("Buscar reservacion");
        JButton crearR = new JButton("Crear Reservacion");
        JButton estadoR = new JButton("Editar Reservacion");
        JButton eliminarR = new JButton("Eliminar Reservacion");
        gestionReservas = new GestionReservas(frame, panelprincipal, buscarR, crearR, estadoR, eliminarR);
    }
}