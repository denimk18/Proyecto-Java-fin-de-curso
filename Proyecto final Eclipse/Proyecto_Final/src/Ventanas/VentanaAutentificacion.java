package Ventanas;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesContraseñas;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaAutentificacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JTextField txtUsuario;
	private JPasswordField passwordField;
	private static OperacionesContraseñas contraseña = new OperacionesContraseñas();
	private static VentanaAutentificacion dialog;
	public static String usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new VentanaAutentificacion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaAutentificacion() {
		setModal(true);
		setBounds(100, 100, 475, 269);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		/**
		 * Al presionar este botón se llama al método iniciaSesion de la clase OperacionesContraseñas
		 * Si el inicio se ha hecho bien (devuelve un booleano) se llama al método abreVentana de la misma clase.
		 * Y es ahí cuando se abre la ventana que corresponde según el oficio del trabajador
		 */
		JButton btnInicio = new JButton("Iniciar sesi\u00F3n");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contraseña.iniciaSesion(txtUsuario, passwordField)) {
					usuario = txtUsuario.getText();
					setModal(false);
					dispose(); //para cerrar esta ventana cuando se abre la ventana propia del oficio
					contraseña.abreVentana(txtUsuario);
				

				}
			}
		});
		btnInicio.setBounds(212, 157, 151, 23);
		contentPanel.add(btnInicio);
		
		/**
		 * Por si el usuario ha olvidado su contraseña. Se comprueba que se haya introducido un usuario
		 * y si lo hay se lleva a la pantalla PreguntaSecreta
		 */
		JButton btnOlvidada = new JButton("\u00BFContrase\u00F1a olvidada?");
		btnOlvidada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Si hay un usuario introducido y existe se abre la ventana para contestar la pregunta secreta y poder cambiar la contraseña
				if(txtUsuario.getText().length() != 0) {
					if(contraseña.existeUsuario(txtUsuario.getText())){
						setModal(false);
						PreguntaSecreta dialog = new PreguntaSecreta();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "El usuario introducido no existe.");
						txtUsuario.setText("");
					}
				
				}else {
					JOptionPane.showMessageDialog(null, "Introduzca usuario");
				}
			}
		});
		btnOlvidada.setBounds(10, 196, 185, 23);
		contentPanel.add(btnOlvidada);
		btnOlvidada.setOpaque(false);
		btnOlvidada.setContentAreaFilled(false);
		btnOlvidada.setBorderPainted(false);
		
		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtUsuario.getText();
				if (Caracteres.length() >= 15) {
				e.consume();
				}
			}
		});
		txtUsuario.setBounds(137, 59, 226, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);
		evitarPegar(txtUsuario);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = passwordField.getText();
				if (Caracteres.length() >= 20) {
				e.consume();
				}
			}
		});
		passwordField.setBounds(133, 109, 230, 20);
		contentPanel.add(passwordField);
		evitarPegar(passwordField);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblContrasea.setBounds(38, 107, 101, 20);
		contentPanel.add(lblContrasea);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(38, 57, 78, 20);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaAutentificacion.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 459, 230);
		contentPanel.add(lblNewLabel);
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
