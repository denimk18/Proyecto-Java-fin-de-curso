package Ventanas.Administrativos;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesAdministrativos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class RecepcionPedidosFarmacia extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtCod;
	private JTextField txtUnidades;
	OperacionesAdministrativos operaciones = new OperacionesAdministrativos();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecepcionPedidosFarmacia dialog = new RecepcionPedidosFarmacia();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecepcionPedidosFarmacia() {
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
		setBounds(100, 100, 454, 442);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNombreMedicamento = new JLabel("Nombre medicamento:");
			lblNombreMedicamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNombreMedicamento.setBounds(29, 99, 151, 21);
			contentPanel.add(lblNombreMedicamento);
			
			JDateChooser txtFecha = new JDateChooser();
			txtFecha.setBounds(205, 233, 177, 20);
			txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
			JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
			editor.setEditable(false);
			contentPanel.add(txtFecha);
		
			txtNombre = new JTextField();
			txtNombre.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String Caracteres = txtNombre.getText();
					if (Caracteres.length() > 15) {
					e.consume();
					}				
				}
			});
			txtNombre.setBounds(205, 100, 177, 20);
			contentPanel.add(txtNombre);
			txtNombre.setColumns(10);
		
			JLabel lblCdigoMedicamento = new JLabel("C\u00F3digo medicamento");
			lblCdigoMedicamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblCdigoMedicamento.setBounds(29, 37, 151, 21);
			contentPanel.add(lblCdigoMedicamento);
	
			txtCod = new JTextField();
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
			txtCod.setBounds(205, 38, 177, 20);
			contentPanel.add(txtCod);
			evitarPegar(txtCod);
		
			JLabel lblUnidades = new JLabel("Unidades:");
			lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblUnidades.setBounds(29, 167, 151, 21);
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
			txtUnidades.setBounds(205, 168, 177, 20);
			contentPanel.add(txtUnidades);
			evitarPegar(txtUnidades);
		
			JLabel lblFechaLlegada = new JLabel("Fecha llegada:");
			lblFechaLlegada.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblFechaLlegada.setBounds(29, 233, 151, 21);
			contentPanel.add(lblFechaLlegada);
		
			
			JButton btnNewButton = new JButton("Recepcionar pedido");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(editor.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Seleccione fecha");
					}else {
						String fecha = editor.getText();
						operaciones.recepcionaFarmacia(txtCod, txtNombre, txtUnidades, fecha);
					}
				}
			});
			btnNewButton.setBounds(220, 311, 177, 23);
			contentPanel.add(btnNewButton);
		
			JButton btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					VentanaAdministrativos dialog = new VentanaAdministrativos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnVolver.setBounds(18, 311, 177, 23);
			contentPanel.add(btnVolver);
		}
		
		
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(RecepcionPedidosFarmacia.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 438, 403);
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
}
