package Ventanas.Medicos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesMedicos;
import Ventanas.VentanaInicio;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Recetas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesMedicos operaciones = new OperacionesMedicos();
	private JTextArea textReceta;
	private JTable tabla;
	Object[][] datos;
	String[] nombreColumnas = { "PACIENTE", "MEDICAMENTO",  "FECHA RECETA" , "ADMINISTRACIÓN"};
	DefaultTableModel dtm;
	private JComboBox cmbPacienteConsulta;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Recetas dialog = new Recetas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("serial")
	public Recetas() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 575, 580);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 32, 539, 445);
		contentPanel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Nueva receta", null, panel_1, null);
		panel_1.setLayout(null);
		
		JComboBox cmbPaciente = new JComboBox();
		cmbPaciente.setBounds(20, 39, 181, 20);
		panel_1.add(cmbPaciente);
		llenaComboBox(cmbPaciente);

		
		JComboBox cmbMedicacion = new JComboBox();
		cmbMedicacion.setBounds(323, 39, 181, 20);
		panel_1.add(cmbMedicacion);
		llenaMedicacion(cmbMedicacion);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(124, 86, 95, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		panel_1.add(txtFecha);
		
		textReceta = new JTextArea();
		textReceta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = textReceta.getText();
				if (Caracteres.length() >= 70) {
					e.consume();
				}
			}
		});
		textReceta.setBounds(10, 104, 514, 267);
		//panel_1.add(textReceta);
		
		evitarPegarTextArea(textReceta);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 167, 484, 204);
		panel_1.add(scrollPane);
		scrollPane.setViewportView(textReceta);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.insertaReceta(cmbPaciente, cmbMedicacion, textReceta,fecha);
				}
			}
		});
		btnGuardar.setBounds(20, 383, 89, 23);
		panel_1.add(btnGuardar);
		
		JLabel lblNewLabel_2 = new JLabel("Administraci\u00F3n:");
		lblNewLabel_2.setBounds(20, 142, 89, 14);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Fecha receta:");
		lblNewLabel_3.setBounds(20, 92, 122, 14);
		panel_1.add(lblNewLabel_3);
		
		
		
		JPanel panel = new JPanel();
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cmbPacienteConsulta.removeAllItems();
				llenaComboBox(cmbPacienteConsulta);
				LimpiarJTable(tabla);
			}
		});
		tabbedPane.addTab("Consultas recetas", null, panel, null);
		panel.setLayout(null);
		
		cmbPacienteConsulta = new JComboBox();
		cmbPacienteConsulta.setBounds(189, 21, 197, 20);
		panel.add(cmbPacienteConsulta);
		llenaComboBox(cmbPacienteConsulta);
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable(tabla);
				operaciones.listadoRecetas(dtm, cmbPacienteConsulta);
			}
		});
		btnNewButton.setBounds(408, 20, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Paciente a consultar:");
		lblNewLabel.setBounds(21, 24, 158, 19);
		panel.add(lblNewLabel);
		
		dtm = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
	
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 60, 534, 357);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
	
		tabla = new JTable(dtm);		
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setPreferredScrollableViewportSize(new Dimension(533, 414));	
		
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane2 = new JScrollPane(tabla);
		scrollPane2.setBounds(0, 0, 533, 357);
		panel_2.add(scrollPane2);
		
		
		
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(10, 507, 89, 23);
		contentPanel.add(btnVolver);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Recetas.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel_1.setBounds(0, 0, 559, 541);
		contentPanel.add(lblNewLabel_1);
	}
	
	public void llenaComboBox(JComboBox cmb) {
		
		try {
			String a = "Seleccione paciente";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
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
	
	public void llenaMedicacion(JComboBox cmb) {
		try {
			String a = "Seleccione fármaco";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_medicamento, nombre from inventario_farmacia");
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
	
	public void LimpiarJTable(JTable table) {
		  try {
	            DefaultTableModel modelo=(DefaultTableModel) table.getModel();
	            int filas=table.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	
	
	/**
	 * Evita pegar en un textbox
	 *
	 * @param campo TextBox
	 */
	public static void evitarPegar(JTextField campo) {
	    InputMap map2 = campo.getInputMap(JTextField.WHEN_FOCUSED);
	    map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	 
	}
	
	
	/**
	 * Evita pegar en un textarea
	 *
	 * @param campo TextArea
	 */
	public static void evitarPegarTextArea(JTextArea campo) {
	 
	    InputMap map2 = campo.getInputMap(JTextArea.WHEN_FOCUSED);
	    map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	 
	}
}
