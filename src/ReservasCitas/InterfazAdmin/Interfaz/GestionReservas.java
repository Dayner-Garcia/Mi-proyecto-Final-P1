package ReservasCitas.InterfazAdmin.Interfaz;

import ReservasCitas.ConnectionDB.ConnectionDB;
import ReservasCitas.ConnectionDB.ConsultasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class GestionReservas {
    private final JPanel panel;
    private final JFrame frame;
    private final JButton buscarR;
    private final JButton crearR;
    private final JButton estadoR;
    private final JButton eliminarR;

    public GestionReservas(JFrame frame, JPanel panel, JButton buscarR, JButton crearR, JButton estadoR, JButton eliminarR) {
        this.frame = frame;
        this.panel = panel;
        this.buscarR = buscarR;
        this.crearR = crearR;
        this.estadoR = estadoR;
        this.eliminarR = eliminarR;
        accionesBotonesReservaciones();
    }

    public void mostrarReservaciones() {
        panel.removeAll();
        JLabel titulo = new JLabel("Gestion de Reservaciones");
        titulo.setBounds(360, 40, 300, 28);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        panel.add(titulo);
        JLabel tituloC = new JLabel("Lista de Reservaciones");
        tituloC.setBounds(100, 100, 300, 24);
        tituloC.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        panel.add(tituloC);

        try {
            Vector<Vector<Object>> data = ConsultasBD.obtenerTodasLasReservaciones();

            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID Reserva");
            columnNames.add("Nombre Cliente");
            columnNames.add("Tipo Habitacion");
            columnNames.add("Fecha de Inicio");
            columnNames.add("Fecha de Fin");

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(100, 130, 550, 300);
            panel.add(scrollPane);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar las reservaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        colocarBotones();
    }

    private void colocarBotones() {
        buscarR.setBounds(700, 155, 175, 20);
        buscarR.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        buscarR.setContentAreaFilled(false);
        panel.add(buscarR);
        crearR.setBounds(700, 230, 175, 20);
        crearR.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        crearR.setContentAreaFilled(false);
        panel.add(crearR);
        estadoR.setBounds(700, 310, 175, 20);
        estadoR.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        estadoR.setContentAreaFilled(false);
        panel.add(estadoR);
        eliminarR.setBounds(700, 390, 175, 20);
        eliminarR.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        eliminarR.setContentAreaFilled(false);
        panel.add(eliminarR);
        panel.revalidate();
        panel.repaint();
    }

    private void accionesBotonesReservaciones() {
        buscarR.addActionListener(e -> {
            String idReservaStr = JOptionPane.showInputDialog(frame, "Ingrese el ID de la reserva que desea buscar:");
            if (idReservaStr != null && !idReservaStr.isEmpty()) {
                try {
                    int idReserva = Integer.parseInt(idReservaStr);
                    String mensaje = ConsultasBD.buscarReservaPorID(idReserva);
                    if (mensaje != null) {
                        JOptionPane.showMessageDialog(frame, mensaje);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontró ninguna reserva con el ID especificado.", "Reserva no encontrada", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al buscar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese el ID de la reserva que desea buscar.", "ID de reserva vacío", JOptionPane.WARNING_MESSAGE);
            }
        });


        crearR.addActionListener(e -> {
            try {
                ConsultasBD.crearNuevaReserva(frame);
                mostrarReservaciones();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al crear la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        estadoR.addActionListener(e -> {
            try {
                ConsultasBD.actualizarEstadoReserva(frame);
                mostrarReservaciones();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al actualizar el estado de la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        eliminarR.addActionListener(e -> {
            String idReserva = JOptionPane.showInputDialog(frame, "Ingrese el ID de la reserva que desea eliminar:");

            if (idReserva != null && !idReserva.isEmpty()) {
                try {
                    Connection con = ConnectionDB.getConnection();
                    String habitacionQuery = "SELECT habitacion_id FROM Reservas WHERE id = ?";
                    PreparedStatement pstmtHabitacion = con.prepareStatement(habitacionQuery);
                    pstmtHabitacion.setInt(1, Integer.parseInt(idReserva));
                    ResultSet rsHabitacion = pstmtHabitacion.executeQuery();

                    int habitacionId = 0;
                    if (rsHabitacion.next()) {
                        habitacionId = rsHabitacion.getInt("habitacion_id");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontro ninguna reserva con el ID especificado.", "Reserva no encontrada", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    pstmtHabitacion.close();
                    rsHabitacion.close();

                    String deleteQuery = "DELETE FROM Reservas WHERE id = ?";
                    PreparedStatement pstmtDelete = con.prepareStatement(deleteQuery);
                    pstmtDelete.setInt(1, Integer.parseInt(idReserva));

                    int rowsDeleted = pstmtDelete.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(frame, "Reserva eliminada correctamente.");

                        String updateHabitacionQuery = "UPDATE Habitaciones SET disponible = true WHERE id = ?";
                        PreparedStatement pstmtUpdateHabitacion = con.prepareStatement(updateHabitacionQuery);
                        pstmtUpdateHabitacion.setInt(1, habitacionId);
                        pstmtUpdateHabitacion.executeUpdate();

                        JOptionPane.showMessageDialog(frame, "Estado de la habitacion restablecido.");
                        mostrarReservaciones();
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontro ninguna reserva con el ID especificado.", "Reserva no encontrada", JOptionPane.WARNING_MESSAGE);
                    }
                    pstmtDelete.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al eliminar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese el ID de la reserva que desea eliminar.", "ID de reserva vacio", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
