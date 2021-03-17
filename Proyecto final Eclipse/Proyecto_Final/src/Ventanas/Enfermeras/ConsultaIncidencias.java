package Ventanas.Enfermeras;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesEnfermeras;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConsultaIncidencias extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesEnfermeras operaciones = new OperacionesEnfermeras();
	Object[][] datos;
	String[] nombreColumnas = { "COD INCIDENCIA", "PACIENTE", "MEDICAMENTO", "CANTIDAD", "FECHA", "CAUSA", "CURA",
			"COMUNICADO FAMILIARES" };
	DefaultTableModel dtm;
	DefaultTableModel dtm2;
	JTable table;
	JTable table2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsultaIncidencias dialog = new ConsultaIncidencias();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsultaIncidencias() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 1047, 698);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(153, 204, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		dtm = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 1011, 602);
		contentPanel.add(tabbedPane_1);
		table = new JTable(dtm);

		table.setPreferredScrollableViewportSize(new Dimension(100, 70));

		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);
		tabbedPane_1.addTab("Listado incidencias", null, scrollPane, null);

		/**
		 * El panel llamado panel contiene un comboBox, un boton y otro panel que
		 * contiene un JTable En ese JTable pintamos todas las incidencias del paciente seleccionado
		 */

		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Consulta incidencias paciente", null, panel, null);
		panel.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(292, 23, 193, 20);
		panel.add(comboBox);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();
				operaciones.incidenciasPaciente(dtm2, comboBox);
			}
		});
		btnConsultar.setBounds(594, 22, 89, 23);
		panel.add(btnConsultar);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 56, 1006, 518);
		panel.add(panel_1);

		dtm2 = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		panel_1.setLayout(null);
		table2 = new JTable(dtm2);
		table2.setPreferredScrollableViewportSize(new Dimension(100, 70));

		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(0, 0, 1006, 518);
		panel_1.add(scrollPane2);

		llenaComboBox(comboBox);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);

			}
		});
		btnVolver.setBounds(10, 624, 89, 23);
		contentPanel.add(btnVolver);
		
		operaciones.listaIncidencias(dtm);
	}
	
	/**
	 * LLena el comboBox con los pacientes
	 * @param cmb
	 */
	public void llenaComboBox(JComboBox cmb) {
		
		try {
			String a = "Seleccione paciente";
			cmb.addItem(a);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_paciente, nombre from pacientes");
			while(rs.next()) {
				String s = rs.getInt(1) + "/" + rs.getString(2);
				cmb.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lo utilizo para limpiar el JTable de la pestaña Consulta fármacos paciente. Cada vez que se selecciona un paciente y se
	 * presiona consultar se limpia la tabla mediante este método y luego se muestran los datos.
	 */
	
	public void LimpiarJTable() {
		  try {
	            DefaultTableModel modelo=(DefaultTableModel) table2.getModel();
	            int filas=table2.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
}