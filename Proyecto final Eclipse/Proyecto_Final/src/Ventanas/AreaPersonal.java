package Ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesAreaPersonal;
import Operaciones.OperacionesMedicos;
import Ventanas.Medicos.VentanaMedicos;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AreaPersonal extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTextField txtCod;
	private static JTextField txtNombre;
	private static JTextField txtDNI;
	private static JTextField txtOficio;
	private static JTextField txtDireccion;
	private static JTextField txtTitulacion;
	private static JTextArea textArea;
	static OperacionesAreaPersonal area = new OperacionesAreaPersonal(); 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AreaPersonal dialog = new AreaPersonal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AreaPersonal() {
		setModal(true);
		setBounds(100, 100, 556, 675);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo trabajador:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(27, 28, 111, 21);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNombreYApellidos = new JLabel("Nombre y apellidos:");
		lblNombreYApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombreYApellidos.setBounds(27, 71, 111, 21);
		contentPanel.add(lblNombreYApellidos);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDni.setBounds(27, 121, 111, 21);
		contentPanel.add(lblDni);
		
		JLabel lblOficio = new JLabel("Oficio:");
		lblOficio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOficio.setBounds(27, 168, 111, 21);
		contentPanel.add(lblOficio);
		
		JLabel lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDireccin.setBounds(27, 215, 111, 21);
		contentPanel.add(lblDireccin);
		
		JLabel lblTitulacin = new JLabel("Titulaci\u00F3n:");
		lblTitulacin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTitulacin.setBounds(27, 266, 111, 21);
		contentPanel.add(lblTitulacin);
		
		txtCod = new JTextField();
		txtCod.setEditable(false);
		txtCod.setBounds(157, 29, 86, 20);
		contentPanel.add(txtCod);
		txtCod.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(157, 72, 270, 20);
		contentPanel.add(txtNombre);
		
		txtDNI = new JTextField();
		txtDNI.setEditable(false);
		txtDNI.setColumns(10);
		txtDNI.setBounds(157, 122, 270, 20);
		contentPanel.add(txtDNI);
		
		txtOficio = new JTextField();
		txtOficio.setEditable(false);
		txtOficio.setColumns(10);
		txtOficio.setBounds(157, 169, 270, 20);
		contentPanel.add(txtOficio);
		
		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(157, 216, 270, 20);
		contentPanel.add(txtDireccion);
		
		txtTitulacion = new JTextField();
		txtTitulacion.setEditable(false);
		txtTitulacion.setColumns(10);
		txtTitulacion.setBounds(157, 267, 270, 20);
		contentPanel.add(txtTitulacion);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(new Color(0, 0, 255));
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 348, 490, 186);
		contentPanel.add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("Cambiar contrase\u00F1a");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				CambiarContraseña dialog = new CambiarContraseña();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);				
			}
		});
		btnNewButton.setBounds(331, 549, 186, 31);
		contentPanel.add(btnNewButton);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnVolver.setBounds(27, 549, 186, 31);
		contentPanel.add(btnVolver);
		
		JLabel lblNewLabel_2 = new JLabel("Cursos:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(28, 316, 78, 21);
		contentPanel.add(lblNewLabel_2);
		
				
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AreaPersonal.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 541, 636);
		contentPanel.add(lblNewLabel);
		
		area.areaPersonal(txtCod, txtNombre, txtDNI, txtOficio, txtDireccion, txtTitulacion, textArea);
	}
}
