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
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PreguntaSecreta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtSecreta;
	private JTextField txtRespuesta;
	private JTextField txtUsuario = VentanaAutentificacion.txtUsuario;
	private static OperacionesContraseñas contraseña = new OperacionesContraseñas();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PreguntaSecreta dialog = new PreguntaSecreta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("static-access")
	public PreguntaSecreta() {
		setModal(true);
		setBounds(100, 100, 498, 281);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel_1 = new JLabel("Pregunta secreta:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(25, 65, 127, 25);
			contentPanel.add(lblNewLabel_1);
		}
		
		txtSecreta = new JTextField();
		txtSecreta.setEditable(false);
		txtSecreta.setBounds(149, 69, 309, 20);
		contentPanel.add(txtSecreta);
		txtSecreta.setColumns(10);
		/**
		 * //Si hay un usuario introducido en el textField txtUsuario de la ventana
		 * autentificacion se coloca en txtSecreta la pregunta del usuario que
		 * corresponde. Para ello se utiliza el método preguntaSecreta de la clase
		 * OperacionesContraseñas
		 */
		txtSecreta.setText(contraseña.preguntaSecreta(txtUsuario));
		
		JLabel lblRespuesta = new JLabel("Respuesta:");
		lblRespuesta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRespuesta.setBounds(25, 126, 86, 25);
		contentPanel.add(lblRespuesta);
		
		txtRespuesta = new JTextField();
		txtRespuesta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtRespuesta.getText();
				if (Caracteres.length() >= 50) {
				e.consume();
				}
			}
		});
		txtRespuesta.setBounds(149, 130, 309, 20);
		contentPanel.add(txtRespuesta);
		txtRespuesta.setColumns(10);
		evitarPegar(txtRespuesta);
		
		JButton btnCambiar = new JButton("Cambiar contrase\u00F1a");
		btnCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contraseña.respuestaCorrecta(txtUsuario, txtRespuesta)) {
				setModal(false);
				RecuperarContraseña dialog = new RecuperarContraseña();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);			
				}
			}
		});
		btnCambiar.setBounds(293, 192, 163, 23);
		contentPanel.add(btnCambiar);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(PreguntaSecreta.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 482, 242);
			contentPanel.add(lblNewLabel);
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
