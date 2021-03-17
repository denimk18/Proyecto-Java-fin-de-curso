package Ventanas.Administrativos;

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

import Operaciones.OperacionesAdministrativos;
import Operaciones.OperacionesEnfermeras;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Visitas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtDni;

	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE VISITANTE", "DNI", "FECHA VISITA", "PACIENTE VISITADO" };
	DefaultTableModel dtm;
	JTable table;
	OperacionesAdministrativos operaciones = new OperacionesAdministrativos();
	JComboBox comboBox_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Visitas dialog = new Visitas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Visitas() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 572, 578);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 31, 536, 417);
		contentPanel.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Registro visita", null, panel, null);
		panel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(256, 50, 176, 20);
		panel.add(comboBox);
		llenaComboBox(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione paciente:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(30, 51, 193, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNombreYApellidos = new JLabel("Nombre y apellidos visitante:");
		lblNombreYApellidos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombreYApellidos.setBounds(30, 110, 193, 17);
		panel.add(lblNombreYApellidos);
		

		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(256, 219, 176, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		panel.add(txtFecha);
		
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
		txtNombre.setBounds(256, 109, 176, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		evitarPegar(txtNombre);
		
		JLabel lblFechaDeVisita = new JLabel("Fecha de visita:");
		lblFechaDeVisita.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaDeVisita.setBounds(30, 222, 193, 17);
		panel.add(lblFechaDeVisita);
		
		JLabel lblDninie = new JLabel("DNI/NIE:");
		lblDninie.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDninie.setBounds(30, 164, 193, 17);
		panel.add(lblDninie);
		
		txtDni = new JTextField();
		txtDni.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtDni.getText();
				if (Caracteres.length() >= 9) {
					e.consume();
				}

			}
		});
		txtDni.setColumns(10);
		txtDni.setBounds(256, 163, 176, 20);
		panel.add(txtDni);
		evitarPegar(txtDni);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.insertaVisita(txtNombre, txtDni, fecha, comboBox);
				}
			}
		});
		btnGuardar.setBounds(256, 299, 176, 23);
		panel.add(btnGuardar);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				vaciaComboBox(comboBox_1);
				llenaComboBox(comboBox_1);
				LimpiarJTable();
				operaciones.listadoVisitas(dtm);
			}
		});
		tabbedPane.addTab("Consulta visitas", null, panel_1, null);
		panel_1.setLayout(null);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(81, 23, 198, 20);
		panel_1.add(comboBox_1);
		llenaComboBox(comboBox_1);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimpiarJTable();
				operaciones.consultaVisita(comboBox_1, dtm);
			}
		});
		btnConsultar.setBounds(334, 22, 89, 23);
		panel_1.add(btnConsultar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 72, 511, 306);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table = new JTable(dtm);
		table.setPreferredScrollableViewportSize(new Dimension(100, 70));
		operaciones.listadoVisitas(dtm);

		JScrollPane scrollPane2 = new JScrollPane(table);
		scrollPane2.setBounds(0, 0, 511, 306);
		panel_2.add(scrollPane2);

		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();
				operaciones.listadoVisitas(dtm);
			}
		});
		btnListado.setBounds(412, 475, 134, 23);
		contentPanel.add(btnListado);
		
		JButton button = new JButton("Volver");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		button.setBounds(245, 475, 134, 23);
		contentPanel.add(button);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(Visitas.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 556, 539);
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
	            DefaultTableModel modelo=(DefaultTableModel) table.getModel();
	            int filas=table.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	
	
	public void vaciaComboBox(JComboBox cmb) {
		cmb.removeAllItems();
		cmb.addItem("Seleccione paciente");
	}
	
	
	/**
	 * Evita pegar en un textfield
	 *
	 * @param campo Textfield
	 */
	public static void evitarPegar(JTextField campo) {
	 
	    InputMap map2 = campo.getInputMap(JTextField.WHEN_FOCUSED);
	    map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	 
	}
}
