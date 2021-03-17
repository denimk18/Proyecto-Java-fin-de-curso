package Ventanas.Medicos;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class ConsultaModificacionPacientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtModifDir;
	private JTextField txtModifTel;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesMedicos operaciones = new OperacionesMedicos();
	private JTable table;
	private JTable table2;
	
	Object[][] datos;
	String[] nombreColumnas = { "COD PACIENTE", "NOMBRE", "DIRECCIÓN", "TELÉFONO URGENCIA", "FECHA ALTA" , "MEDICO"};
	DefaultTableModel dtm;
	DefaultTableModel dtm2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsultaModificacionPacientes dialog = new ConsultaModificacionPacientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsultaModificacionPacientes() {
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
		setBounds(100, 100, 574, 554);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(51, 102, 153));
		tabbedPane.setBounds(10, 11, 538, 442);
		contentPanel.add(tabbedPane);
		
		JPanel listadoPacientes = new JPanel();
		
		tabbedPane.addTab("Listado Pacientes", null, listadoPacientes, null);
		listadoPacientes.setLayout(null);
		
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(null);
		panelTabla.setBounds(0, 0, 533, 414);
		listadoPacientes.add(panelTabla);
		
		dtm = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table = new JTable(dtm);		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(533, 414));	
		
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 533, 414);
		panelTabla.add(scrollPane);
		
		JPanel unPaciente = new JPanel();
		tabbedPane.addTab("Consulta paciente", null, unPaciente, null);
		unPaciente.setLayout(null);
		
		operaciones.listadoPacientes(dtm); //Para mostrar el listado con todos los pacientes.
		
		JComboBox cmbConsulta = new JComboBox();
		cmbConsulta.setBounds(83, 11, 212, 20);
		unPaciente.add(cmbConsulta);
		llenaComboBox(cmbConsulta);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable(table2);
				operaciones.consultaPacienteTabla(cmbConsulta, dtm2);
			}
		});
		btnConsultar.setBounds(350, 10, 89, 23);
		unPaciente.add(btnConsultar);
		
		JPanel panelConsulta = new JPanel();
		panelConsulta.setBounds(0, 43, 533, 371);
		unPaciente.add(panelConsulta);
		panelConsulta.setLayout(null);
		
		dtm2 = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table2 = new JTable(dtm2);		
		table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table2.setPreferredScrollableViewportSize(new Dimension(533, 414));	
		
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(0, 0, 533, 414);
		panelConsulta.add(scrollPane2);
		
		
		
		JPanel modifPaciente = new JPanel();
		tabbedPane.addTab("Modificaci\u00F3n paciente", null, modifPaciente, null);
		modifPaciente.setLayout(null);
		
		txtModifDir = new JTextField();
		txtModifDir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtModifDir.getText();
				if (Caracteres.length() >= 40) {
				e.consume();
				}
			}
		});
		
		
		txtModifDir.setColumns(10);
		txtModifDir.setBounds(177, 103, 267, 20);
		modifPaciente.add(txtModifDir);
		evitarPegar(txtModifDir);
		
		JComboBox cmbMedicoModif = new JComboBox();
		cmbMedicoModif.setBounds(177, 254, 267, 20);
		modifPaciente.add(cmbMedicoModif);
		llenaComboBoxMedicos(cmbMedicoModif);
		
		JDateChooser txtModifFecha = new JDateChooser();
		txtModifFecha.setBounds(177, 199, 267, 20);
		txtModifFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtModifFecha.getDateEditor();
		editor.setEditable(false);
		modifPaciente.add(txtModifFecha);
		
		
		JComboBox cmbModificacion = new JComboBox();
		cmbModificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbModificacion.getSelectedIndex() != 0 && cmbModificacion.getSelectedIndex() != -1) {
					operaciones.consultaPaciente(cmbModificacion, txtModifDir, txtModifTel, editor,cmbMedicoModif);
				}
			}
		});
		cmbModificacion.setBounds(54, 38, 255, 20);
		modifPaciente.add(cmbModificacion);
		llenaComboBox(cmbModificacion);
		
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.modificaPaciente(cmbModificacion, txtModifDir, txtModifTel, fecha, cmbMedicoModif);

				}
				
				//Lo que hago aquí es refrescar la tabla donde está la información de todos los pacientes.
				//Cada vez que se modifica algún paciente, limpio la tabla de la pestaña Listado Pacientes y vuelvo a cargar la información,
				//de manera que siempre esté actualizada.
				LimpiarJTable(table);
				operaciones.listadoPacientes(dtm);
			}
		});
		btnModificar.setBounds(355, 37, 89, 23);
		modifPaciente.add(btnModificar);
		
		JLabel label = new JLabel("Direcci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(52, 105, 69, 14);
		modifPaciente.add(label);
		
		JLabel label_1 = new JLabel("Tel\u00E9fono urgencia:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(53, 149, 108, 20);
		modifPaciente.add(label_1);
		
		txtModifTel = new JTextField();
		txtModifTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtModifTel.getText();
				if (Caracteres.length() > 8) {
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
		txtModifTel.setColumns(10);
		txtModifTel.setBounds(177, 150, 267, 20);
		modifPaciente.add(txtModifTel);
		evitarPegar(txtModifTel);
		
		JLabel label_2 = new JLabel("Fecha de alta:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(53, 199, 108, 20);
		modifPaciente.add(label_2);
		
		JLabel label_3 = new JLabel("M\u00E9dico personal:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(53, 253, 108, 20);
		modifPaciente.add(label_3);
		
		
		
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(10, 470, 164, 23);
		contentPanel.add(btnVolver);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ConsultaModificacionPacientes.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 575, 515);
		contentPanel.add(lblNewLabel);
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
	
	public void llenaComboBoxMedicos(JComboBox cmb) {
		
		try {
			String a = "Seleccione médico";
			cmb.addItem(a);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_personal, nombre , apellidos from personal where oficio = 'MEDICO'");
			while(rs.next()) {
				String s = rs.getInt(1) + "/" + rs.getString(2) + " " + rs.getString(3);
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
}
