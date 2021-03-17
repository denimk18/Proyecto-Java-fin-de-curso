package Ventanas.Enfermeras;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.JTablaDiagnóstico;
import Operaciones.JTablaRecetas;
import Operaciones.OperacionesEnfermeras;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class FarmacosAdministrados extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private static OperacionesEnfermeras operaciones = new OperacionesEnfermeras();
	public static int codPaciente; //Se utiliza para pasarlo a la ventana ConsultaDiagnostico. Lo utiliza para mostrar
	// los diagnósticos (y tratamientos) que se han prescrito a un paciente
	private JTextField txtCantidad;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FarmacosAdministrados dialog = new FarmacosAdministrados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FarmacosAdministrados() {
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
		setBounds(100, 100, 530, 268);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		/**
		 * Al seleccionar un elemento del comboBox de pacientes automáticamente en la variable estática codPaciente de esta clase
		 * se guarda el código de paciente seleccionado. Para ello se utiliza el método codigoPaciente(JComboBox cmb)
		 * Posteriormente esta variable estática es utilizada en la ventana ConsultaDiagnostico para mostrar los diagnósticos
		 * del paciente seleccionado
		 */
		JComboBox cmbPacientes = new JComboBox();
		llenaComboBox(cmbPacientes);

		cmbPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cmbPacientes.getSelectedIndex() != 0 && cmbPacientes.getSelectedIndex() != -1) {
					codPaciente = codigoPaciente(cmbPacientes);
					
				}
			}
		});
		cmbPacientes.setBounds(16, 32, 219, 20);
		contentPanel.add(cmbPacientes);
		
		JComboBox cmbMedicacion = new JComboBox();
		cmbMedicacion.setBounds(256, 32, 210, 20);
		contentPanel.add(cmbMedicacion);
		llenaMedicacion(cmbMedicacion);

		JLabel lblNewLabel_1 = new JLabel("Fecha  administraci\u00F3n:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(16, 118, 150, 14);
		contentPanel.add(lblNewLabel_1);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(130, 112, 105, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		contentPanel.add(txtFecha);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(txtFecha.equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.insertaFarmacosAdministrados(cmbPacientes, cmbMedicacion,fecha, txtCantidad);
				}
			}
		});
		btnGuardar.setBounds(20, 177, 99, 23);
		contentPanel.add(btnGuardar);
		
		/**
		 * Al presionar este botón se abre un JTable que muestra los diagnósticos de cada paciente.
		 * Primero comprueba que haya seleccionado un elemento seleccionado en el comboBox . Después comprueba
		 * si el paciente seleccionado tiene diagnósticos. Si los tiene abre la tabla con los datos, si no los tiene
		 * muestra el mensaje
		 */
		JButton btnNewButton = new JButton("Consultar diagn\u00F3stico");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			 if(cmbPacientes.getSelectedIndex() != 0 && cmbPacientes.getSelectedIndex() != -1) {
					if(tieneDiagnostico(codigoPaciente(cmbPacientes))) {
						JTablaDiagnóstico frame = new JTablaDiagnóstico();
						frame.pack();
						frame.setVisible(true);		
				
						JTablaDiagnóstico tabla = new JTablaDiagnóstico();
						tabla.LLenarJTableConsulta(codPaciente);
						
					}else {
						JOptionPane.showMessageDialog(null,"No hay datos");
					}
			 }else {
				 JOptionPane.showMessageDialog(null,"Seleccione un paciente");
			 }
			
				
				
			}
		});
		btnNewButton.setBounds(161, 177, 167, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(361, 177, 105, 23);
		contentPanel.add(btnVolver);
		
		/**
		 * Al pinchar en este botón se muestran las recetas del paciente seleccionado en el comboBox.
		 * Primero comprueba que haya paciente seleccionado del comboBox, si lo hay comprueba si tiene recetas.
		 * Si tiene recetas pinta la tabla, de lo contrario muestra el mensaje
		 */
		JButton btnConsultarRecetas = new JButton("Consultar recetas");
		btnConsultarRecetas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cmbPacientes.getSelectedIndex() != 0 && cmbPacientes.getSelectedIndex() != -1) {
					if(tieneRecetas(codigoPaciente(cmbPacientes))) {
						JTablaRecetas frame = new JTablaRecetas();
						frame.pack();
						frame.setVisible(true);		
				
						JTablaRecetas tabla = new JTablaRecetas();
						tabla.LLenarJTableConsulta(codPaciente);
					}else {
						JOptionPane.showMessageDialog(null,"No hay datos");
					}
				}else {
					JOptionPane.showMessageDialog(null,"Seleccione paciente");
				}
			}
		});
		btnConsultarRecetas.setBounds(299, 97, 167, 23);
		contentPanel.add(btnConsultarRecetas);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCantidad.setBounds(16, 71, 150, 14);
		contentPanel.add(lblCantidad);
		
		txtCantidad = new JTextField();
		txtCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtCantidad.getText();
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
		txtCantidad.setColumns(10);
		txtCantidad.setBounds(130, 68, 105, 20);
		contentPanel.add(txtCantidad);
		evitarPegar(txtCantidad);
		
	
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(FarmacosAdministrados.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 514, 229);
			contentPanel.add(lblNewLabel);
		}
	}
	
	/**
	 * LLena el comboBox con los pacientes
	 * @param cmb
	 */
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
	
	/**
	 * LLena el comboBox con los medicamentos
	 * @param cmb
	 */
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
	 * Recibe un objeto comboBox , el cual está relleno de datos del paciente y
	 * devuelve un entero que corresponde al código del paciente seleccionado.
	 * 
	 * @param cmb
	 * @return int codigoPaciente
	 */
	public int codigoPaciente(JComboBox cmb) {
		int codigo = 0;
		String resultado = cmb.getSelectedItem().toString();
		String[] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);
		return codigo;
	}
	
	
	/**
	 * Comprueba si el paciente seleccionado tiene algún diagnóstico asignado. (Por ej si tiene amigdalitis le han
	 * dado amoxicilina y eso es lo que le tiene que administrar la enfermera)
	 * @param codPaciente
	 * @return tiene, indica si tiene diagnóstico o no
	 */
	public boolean tieneDiagnostico(int codPaciente) {
		boolean tiene = false;
		String s = "select * from diagnostico_paciente where cod_paciente = " + codPaciente;
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery(s);
			while(rs.next()) {
				tiene = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tiene;
	}
	
	public boolean tieneRecetas(int codPaciente) {
		boolean tiene = false;
		String s = "select * from recetas where cod_paciente = " + codPaciente;
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery(s);
			while(rs.next()) {
				tiene = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tiene;
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
