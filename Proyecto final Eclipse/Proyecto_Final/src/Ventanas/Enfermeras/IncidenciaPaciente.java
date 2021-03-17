package Ventanas.Enfermeras;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesEnfermeras;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class IncidenciaPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private static OperacionesEnfermeras operaciones = new OperacionesEnfermeras();
	private JTextField txtIncidencia;
	private JTextArea textCausa;
	private JTextArea textCura;
	private JTextArea textFamiliares;
	private JTextField txtCantidad;
	private JComboBox cmbPaciente;
	private JComboBox cmbMedicacion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			IncidenciaPaciente dialog = new IncidenciaPaciente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public IncidenciaPaciente() {
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
		setBounds(100, 100, 574, 685);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		cmbPaciente = new JComboBox();
		cmbPaciente.setBounds(25, 51, 192, 20);
		contentPanel.add(cmbPaciente);
		llenaComboBox(cmbPaciente);
		
		cmbMedicacion = new JComboBox();
		cmbMedicacion.setBounds(293, 51, 192, 20);
		contentPanel.add(cmbMedicacion);
		llenaMedicacion(cmbMedicacion);
		
		JLabel lblNewLabel_1 = new JLabel("Paciente");
		lblNewLabel_1.setBounds(25, 26, 63, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblMedicacin = new JLabel("Medicaci\u00F3n");
		lblMedicacin.setBounds(293, 26, 69, 14);
		contentPanel.add(lblMedicacin);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(378, 106, 107, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		contentPanel.add(txtFecha);
		
		
		
		txtIncidencia = new JTextField();
		txtIncidencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtIncidencia.getText();
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
		txtIncidencia.setBounds(131, 106, 86, 20);
		contentPanel.add(txtIncidencia);
		txtIncidencia.setColumns(10);
		evitarPegar(txtIncidencia);
		
		JLabel lblCausa = new JLabel("Causa");
		lblCausa.setBounds(25, 196, 107, 14);
		contentPanel.add(lblCausa);
		
		textCausa = new JTextArea();
		textCausa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = textCausa.getText();
				if (Caracteres.length() >= 500) {
					e.consume();
				}

			
			}
		});
		textCausa.setBounds(25, 192, 513, 81);
		evitarPegarTextArea(textCausa);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 221, 513, 81);
		contentPanel.add(scrollPane);
		scrollPane.setViewportView(textCausa);
		
		
		JLabel lblCura = new JLabel("Cura");
		lblCura.setBounds(25, 329, 107, 14);
		contentPanel.add(lblCura);
		
		textCura = new JTextArea();
		textCura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = textCura.getText();
				if (Caracteres.length() >= 500) {
					e.consume();
				}

			}
		});
		textCura.setBounds(25, 323, 513, 81);
		evitarPegarTextArea(textCura);
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(25, 354, 513, 81);
		contentPanel.add(scrollPane2);
		scrollPane2.setViewportView(textCura);
		
		JLabel lblComunicadoFamiliares = new JLabel("Comunicado familiares");
		lblComunicadoFamiliares.setBounds(25, 460, 182, 14);
		contentPanel.add(lblComunicadoFamiliares);
		
		textFamiliares = new JTextArea();
		textFamiliares.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = textFamiliares.getText();
				if (Caracteres.length() >= 500) {
					e.consume();
				}

			}
		});
		textFamiliares.setBounds(25, 459, 513, 81);
		evitarPegarTextArea(textFamiliares);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(25, 485, 513, 81);
		contentPanel.add(scrollPane3);
		scrollPane3.setViewportView(textFamiliares);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Seleccione fecha");
				}else {
					String fecha = editor.getText();
					if(operaciones.insertaIncidencia(cmbPaciente, cmbMedicacion, txtIncidencia, textCausa, textCura, textFamiliares, txtCantidad, fecha)) {
						limpiaCampos();
					}
				}
				
			}
		});
		btnGuardar.setBounds(446, 612, 89, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(335, 612, 89, 23);
		contentPanel.add(btnVolver);
		
		JLabel lblCdigoIncidencia = new JLabel("C\u00F3digo incidencia:");
		lblCdigoIncidencia.setBounds(25, 109, 107, 14);
		contentPanel.add(lblCdigoIncidencia);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(282, 109, 69, 14);
		contentPanel.add(lblFecha);
		
		txtCantidad = new JTextField();
		txtCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtCantidad.getText();
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
		txtCantidad.setColumns(10);
		txtCantidad.setBounds(378, 156, 107, 20);
		contentPanel.add(txtCantidad);
		evitarPegar(txtCantidad);
		
		JLabel lblCantidadFrmaco = new JLabel("Cantidad f\u00E1rmaco:");
		lblCantidadFrmaco.setBounds(269, 162, 107, 14);
		contentPanel.add(lblCantidadFrmaco);
		
	
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(IncidenciaPaciente.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 564, 646);
		contentPanel.add(lblNewLabel);
	}
	
	
	public void llenaComboBox(JComboBox cmb) {

		try {
			String a = "Seleccione paciente";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_paciente, nombre from pacientes");
			while (rs.next()) {
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
			while (rs.next()) {
				String s = rs.getInt(1) + "/" + rs.getString(2);
				cmb.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void limpiaCampos() {
		cmbPaciente.setSelectedIndex(0);
		cmbMedicacion.setSelectedIndex(0);
		txtIncidencia.setText("");
		txtCantidad.setText("");
		textCura.setText("");
		textFamiliares.setText("");
		textCausa.setText("");
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
