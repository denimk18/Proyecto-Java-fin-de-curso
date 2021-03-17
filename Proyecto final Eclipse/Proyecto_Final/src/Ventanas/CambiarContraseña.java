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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JInternalFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CambiarContraseña extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField pswAnterior;
	private JPasswordField pswNueva;
	private JPasswordField pswRepite;
	private static OperacionesContraseñas contraseña = new OperacionesContraseñas();
	private JLabel lblSeguridad;
	private int seguridad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CambiarContraseña dialog = new CambiarContraseña();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CambiarContraseña() {
		setModal(true);
		setBounds(100, 100, 387, 319);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a anterior:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(29, 37, 123, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblContraseaNueva = new JLabel("Contrase\u00F1a nueva:");
		lblContraseaNueva.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblContraseaNueva.setBounds(29, 79, 123, 15);
		contentPanel.add(lblContraseaNueva);
		
		pswAnterior = new JPasswordField();
		pswAnterior.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = pswAnterior.getText();
				if (Caracteres.length() >= 20) {
					e.consume();
				}
			}
		});
		pswAnterior.setBounds(166, 35, 161, 20);
		contentPanel.add(pswAnterior);
		evitarPegar(pswAnterior);
		
		
		pswNueva = new JPasswordField();
		pswNueva.setToolTipText("La contrase\u00F1a tiene que contener por lo menos una may\u00FAscula, min\u00FAscula, un n\u00FAmero y m\u00EDnimo 8 car\u00E1cteres");
		
		
		/**
		 * Cuando el usuario escriba texto en el JPasswordField se hará visible el label que indique que % de seguridad
		 * tiene la contrasña introducida.
		 * La variable seguridad es un int que guarda el valor que retorna el método seguridadClave()
		 * Lo utilizo en el botón guardar, de manera que si no es >100 no deje cambiar la contraseña. Es decir, si la contraseña
		 * no es por lo menos un 100% segura no dejará al usuario actualizarla.
		 */
		pswNueva.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = pswNueva.getText();
				if (Caracteres.length() >= 20) {
					e.consume();
				}
				
				lblSeguridad.enable(true);
				lblSeguridad.setVisible(true);
				seguridad = contraseña.seguridadClave(pswNueva.getText());
				lblSeguridad.setText("La contraseña es " + seguridad + " %segura.");
			}
		});
		pswNueva.setBounds(166, 77, 161, 20);
		contentPanel.add(pswNueva);
		evitarPegar(pswNueva);
		
		pswRepite = new JPasswordField();
		pswRepite.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = pswRepite.getText();
				if (Caracteres.length() >= 20) {
					e.consume();
				}
			}
		});
		pswRepite.setBounds(166, 125, 161, 20);
		contentPanel.add(pswRepite);
		evitarPegar(pswRepite);
		
		JLabel lblRepiteContrasea = new JLabel("Repite contrase\u00F1a:");
		lblRepiteContrasea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRepiteContrasea.setBounds(29, 127, 123, 15);
		contentPanel.add(lblRepiteContrasea);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seguridad >=100) {
					if(contraseña.cambioContraseña(pswAnterior,pswNueva, pswRepite, VentanaAutentificacion.txtUsuario)) {
						pswNueva.setText("");  pswRepite.setText("");  pswAnterior.setText(""); lblSeguridad.setVisible(false);
					}
				}else {
					JOptionPane.showMessageDialog(null,"La contraseña debe de ser por lo menos 100% segura");
				}
			
			}
		});
		btnGuardar.setBounds(202, 207, 123, 23);
		contentPanel.add(btnGuardar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				dispose();
			}
		});
		btnVolver.setBounds(29, 207, 123, 23);
		contentPanel.add(btnVolver);
		
	    lblSeguridad = new JLabel("");
		lblSeguridad.setBounds(127, 166, 200, 15);
		lblSeguridad.enable(false);
		lblSeguridad.setVisible(false);
		contentPanel.add(lblSeguridad);
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CambiarContraseña.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 371, 280);
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
