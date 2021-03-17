package Ventanas.Medicos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Ventanas.AreaPersonal;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaMedicos extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaMedicos dialog = new VentanaMedicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaMedicos() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaMedicos.class.getResource("/Imagenes/icons8-estetoscopio-48.png")));
		setBounds(100, 100, 456, 493);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnBaja = new JButton("Baja pacientes");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				dispose();
				BajaPacientes dialog = new BajaPacientes();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnBaja.setBounds(81, 96, 279, 35);
		contentPanel.add(btnBaja);
		
		JButton btnAlta = new JButton("Alta pacientes");
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setModal(false);
				dispose();
				AltaPacientes dialog = new AltaPacientes();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnAlta.setBounds(81, 35, 279, 37);
		contentPanel.add(btnAlta);
		
		JButton btnConsulModif = new JButton("Consulta y modificaci\u00F3n pacientes");
		btnConsulModif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				dispose();
				ConsultaModificacionPacientes dialog = new ConsultaModificacionPacientes();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnConsulModif.setBounds(81, 169, 279, 35);
		contentPanel.add(btnConsulModif);
		
		JButton btnTratamiento = new JButton("Diagn\u00F3stico y tratamiento");
		btnTratamiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				dispose();
				Tratamientos dialog = new Tratamientos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnTratamiento.setBounds(81, 234, 279, 35);
		contentPanel.add(btnTratamiento);
		
		JButton btnreaPersonal = new JButton("\u00C1rea personal");
		btnreaPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				AreaPersonal dialog = new AreaPersonal();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		btnreaPersonal.setBounds(81, 374, 279, 35);
		contentPanel.add(btnreaPersonal);
		
		JButton btnRecetas = new JButton("Recetas");
		btnRecetas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setModal(false);
				dispose();
				Recetas dialog = new Recetas();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				dispose();
			}
		});
		btnRecetas.setBounds(81, 305, 279, 35);
		contentPanel.add(btnRecetas);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaMedicos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 439, 455);
		contentPanel.add(lblNewLabel);
	}
}
