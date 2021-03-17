package Ventanas.Medicos;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Tratamientos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCantidad;
	private JTextField txtCod;
	private JTextArea textArea;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private OperacionesMedicos operaciones = new OperacionesMedicos();
	private JTable tabla;
	private Object[][] datos;
	private String[] nombreColumnas = { "CÓDIGO", "PACIENTE",  "FÁRMACO" , "UNIDADES", "DIAGNÓSTICO", "FECHA"};
	private DefaultTableModel dtm;
	private JComboBox comboBox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Tratamientos dialog = new Tratamientos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Tratamientos() {
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
		setBounds(100, 100, 576, 634);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 540, 159, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		contentPanel.add(btnVolver);
		
	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 540, 518);
		contentPanel.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Di\u00E1gnostico", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblDiagnostico = new JLabel("Diagn\u00F3stico:");
		lblDiagnostico.setBounds(20, 250, 83, 25);
		panel.add(lblDiagnostico);
		lblDiagnostico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(20, 199, 83, 25);
		panel.add(lblFecha);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
	
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(20, 145, 83, 25);
		panel.add(lblCantidad);
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(126, 148, 160, 20);
		panel.add(txtCantidad);
		txtCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtCantidad.getText();
				if (Caracteres.length() > 2) {
				e.consume();
				}

				char caracter = e.getKeyChar();
				// comprobar si los caracteres
				// son digitos o backspace
				if ((caracter < '0' || caracter > '9') && caracter != '\b') {
					e.consume();
				}
			}
		});
		txtCantidad.setColumns(10);
		evitarPegar(txtCantidad);
		
		JLabel lblMedicacin = new JLabel("Medicaci\u00F3n:");
		lblMedicacin.setBounds(20, 93, 83, 25);
		panel.add(lblMedicacin);
		lblMedicacin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JComboBox cmbMedicacion = new JComboBox();
		cmbMedicacion.setBounds(127, 96, 159, 20);
		panel.add(cmbMedicacion);
		llenaMedicacion(cmbMedicacion);
		
		JLabel lblNewLabel_1 = new JLabel("Paciente:");
		lblNewLabel_1.setBounds(20, 45, 63, 25);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JComboBox cmbPaciente = new JComboBox();
		cmbPaciente.setBounds(127, 48, 159, 20);
		panel.add(cmbPaciente);
		llenaComboBox(cmbPaciente);
		
		JLabel lblCdigoPaciente = new JLabel("C\u00F3digo diagn\u00F3stico:");
		lblCdigoPaciente.setBounds(10, 9, 119, 25);
		panel.add(lblCdigoPaciente);
		lblCdigoPaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtCod = new JTextField();
		txtCod.setBounds(127, 12, 160, 20);
		panel.add(txtCod);
		txtCod.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtCod.getText();
				if (Caracteres.length() > 4) {
				e.consume();
				}

				char caracter = e.getKeyChar();
				// comprobar si los caracteres
				// son digitos o backspace
				if ((caracter < '0' || caracter > '9') && caracter != '\b') {
					e.consume();
				}
			}
		});
		txtCod.setColumns(10);
		evitarPegar(txtCod);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = textArea.getText();
				if (Caracteres.length() >= 500) {
				e.consume();
			}}
		});
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		evitarPegarTextArea(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 286, 497, 125);
		panel.add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(126, 199, 166, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		panel.add(txtFecha);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(367, 540, 159, 23);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.insertaTratamiento(txtCod, txtCantidad, cmbPaciente, cmbMedicacion, textArea, fecha);
				}
			}
		});
		contentPanel.add(btnGuardar);
		
		JPanel panel_1 = new JPanel();
		panel_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				comboBox.removeAllItems();
				llenaComboBox(comboBox);
				LimpiarJTable(tabla);
				operaciones.listadoTratamientos(dtm);
			}
		});
		tabbedPane.addTab("Consulta di\u00E1gnostico", null, panel_1, null);
		
		dtm = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		panel_1.setLayout(null);
		
		tabla = new JTable(dtm);		
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setPreferredScrollableViewportSize(new Dimension(533, 414));	
		
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane2 = new JScrollPane(tabla);
		scrollPane2.setBounds(0, 65, 535, 425);
		panel_1.add(scrollPane2);
		
		comboBox = new JComboBox();
		comboBox.setBounds(73, 24, 177, 20);
		panel_1.add(comboBox);
		llenaComboBox(comboBox);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex() != 0 && comboBox.getSelectedIndex() != -1) {
					LimpiarJTable(tabla);
					operaciones.listadoTratamientosPaciente(dtm, comboBox);
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione paciente");
				}
			}
		});
		btnConsultar.setBounds(283, 23, 89, 23);
		panel_1.add(btnConsultar);
		
		JButton btnLista = new JButton("Listado");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimpiarJTable(tabla);
				operaciones.listadoTratamientos(dtm);
			}
		});
		btnLista.setBounds(395, 23, 89, 23);
		panel_1.add(btnLista);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 564, 595);
		lblNewLabel.setIcon(new ImageIcon(Tratamientos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		contentPanel.add(lblNewLabel);
		
		operaciones.listadoTratamientos(dtm);

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
	
}
