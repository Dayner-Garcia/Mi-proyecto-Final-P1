package ReservasCitas.InterfazAdmin.Interfaz;

import ReservasCitas.ConnectionDB.ConnectionDB;
import ReservasCitas.ConnectionDB.ConsultasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class GestionUsuarios {
    private final JFrame frame;
    private final JPanel panelprincipal;
    private final JButton agregarU;
    private final JButton buscarU;
    private final JButton editarU;
    private final JButton eliminarU;

    public GestionUsuarios(JFrame frame, JPanel panelprincipal, JButton agregarU, JButton buscarU, JButton editarU, JButton eliminarU) {
        this.frame = frame;
        this.panelprincipal = panelprincipal;
        this.agregarU = agregarU;
        this.buscarU = buscarU;
        this.editarU = editarU;
        this.eliminarU = eliminarU;

        accionesBotonesUsuarios();
    }
    public void mostrarUsuarios() {
        panelprincipal.removeAll();

        JLabel titulo = new JLabel("Gestion de Usuarios");
        titulo.setBounds(360, 40, 300, 28);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        panelprincipal.add(titulo);

        JLabel tituloC = new JLabel("Lista de usuarios");
        tituloC.setBounds(100, 100, 300, 24);
        tituloC.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        panelprincipal.add(tituloC);

        Vector<Vector<Object>> data = ConsultasBD.obtenerUsuarios();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Username");
        columnNames.add("Password");
        columnNames.add("Tipo Usuario");
        columnNames.add("Nombre");
        columnNames.add("Email");
        columnNames.add("Telefono");
        columnNames.add("Cedula");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 130, 600, 300);

        panelprincipal.add(scrollPane);
        agregarU.setBounds(730,160,140,20);
        agregarU.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        agregarU.setContentAreaFilled(false);
        panelprincipal.add(agregarU);
        buscarU.setBounds(750, 380, 100, 20);
        buscarU.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        buscarU.setContentAreaFilled(false);
        panelprincipal.add(buscarU);
        editarU.setBounds(750, 240, 100, 20);
        editarU.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        editarU.setContentAreaFilled(false);
        panelprincipal.add(editarU);
        eliminarU.setBounds(750, 310, 100, 20);
        eliminarU.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        eliminarU.setContentAreaFilled(false);
        panelprincipal.add(eliminarU);
        panelprincipal.revalidate();
        panelprincipal.repaint();
    }
    private void accionesBotonesUsuarios() {
        buscarU.addActionListener(e -> {
            String idUsuario = JOptionPane.showInputDialog(frame, "Ingrese el ID del usuario:");

            if (idUsuario != null && !idUsuario.isEmpty()) {
                try {
                    Connection con = ConnectionDB.getConnection();
                    String query = "SELECT * FROM Usuarios WHERE id = ?";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, Integer.parseInt(idUsuario));
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String mensaje = "ID: " + rs.getInt("id") + "\n" +
                                "Username: " + rs.getString("username") + "\n" +
                                "Password: " + rs.getString("password") + "\n" +
                                "Tipo Usuario: " + rs.getString("tipo_usuario") + "\n" +
                                "Nombre Completo: " + rs.getString("nombre_completo") + "\n" +
                                "Email: " + rs.getString("email") + "\n" +
                                "Telefono: " + rs.getString("telefono") + "\n" +
                                "Cedula: " + rs.getString("cedula");
                        JOptionPane.showMessageDialog(frame, mensaje, "Datos del Usuario", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontro un usuario con el ID especificado.", "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
                    }

                    rs.close();
                    pstmt.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al buscar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editarU.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField tipoUsuarioField = new JTextField();
            JTextField nombreCompletoField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField telefonoField = new JTextField();
            JTextField cedulaField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("ID de usuario a editar:"));
            panel.add(idField);
            panel.add(new JLabel("Nuevo username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Nuevo password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Nuevo tipo de usuario (Administrador o cliente)"));
            panel.add(tipoUsuarioField);
            panel.add(new JLabel("Nuevo nombre completo:"));
            panel.add(nombreCompletoField);
            panel.add(new JLabel("Nuevo email:"));
            panel.add(emailField);
            panel.add(new JLabel("Nuevo telefono:"));
            panel.add(telefonoField);
            panel.add(new JLabel("Nueva cedula:"));
            panel.add(cedulaField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Editar Usuario",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int id;
                try {
                    id = Integer.parseInt(idField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "El ID del usuario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String tipoUsuario = tipoUsuarioField.getText();
                String nombreCompleto = nombreCompletoField.getText();
                String email = emailField.getText();
                String telefono = telefonoField.getText();
                String cedula = cedulaField.getText();

                if (username.isEmpty() || password.isEmpty() || tipoUsuario.isEmpty() || nombreCompleto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    boolean success = ConsultasBD.editarUsuario(id, username, password, tipoUsuario, nombreCompleto, email, telefono, cedula);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Usuario editado exitosamente.");
                        mostrarUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontro un usuario con el ID ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al editar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarU.addActionListener(e -> {
            String idUsuario = JOptionPane.showInputDialog(frame, "Ingrese el ID del usuario a eliminar:");

            if (idUsuario != null && !idUsuario.isEmpty()) {
                int confirmacion = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        int id = Integer.parseInt(idUsuario);
                        boolean success = ConsultasBD.eliminarUsuario(id);
                        if (success) {
                            JOptionPane.showMessageDialog(frame, "Usuario eliminado correctamente.", "Eliminacion completada", JOptionPane.INFORMATION_MESSAGE);
                            mostrarUsuarios();
                        } else {
                            JOptionPane.showMessageDialog(frame, "No se encontro ningin usuario con ese ID.", "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "El ID del usuario debe ser un numero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame,
                                "Error al eliminar el usuario: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        agregarU.addActionListener(e -> {
            JTextField usernameField = new JTextField();
            JPasswordField passwordField = new JPasswordField();
            JTextField tipoUsuarioField = new JTextField();
            JTextField nombreCompletoField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField telefonoField = new JTextField();
            JTextField cedulaField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Tipo de Usuario: (Cliente) o (Administrador)"));
            panel.add(tipoUsuarioField);
            panel.add(new JLabel("Nombre Completo:"));
            panel.add(nombreCompletoField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Telefono:"));
            panel.add(telefonoField);
            panel.add(new JLabel("Cedula:"));
            panel.add(cedulaField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Usuario",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String tipoUsuario = tipoUsuarioField.getText();
                String nombreCompleto = nombreCompletoField.getText();
                String email = emailField.getText();
                String telefono = telefonoField.getText();
                String cedula = cedulaField.getText();

                if (username.isEmpty() || password.isEmpty() || tipoUsuario.isEmpty() || nombreCompleto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    boolean success = ConsultasBD.agregarUsuario(username, password, tipoUsuario, nombreCompleto, email, telefono, cedula);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
                        mostrarUsuarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}