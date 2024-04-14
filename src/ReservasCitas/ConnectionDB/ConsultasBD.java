package ReservasCitas.ConnectionDB;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.Date;

public class ConsultasBD {
    // InterfazLogin
    public static int obtenerIdUsuario(String usuario) {
        int idUsuario = -1;
        try {
            Connection con = ConnectionDB.getConnection();

            String sql = "SELECT id FROM Usuarios WHERE username = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idUsuario = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idUsuario;
    }

    public static String verificarCredenciales(String usuario, String password) {
        String tipoUsuario = null;
        try {
            Connection con = ConnectionDB.getConnection();

            String sql = "SELECT tipo_usuario FROM Usuarios WHERE username = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tipoUsuario = rs.getString("tipo_usuario");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoUsuario;
    }

    //-------------------------------------------------------------------------------------------------------------------------
    //Interfaz registro
    public static boolean registrarUsuario(String nombreCompleto, String nombreUsuario, String password, String email, String telefono, String cedula) {
        boolean registroExitoso = false;
        try {
            Connection con = ConnectionDB.getConnection();
            String sql = "INSERT INTO Usuarios (username, password, tipo_usuario, nombre_completo, email, telefono, cedula) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, password);
            stmt.setString(3, "Cliente");
            stmt.setString(4, nombreCompleto);
            stmt.setString(5, email);
            stmt.setString(6, telefono);
            stmt.setString(7, cedula);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                registroExitoso = true;
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + ex.getMessage());
        }
        return registroExitoso;
    }

    // ------------------------------------------------------------------------------------------------------------------
    // InterfazLogin
    public static Vector<Vector<Object>> obtenerHabitacionesDisponibles() {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String consulta = "SELECT * FROM habitaciones WHERE disponible = true";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("tipo"));
                row.add(rs.getBigDecimal("precio"));
                row.add(rs.getString("descripcion"));
                data.add(row);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Vector<Vector<Object>> obtenerReservacionesCliente(int idUsuario) {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String consulta = "SELECT r.id, r.habitacion_id, h.tipo, h.precio, r.fecha_inicio, r.fecha_fin " +
                    "FROM reservas r " +
                    "INNER JOIN habitaciones h ON r.habitacion_id = h.id " +
                    "WHERE r.cliente_id = " + idUsuario;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(consulta);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getInt("habitacion_id"));
                row.add(rs.getString("tipo"));
                row.add(rs.getBigDecimal("precio"));
                row.add(rs.getString("fecha_inicio"));
                row.add(rs.getString("fecha_fin"));
                data.add(row);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean actualizarReserva(int reservaId, String nuevaFechaInicioStr) {
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement("UPDATE reservas SET fecha_inicio = ?, fecha_fin = ? WHERE id = ?")) {
            pstmt.setDate(1, java.sql.Date.valueOf(nuevaFechaInicioStr));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(nuevaFechaInicioStr));
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date nuevaFechaFin = calendar.getTime();

            pstmt.setDate(2, new java.sql.Date(nuevaFechaFin.getTime()));
            pstmt.setInt(3, reservaId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void realizarReserva(int idUsuario, int idHabitacion, Date fechaInicio, Date fechaFin) {
        String sql = "INSERT INTO Reservas (cliente_id, habitacion_id, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idHabitacion);
            stmt.setDate(3, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(4, new java.sql.Date(fechaFin.getTime()));

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void marcarHabitacionNoDisponible(int habitacionId) {
        try {
            Connection con = ConnectionDB.getConnection();

            String sql = "UPDATE Habitaciones SET disponible = false WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, habitacionId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("La habitacion se marco como no disponible correctamente.");
            } else {
                System.out.println("No se pudo marcar la habitacion como no disponible.");
            }

            stmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------
    // InterfazAdmin (metodos de las sub clases).
    // GestionHabitaciones
    public static Vector<Vector<Object>> obtenerTodasLasHabitaciones() {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "SELECT * FROM Habitaciones";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("tipo"));
                row.add(rs.getBigDecimal("precio"));
                row.add(rs.getString("descripcion"));
                row.add(rs.getBoolean("disponible"));
                data.add(row);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Vector<Vector<Object>> filtrarHabitacionesPorTipo(String tipoHabitacion) {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "SELECT * FROM Habitaciones WHERE tipo = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, tipoHabitacion);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("tipo"));
                row.add(rs.getBigDecimal("precio"));
                row.add(rs.getString("descripcion"));
                row.add(rs.getBoolean("disponible"));
                data.add(row);
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean agregarHabitacion(String tipo, double precio, String descripcion) {
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "INSERT INTO Habitaciones (tipo, precio, descripcion, disponible) VALUES (?, ?, ?, true)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, tipo);
            pstmt.setDouble(2, precio);
            pstmt.setString(3, descripcion);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean editarHabitacion(int id, String tipo, double precio, String descripcion) {
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "UPDATE Habitaciones SET tipo = ?, precio = ?, descripcion = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, tipo);
            pstmt.setDouble(2, precio);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, id);
            int rowsUpdated = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean eliminarHabitacion(int idHabitacion) {
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "DELETE FROM Habitaciones WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, idHabitacion);
            int rowsDeleted = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //GestionReservas
    public static Vector<Vector<Object>> obtenerTodasLasReservaciones() throws SQLException {
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT r.id AS 'ID Reserva', u.nombre_completo AS 'Nombre Cliente', h.tipo AS 'Tipo Habitacion', " +
                     "r.fecha_inicio AS 'Fecha de Inicio', r.fecha_fin AS 'Fecha de Fin' " +
                     "FROM Reservas r " +
                     "INNER JOIN Usuarios u ON r.cliente_id = u.id " +
                     "INNER JOIN Habitaciones h ON r.habitacion_id = h.id");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ID Reserva"));
                row.add(rs.getString("Nombre Cliente"));
                row.add(rs.getString("Tipo Habitacion"));
                row.add(rs.getDate("Fecha de Inicio"));
                row.add(rs.getDate("Fecha de Fin"));
                data.add(row);
            }
        }
        return data;
    }

    public static String buscarReservaPorID(int idReserva) throws SQLException {
        String mensaje = null;
        try (Connection con = ConnectionDB.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT id, cliente_id, habitacion_id, fecha_inicio, fecha_fin FROM Reservas WHERE id = ?")) {

            pstmt.setInt(1, idReserva); // Establecer el valor del parámetro
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mensaje = "ID de Reserva: " + rs.getInt("id") + "\n"
                            + "ID Cliente: " + rs.getInt("cliente_id") + "\n"
                            + "ID Habitación: " + rs.getInt("habitacion_id") + "\n"
                            + "Fecha de Inicio: " + rs.getDate("fecha_inicio") + "\n"
                            + "Fecha de Fin: " + rs.getDate("fecha_fin");
                }
            }
        }
        return mensaje;
    }

    public static void crearNuevaReserva(JFrame frame) throws SQLException {
        String idCliente = JOptionPane.showInputDialog(frame, "Ingrese el ID del cliente:");
        String idHabitacion = JOptionPane.showInputDialog(frame, "Ingrese el ID de la habitacion:");
        String fechaInicio = JOptionPane.showInputDialog(frame, "Ingrese la fecha de inicio (YYYY-MM-DD):");
        String fechaFin = JOptionPane.showInputDialog(frame, "Ingrese la fecha de fin (YYYY-MM-DD):");

        if (idCliente != null && !idCliente.isEmpty() &&
                idHabitacion != null && !idHabitacion.isEmpty() &&
                fechaInicio != null && !fechaInicio.isEmpty() &&
                fechaFin != null && !fechaFin.isEmpty()) {
            try (Connection con = ConnectionDB.getConnection();
                 PreparedStatement pstmt = con.prepareStatement("INSERT INTO Reservas (cliente_id, habitacion_id, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)")) {

                pstmt.setInt(1, Integer.parseInt(idCliente));
                pstmt.setInt(2, Integer.parseInt(idHabitacion));
                pstmt.setDate(3, java.sql.Date.valueOf(fechaInicio));
                pstmt.setDate(4, java.sql.Date.valueOf(fechaFin));

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(frame, "Reserva creada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "No se pudo crear la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void actualizarEstadoReserva(JFrame frame) throws SQLException {
        String idReserva = JOptionPane.showInputDialog(frame, "Ingrese el ID de la reserva que desea editar:");

        if (idReserva != null && !idReserva.isEmpty()) {
            String nuevoEstado = JOptionPane.showInputDialog(frame, "Ingrese el nuevo estado de disponibilidad (true/false):");
            if (nuevoEstado != null && !nuevoEstado.isEmpty()) {
                try (Connection con = ConnectionDB.getConnection();
                     PreparedStatement pstmt = con.prepareStatement("UPDATE Reservas r INNER JOIN Habitaciones h ON r.habitacion_id = h.id SET h.disponible = ? WHERE r.id = ?")) {

                    pstmt.setBoolean(1, Boolean.parseBoolean(nuevoEstado));
                    pstmt.setInt(2, Integer.parseInt(idReserva));

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(frame, "Estado de disponibilidad actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontró ninguna reserva con el ID especificado.", "Reserva no encontrada", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un nuevo estado de disponibilidad válido.", "Estado de disponibilidad vacío", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese el ID de la reserva que desea editar.", "ID de reserva vacío", JOptionPane.WARNING_MESSAGE);
        }
    }

    //GestionClientes
    public static Vector<Vector<Object>> obtenerUsuarios() {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection con = ConnectionDB.getConnection();
            String query = "SELECT * FROM Usuarios";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("username"));
                row.add(rs.getString("password"));
                row.add(rs.getString("tipo_usuario"));
                row.add(rs.getString("nombre_completo"));
                row.add(rs.getString("email"));
                row.add(rs.getString("telefono"));
                row.add(rs.getString("cedula"));
                data.add(row);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean editarUsuario(int id, String username, String password, String tipoUsuario, String nombreCompleto, String email, String telefono, String cedula) throws SQLException {
        try (Connection con = ConnectionDB.getConnection()) {
            String query = "UPDATE Usuarios SET username=?, password=?, tipo_usuario=?, nombre_completo=?, email=?, telefono=?, cedula=? WHERE id=?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, tipoUsuario);
                pstmt.setString(4, nombreCompleto);
                pstmt.setString(5, email);
                pstmt.setString(6, telefono);
                pstmt.setString(7, cedula);
                pstmt.setInt(8, id);
                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0;
            }
        }
    }

    public static boolean eliminarUsuario(int id) throws SQLException {
        try (Connection con = ConnectionDB.getConnection()) {
            String query = "DELETE FROM Usuarios WHERE id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, id);
                int rowsDeleted = pstmt.executeUpdate();
                return rowsDeleted > 0;
            }
        }
    }

    public static boolean agregarUsuario(String username, String password, String tipoUsuario, String nombreCompleto, String email, String telefono, String cedula) throws SQLException {
        try (Connection con = ConnectionDB.getConnection()) {
            String query = "INSERT INTO Usuarios (username, password, tipo_usuario, nombre_completo, email, telefono, cedula) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, tipoUsuario);
                pstmt.setString(4, nombreCompleto);
                pstmt.setString(5, email);
                pstmt.setString(6, telefono);
                pstmt.setString(7, cedula);
                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0;
            }
        }
    }

}