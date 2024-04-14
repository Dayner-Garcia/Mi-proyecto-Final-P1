package ReservasCitas.InterfazClientes.Interfaz;

import ReservasCitas.ConnectionDB.ConsultasBD;
import ReservasCitas.InterfazAdmin.Interfaz.InterfazAdmin;
import ReservasCitas.InterfazLogin.InterfazLogin.InterfazLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class InterfazClientes extends JFrame {
    private final JFrame frame = new JFrame();
    private final JPanel panelUtilidades = new JPanel();
    private final JPanel panelLogo = new JPanel();
    private final JPanel panelprincipal = new JPanel();
    private final URL iconAdmin = InterfazClientes.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Admin.png");
    private final ImageIcon imgAdmin = new ImageIcon(iconAdmin);
    private final URL logopng = InterfazLogin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Logo.png");
    private final ImageIcon imgLogopng = new ImageIcon(logopng);
    private final URL iconlogo = InterfazClientes.class.getResource("/ReservasCitas/InterfazAdmin/Icons/LogoR.png");
    private final ImageIcon LogoR = new ImageIcon(iconlogo);
    private final URL iconReservaciones = InterfazClientes.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Reservaciones.png");
    private final ImageIcon imgReservacioes = new ImageIcon(iconReservaciones);
    private final JLabel lblBienvenida = new JLabel();
    private final JButton btnReservaciones = new JButton(imgReservacioes);
    private final JButton btnCerrarSecion = new JButton("Cerrar sesion");
    private final URL iconAgregar = InterfazAdmin.class.getResource("/ReservasCitas/InterfazAdmin/Icons/Agregar.png");
    private final ImageIcon imgAgregar = new ImageIcon(iconAgregar);
    private final JButton realizarReserva = new JButton("Reserva");
    private final JButton editarMireserva = new JButton("Editar mi Reserva");
    private final JButton imprimirReserva = new JButton("Imprimir tiket de reserva");
    private JTable table;
    private final int idUsuario;
    private final JButton verMisReservas = new JButton("ver mis Reserva");
    private final JButton habitaciones = new JButton("Habitaciones");

    public void frame() {
        frame.setSize(1920, 1080);
        frame.setTitle("Realizar reservaciones");
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
        btnReservaciones.setBounds(70, 250, 64, 64);
        btnReservaciones.setContentAreaFilled(false);
        btnReservaciones.setBorderPainted(false);
        panelUtilidades.add(btnReservaciones);
        JLabel lblhabitaciones = new JLabel("Realizar una Reservacion");
        lblhabitaciones.setBounds(15, 315, 200, 20);
        lblhabitaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        panelUtilidades.add(lblhabitaciones);
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

    public void mostrarHabitacionesParaReservar() {
        panelprincipal.removeAll();
        JLabel titulo = new JLabel("Realizar una Reservacion");
        titulo.setBounds(360, 40, 300, 28);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        panelprincipal.add(titulo);
        JLabel imghabitaciones = new JLabel(imgAgregar);
        imghabitaciones.setBounds(350, 100, 40, 40);
        panelprincipal.add(imghabitaciones);
        JLabel tituloC = new JLabel("Habitaciones Disponibles");
        tituloC.setBounds(100, 120, 300, 24);
        tituloC.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panelprincipal.add(tituloC);
        realizarReserva.setBounds(700, 220, 200, 20);
        realizarReserva.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        realizarReserva.setContentAreaFilled(false);
        panelprincipal.add(realizarReserva);
        verMisReservas.setBounds(700, 320, 200, 20);
        verMisReservas.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        verMisReservas.setContentAreaFilled(false);
        panelprincipal.add(verMisReservas);

        verMisReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarReservaciones();
            }
        });

        Vector<Vector<Object>> data = ConsultasBD.obtenerHabitacionesDisponibles();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Tipo");
        columnNames.add("Precio");
        columnNames.add("Descripcion");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 150, 575, 300);
        panelprincipal.add(scrollPane);

        panelprincipal.revalidate();
        panelprincipal.repaint();
    }

    public void realizarReserva() {
        realizarReserva.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una habitacion para reservar.");
                return;
            }

            int habitacionId = (int) table.getValueAt(selectedRow, 0);
            Date fechaInicio = obtenerFechaInicio();
            Date fechaFin = obtenerFechaFin(fechaInicio);

            int confirmacion = JOptionPane.showConfirmDialog(frame, "¿Estas seguro de realizar esta reserva?", "Confirmar Reserva", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                ConsultasBD.realizarReserva(idUsuario, habitacionId, fechaInicio, fechaFin);
                ConsultasBD.marcarHabitacionNoDisponible(habitacionId);
                JOptionPane.showMessageDialog(frame, "¡Reserva realizada corectamente!");
                mostrarReservaciones();
            }
        });
    }

    private Date obtenerFechaInicio() {
        return new Date();
    }

    private Date obtenerFechaFin(Date fechaInicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTime();
    }

    public void mostrarReservaciones() {
        panelprincipal.removeAll();
        JLabel titulo = new JLabel("Mis Reservas");
        titulo.setBounds(430, 40, 300, 28);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        panelprincipal.add(titulo);

        habitaciones.setBounds(175, 450, 200, 20);
        habitaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        habitaciones.setContentAreaFilled(false);
        panelprincipal.add(habitaciones);
        habitaciones.addActionListener(e -> mostrarHabitacionesParaReservar());

        editarMireserva.setBounds(400, 450, 200, 20);
        editarMireserva.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        editarMireserva.setContentAreaFilled(false);
        panelprincipal.add(editarMireserva);

        imprimirReserva.setBounds(625, 450, 200, 20);
        imprimirReserva.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        imprimirReserva.setContentAreaFilled(false);
        panelprincipal.add(imprimirReserva);

        Vector<Vector<Object>> data = ConsultasBD.obtenerReservacionesCliente(idUsuario);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("ID de Habitacion");
        columnNames.add("Tipo de Habitacion");
        columnNames.add("Precio");
        columnNames.add("Fecha de Inicio");
        columnNames.add("Fecha de Fin");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 100, 700, 300);
        panelprincipal.add(scrollPane);

        panelprincipal.revalidate();
        panelprincipal.repaint();
    }

    public void editarMiReserva() {
        editarMireserva.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una reserva para editar.");
                return;
            }

            int reservaId = (int) table.getValueAt(selectedRow, 0);
            String fechaInicioStr = (String) table.getValueAt(selectedRow, 4);
            Date fechaInicioActual = null;
            try {
                fechaInicioActual = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));

            JLabel lblFechaInicio = new JLabel("Nueva Fecha de Inicio:");
            JTextField txtFechaInicio = new JTextField(10);
            if (fechaInicioActual != null) {
                txtFechaInicio.setText(new SimpleDateFormat("yyyy-MM-dd").format(fechaInicioActual));
            }
            panel.add(lblFechaInicio);
            panel.add(txtFechaInicio);

            if (fechaInicioActual != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicioActual);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                Date nuevaFechaFin = calendar.getTime();

                JLabel lblFechaFin = new JLabel("Fecha de Fin:");
                JTextField txtFechaFin = new JTextField(10);
                txtFechaFin.setEditable(false);
                txtFechaFin.setText(new SimpleDateFormat("yyyy-MM-dd").format(nuevaFechaFin));
                panel.add(lblFechaFin);
                panel.add(txtFechaFin);
            }

            int confirmacion = JOptionPane.showConfirmDialog(frame, panel, "Editar Reserva", JOptionPane.OK_CANCEL_OPTION);
            if (confirmacion == JOptionPane.OK_OPTION) {
                String nuevaFechaInicioStr = txtFechaInicio.getText();

                boolean actualizacionExitosa = ConsultasBD.actualizarReserva(reservaId, nuevaFechaInicioStr);

                if (actualizacionExitosa) {
                    JOptionPane.showMessageDialog(frame, "¡Reserva actualizada correctamente!");
                    mostrarReservaciones();
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo actualizar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void imprimirReserva() {
        imprimirReserva.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona una reserva para imprimir.");
                return;
            }
            int reservaId = (int) table.getValueAt(selectedRow, 0);
            int habitacionId = (int) table.getValueAt(selectedRow, 1);
            String tipoHabitacion = (String) table.getValueAt(selectedRow, 2);
            BigDecimal precioHabitacion = (BigDecimal) table.getValueAt(selectedRow, 3);
            String fechaInicio = (String) table.getValueAt(selectedRow, 4);
            String fechaFin = (String) table.getValueAt(selectedRow, 5);
            int precioHabitacionEntero = precioHabitacion.intValue();

            String mensaje = "       Detalles de la Reserva:\n\n" +
                    "   ID de Reserva: " + reservaId + "\n" + "\n" +
                    "   ID de Habitacion: " + habitacionId + "\n" + "\n" +
                    "   Tipo de Habitacion: " + tipoHabitacion + "\n"  + "\n" +
                    "   Precio a pagar: " + precioHabitacionEntero + "\n"  + "\n" +
                    "   Fecha de Inicio: " + fechaInicio + "\n"  + "\n" +
                    "   Fecha de Fin: " + fechaFin;

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reserva");

            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(fileToSave)) {
                    writer.println(mensaje);
                    JOptionPane.showMessageDialog(frame, "Reserva guardada como texto en: " + fileToSave.getAbsolutePath());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al guardar la reserva como texto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void abrirPanelSegunBoton() {
        btnReservaciones.addActionListener(e -> mostrarHabitacionesParaReservar());
    }

    public void establecerNombreUsuario(String nombreUsuario) {
        lblBienvenida.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblBienvenida.setText(" ! Bienvenido, " + nombreUsuario + " !");
    }

    public void cerrarsecion() {
        btnCerrarSecion.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Estas seguro que quieres cerrar la sesion?",
                    "Confirmar cierre de sesion", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Sesion cerrada con exito.");
                frame.dispose();
                new InterfazLogin();
            }
        });
    }

    public InterfazClientes(int idUsuario) {
        this.idUsuario = idUsuario;
        realizarReserva();
        editarMiReserva();
        imprimirReserva();
        frame();
        componentes();
        abrirPanelSegunBoton();
        cerrarsecion();
    }
}