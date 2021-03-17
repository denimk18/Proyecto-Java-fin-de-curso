package Ventanas.Medicos;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Operaciones.OperacionesMedicos;
import Ventanas.VentanaInicio;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class AltaPacientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodPaciente;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesMedicos operaciones = new OperacionesMedicos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AltaPacientes dialog = new AltaPacientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AltaPacientes() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				setModal(false);
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 495, 479);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblCdigoPaciente = new JLabel("C\u00F3digo paciente:");
		lblCdigoPaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCdigoPaciente.setBounds(24, 38, 106, 16);
		contentPanel.add(lblCdigoPaciente);
		
		txtCodPaciente = new JTextField();
		txtCodPaciente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtCodPaciente.getText();
				if (Caracteres.length() >= 4) {
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
		txtCodPaciente.setBounds(157, 37, 63, 20);
		contentPanel.add(txtCodPaciente);
		txtCodPaciente.setColumns(10);
		evitarPegar(txtCodPaciente);
		
		JLabel lblNombre = new JLabel("Nombre y apellidos:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(24, 83, 132, 16);
		contentPanel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtNombre.getText();
				if (Caracteres.length() >= 25) {
				e.consume();
				}		
			}
		});
		txtNombre.setColumns(10);
		txtNombre.setBounds(158, 82, 282, 20);
		contentPanel.add(txtNombre);
		evitarPegar(txtNombre);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDireccin.setBounds(24, 129, 132, 16);
		contentPanel.add(lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtDireccion.getText();
				if (Caracteres.length() >= 40) {
				e.consume();
				}

			
			}
		});
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(157, 128, 282, 20);
		contentPanel.add(txtDireccion);
		evitarPegar(txtDireccion);
		
		JLabel lblTelfonoDeUrgencia = new JLabel("Tel\u00E9fono de urgencia:");
		lblTelfonoDeUrgencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelfonoDeUrgencia.setBounds(24, 182, 132, 16);
		contentPanel.add(lblTelfonoDeUrgencia);
		
		txtTelefono = new JTextField();
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtTelefono.getText();
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
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(157, 181, 282, 20);
		contentPanel.add(txtTelefono);
		evitarPegar(txtTelefono);
		
		JLabel lblFechaDeAlta = new JLabel("Fecha de alta:");
		lblFechaDeAlta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaDeAlta.setBounds(24, 238, 132, 16);
		contentPanel.add(lblFechaDeAlta);
		
		JLabel lblCdigoMdicoPersonal = new JLabel("M\u00E9dico personal:");
		lblCdigoMdicoPersonal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCdigoMdicoPersonal.setBounds(24, 298, 154, 16);
		contentPanel.add(lblCdigoMdicoPersonal);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(175, 297, 265, 20);
		contentPanel.add(comboBox);
		comboBox.setSelectedItem("Selecciona médico");
		llenaComboBox(comboBox);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(157, 234, 283, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		contentPanel.add(txtFecha);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.altaPacientes(txtCodPaciente, txtNombre, txtDireccion, txtTelefono, fecha, comboBox);
				}
			}
		});
		btnGuardar.setBounds(287, 380, 142, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}			
		});
		btnVolver.setBounds(24, 380, 142, 23);
		contentPanel.add(btnVolver);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AltaPacientes.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 479, 482);
		contentPanel.add(lblNewLabel);
	}
	
	/**
	 * LLena el comboBox con los datos del personal médico.
	 * @param cmb
	 */
	public void llenaComboBox(JComboBox cmb) {
		
		try {
			String a = "Seleccione médico";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_personal, nombre from personal where oficio = 'MEDICO'");
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
	 * Evita pegar en un textfield
	 * @param campo TextArea
	 */
	public static void evitarPegar(JTextField campo) {
	 
	    InputMap map2 = campo.getInputMap(JTextField.WHEN_FOCUSED);
	    map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	 
	}
}
