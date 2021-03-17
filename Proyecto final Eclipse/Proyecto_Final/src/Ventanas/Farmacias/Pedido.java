package Ventanas.Farmacias;

import java.awt.BorderLayout;
import java.awt.Event;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesFarmacia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Calendar;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;


public class Pedido extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUnidades;
	private JTextField txtMedicación;
	OperacionesFarmacia operaciones = new OperacionesFarmacia();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Pedido dialog = new Pedido();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Pedido() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 346, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblMedicacin = new JLabel("Medicaci\u00F3n:");
		lblMedicacin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedicacin.setBounds(20, 36, 92, 20);
		contentPanel.add(lblMedicacin);
		
		JLabel lblUnidades = new JLabel("Unidades:");
		lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUnidades.setBounds(20, 93, 92, 20);
		contentPanel.add(lblUnidades);
		
		txtUnidades = new JTextField();
		txtUnidades.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtUnidades.getText();
				if (Caracteres.length() >= 5) {
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
		txtUnidades.setBounds(133, 94, 168, 20);
		contentPanel.add(txtUnidades);
		evitarPegar(txtUnidades);
		
		JLabel lblFechaPedido = new JLabel("Fecha pedido:");
		lblFechaPedido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaPedido.setBounds(20, 154, 92, 20);
		contentPanel.add(lblFechaPedido);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		txtFecha.setBounds(133, 154, 168, 20);
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
					operaciones.insertaPedido(txtMedicación, txtUnidades, fecha);
				}
				
			}
		});
		btnGuardar.setBounds(207, 237, 89, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(20, 237, 89, 23);
		contentPanel.add(btnVolver);
		
		
		
		txtMedicación = new JTextField();
		txtMedicación.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtMedicación.getText();
				if (Caracteres.length() >= 15) {
					e.consume();
				}

				
			}
		});
		txtMedicación.setColumns(10);
		txtMedicación.setBounds(133, 37, 168, 20);
		evitarPegar(txtMedicación);
		contentPanel.add(txtMedicación);
		

		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Pedido.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 330, 281);
		contentPanel.add(lblNewLabel);
		
		
	}
	
	
	/**
	 * Evita pegar en un textarea
	 * @param campo TextArea
	 */
	public static void evitarPegar(JTextField campo) {
	 
	    InputMap map2 = campo.getInputMap(JTextField.WHEN_FOCUSED);
	    map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	 
	}
}
