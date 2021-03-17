package Ventanas;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesContrase�as;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RecuperarContrase�a extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField pswContrase�a;
	private JPasswordField pswRepiteContrase�a;
	private JTextField txtUsuario = VentanaAutentificacion.txtUsuario;
	private OperacionesContrase�as contrase�a = new OperacionesContrase�as();
	private int seguridad;
	private JLabel lblSeguridad;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecuperarContrase�a dialog = new RecuperarContrase�a();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecuperarContrase�a() {
		setModal(true);
		setBounds(100, 100, 450, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		/**
		 * Cuando el usuario introduce texto en el JPasswordField hace visible un label donde va mostrando el porcentaje
		 * de seguridad que tiene una contrase�a. Obtiene este porcentaje mediante el m�todo seguridadClave que se encuentra
		 * en la clase OperacionesContrase�as. Retorona un int que equivale a dicho porcentaje.
		 */
		pswContrase�a = new JPasswordField();
		pswContrase�a.setToolTipText("La contrase\u00F1a tiene que contener por lo menos\r\n una may\u00FAscula, min\u00FAscula, un n\u00FAmero y m\u00EDnimo 8 car\u00E1cteres");
		pswContrase�a.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = pswContrase�a.getText();
				if (Caracteres.length() >= 20) {
				e.consume();
				}
				lblSeguridad.setVisible(true);
				seguridad = contrase�a.seguridadClave(pswContrase�a.getText());
				lblSeguridad.setText("La contrase�a es " + seguridad + " % segura");
			}
		});
		pswContrase�a.setBounds(159, 37, 231, 20);
		contentPanel.add(pswContrase�a);
		evitarPegar(pswContrase�a);
		
		pswRepiteContrase�a = new JPasswordField();
		pswRepiteContrase�a.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = pswRepiteContrase�a.getText();
				if (Caracteres.length() >= 20) {
				e.consume();
				}
			}
		});
		pswRepiteContrase�a.setBounds(159, 84, 231, 20);
		contentPanel.add(pswRepiteContrase�a);
		evitarPegar(pswRepiteContrase�a);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a nueva:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(20, 39, 112, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblRepiteContrasea = new JLabel("Repite contrase\u00F1a:");
		lblRepiteContrasea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRepiteContrasea.setBounds(20, 87, 112, 14);
		contentPanel.add(lblRepiteContrasea);
		
		/**
		 * 
		 * Al darle al bot�n guardar se llama al m�todo cambioContrase�a de la clase OperacionesContrase�as pero primero
		 * comprueba si la coontrase�a es 100% segura. De no ser as� no deja que el usuario la cambie.
		 * Si todo est� bien, actualiza la contrase�a (encriptada) en la base de datos.
		 */
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(seguridad >= 100) {
					if(contrase�a.contrase�aOlvidada(pswContrase�a, pswRepiteContrase�a, txtUsuario)) {
						pswContrase�a.setText(""); pswRepiteContrase�a.setText(""); 
					}
				}else {
					JOptionPane.showMessageDialog(null, "La contrase�a tiene que ser 100% segura");
				}
				
			}
		});
		btnNewButton.setBounds(301, 153, 89, 23);
		contentPanel.add(btnNewButton);
		
		lblSeguridad = new JLabel("");
		lblSeguridad.setBounds(159, 123, 231, 14);
		lblSeguridad.setVisible(false);
		contentPanel.add(lblSeguridad);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RecuperarContrase�a.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 434, 261);
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
