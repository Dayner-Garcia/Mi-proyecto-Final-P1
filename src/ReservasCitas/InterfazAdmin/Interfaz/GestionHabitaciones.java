package ReservasCitas.InterfazAdmin.Interfaz;

import ReservasCitas.ConnectionDB.ConsultasBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class GestionHabitaciones {
    private final JPanel panelprincipal;
    private final JButton agregarH;
    private final JButton editarH;
    private final JButton eliminarH;
    private final JButton filtrarHPorTipo;
    private final JFrame frame;

    public GestionHabitaciones(JPanel panelprincipal, JFrame frame, JButton agregarH, JButton editarH, JButton eliminarH, JButton filtrarHPorTipo) {
        this.panelprincipal = panelprincipal;
        this.frame = frame;
        this.agregarH = agregarH;
        this.editarH = editarH;
        this.eliminarH = eliminarH;
        this.filtrarHPorTipo = filtrarHPorTipo;
        accionListenerdeHabitaciones();
    }

    public void mostrarHabitaciones() {
        panelprincipal.removeAll();
        JLabel titulo = new JLabel("Gestion de habitaciones");
        titulo.setBounds(360, 40, 500, 28);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        panelprincipal.add(titulo);

        JLabel lblHabitaciones = new JLabel("Lista de habitaciones");
        lblHabitaciones.setBounds(100, 100, 300, 24);
        lblHabitaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panelprincipal.add(lblHabitaciones);

        Vector<Vector<Object>> data = ConsultasBD.obtenerTodasLasHabitaciones();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Tipo");
        columnNames.add("Precio");
        columnNames.add("Descripcion");
        columnNames.add("Disponible");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 130, 550, 300);
        panelprincipal.add(scrollPane);

        colocarBotones();
    }

    public void filtrarHabitacionesPorTipo(String tipoHabitacion) {
        panelprincipal.removeAll();
        JLabel titulo = new JLabel("Gestion de habitaciones - Tipo: " + tipoHabitacion);
        titulo.setBounds(300, 40, 500, 25);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        panelprincipal.add(titulo);
        JLabel lblHabitaciones = new JLabel("Registro de Habitaciones");
        lblHabitaciones.setBounds(100, 100, 300, 20);
        lblHabitaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panelprincipal.add(lblHabitaciones);

        Vector<Vector<Object>> data = ConsultasBD.filtrarHabitacionesPorTipo(tipoHabitacion);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Tipo");
        columnNames.add("Precio");
        columnNames.add("Descripcion");
        columnNames.add("Disponible");

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 130, 550, 300);
        panelprincipal.add(scrollPane);

        colocarBotones();
    }

    private void colocarBotones() {
        filtrarHPorTipo.setBounds(700, 150, 100, 20);
        filtrarHPorTipo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        filtrarHPorTipo.setContentAreaFilled(false);
        panelprincipal.add(filtrarHPorTipo);
        agregarH.setBounds(700, 230, 100, 20);
        agregarH.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        agregarH.setContentAreaFilled(false);
        panelprincipal.add(agregarH);
        editarH.setBounds(700, 310, 100, 20);
        editarH.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        editarH.setContentAreaFilled(false);
        panelprincipal.add(editarH);
        eliminarH.setBounds(700, 390, 100, 20);
        eliminarH.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        eliminarH.setContentAreaFilled(false);
        panelprincipal.add(eliminarH);
        panelprincipal.revalidate();
        panelprincipal.repaint();
    }

    private void accionListenerdeHabitaciones() {
        filtrarHPorTipo.addActionListener(e -> {
            String tipoHabitacion = JOptionPane.showInputDialog(frame, "Ingrese el tipo de habitacion a filtrar (2 personas, 3 personas, 4 personas):");
            if (tipoHabitacion != null && !tipoHabitacion.isEmpty()) {
                filtrarHabitacionesPorTipo(tipoHabitacion);
            } else {
                JOptionPane.showMessageDialog(frame, "Debe ingresar un tipo de habitacion valido.");
            }
        });

        agregarH.addActionListener(e -> {
            JTextField tipoField = new JTextField();
            JTextField precioField = new JTextField();
            JTextField descripcionField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Tipo de habitacion (2,3,4 Personas)."));
            panel.add(tipoField);
            panel.add(new JLabel("Precio:"));
            panel.add(precioField);
            panel.add(new JLabel("Descripcion:"));
            panel.add(descripcionField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Habitacion",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String tipo = tipoField.getText();
                double precio = Double.parseDouble(precioField.getText());
                String descripcion = descripcionField.getText();

                boolean success = ConsultasBD.agregarHabitacion(tipo, precio, descripcion);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Habitacion agregada exitosamente.");
                    mostrarHabitaciones();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al agregar la habitacion.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editarH.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField tipoField = new JTextField();
            JTextField precioField = new JTextField();
            JTextField descripcionField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("ID de habitacion a editar:"));
            panel.add(idField);
            panel.add(new JLabel("Nuevo tipo de habitacion:"));
            panel.add(tipoField);
            panel.add(new JLabel("Nuevo precio:"));
            panel.add(precioField);
            panel.add(new JLabel("Nueva descripcion:"));
            panel.add(descripcionField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Habitacion",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int id = Integer.parseInt(idField.getText());
                String tipo = tipoField.getText();
                double precio = Double.parseDouble(precioField.getText());
                String descripcion = descripcionField.getText();

                boolean success = ConsultasBD.editarHabitacion(id, tipo, precio, descripcion);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Habitacion editada exitosamente.");
                    mostrarHabitaciones();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error al editar la habitacion.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarH.addActionListener(e -> {
            String idHabitacionStr = JOptionPane.showInputDialog(frame, "Ingrese el ID de la habitacion a eliminar:");
            if (idHabitacionStr != null && !idHabitacionStr.isEmpty()) {
                try {
                    int idHabitacion = Integer.parseInt(idHabitacionStr);
                    boolean success = ConsultasBD.eliminarHabitacion(idHabitacion);

                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Habitacion eliminada exitosamente.");
                        mostrarHabitaciones();
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se encontro una habitacion con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Debe ingresar un ID valido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Debe ingresar un ID de habitacion.");
            }
        });
    }
}