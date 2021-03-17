package Ventanas.Administrativos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesAdministrativos;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class RecepcionPedidos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private JTextField txtUnidades;
	OperacionesAdministrativos operaciones = new OperacionesAdministrativos();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecepcionPedidos dialog = new RecepcionPedidos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecepcionPedidos() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 423, 366);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(20, 262, 158, 23);
		contentPanel.add(btnVolver);
		
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(216, 180, 143, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		contentPanel.add(txtFecha);
		
		JComboBox cmbNombre = new JComboBox();
		cmbNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unidadesPedidas(cmbNombre);
			}
		});
		cmbNombre.setBounds(216, 64, 150, 20);
		contentPanel.add(cmbNombre);
		llenaComboBox(cmbNombre);
		
		JButton btnRecepcionar = new JButton("Recepcionar material");
		btnRecepcionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.recepcionaOficina(cmbNombre, txtUnidades, fecha);
					cmbNombre.removeAllItems();
					llenaComboBox(cmbNombre);
				}
			}
		});
		btnRecepcionar.setBounds(213, 262, 165, 23);
		contentPanel.add(btnRecepcionar);
		{
			
		}
		
		JLabel lblNombre = new JLabel("Nombre material:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(46, 62, 113, 23);
		contentPanel.add(lblNombre);
		
		JLabel lblUnidades = new JLabel("Unidades:");
		lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUnidades.setBounds(46, 117, 113, 23);
		contentPanel.add(lblUnidades);
		
		txtUnidades = new JTextField();
		txtUnidades.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtUnidades.getText();
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
		txtUnidades.setColumns(10);
		txtUnidades.setBounds(213, 119, 150, 20);
		contentPanel.add(txtUnidades);
		evitarPegar(txtUnidades);
		
		JLabel lblFechaLlegada = new JLabel("Fecha llegada:");
		lblFechaLlegada.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaLlegada.setBounds(46, 177, 113, 23);
		contentPanel.add(lblFechaLlegada);
	
	
		
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(RecepcionPedidos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 407, 333);
			contentPanel.add(lblNewLabel);
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

	
	/**
	 * Método para llenar comboBox con los materiales que se han pedido
	 * */
	
	
	public void llenaComboBox(JComboBox cmb) {
		cmb.addItem("Seleccione material");
		
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre from pedidos_oficina");
			while(rs.next()) {
				String s = rs.getString(1);
				cmb.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lo utilizo para que cuando se seleccione un material del comboBox en el textField que corresponde a las unidades
	 * aparezca la cantidad pedida.
	 * @param cmb
	 */
	public void unidadesPedidas(JComboBox cmb) {
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select unidades from pedidos_oficina where nombre = '" + cmb.getSelectedItem() + "'");
			while(rs.next()) {
				txtUnidades.setText(String.valueOf(rs.getInt(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
