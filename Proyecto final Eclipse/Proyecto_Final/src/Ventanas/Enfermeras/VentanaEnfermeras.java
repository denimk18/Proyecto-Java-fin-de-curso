package Ventanas.Enfermeras;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Ventanas.AreaPersonal;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaEnfermeras extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaEnfermeras dialog = new VentanaEnfermeras();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaEnfermeras() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaEnfermeras.class.getResource("/Imagenes/icons8-enfermera-40.png")));
		setBounds(100, 100, 453, 496);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnFarmacos = new JButton("F\u00E1rmacos administrados");
			btnFarmacos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					FarmacosAdministrados dialog = new FarmacosAdministrados();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnFarmacos.setBounds(65, 37, 306, 35);
			contentPanel.add(btnFarmacos);
		}
		{
			JButton btnConsulta = new JButton("Consulta f\u00E1rmacos");
			btnConsulta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					ConsutaFarmacos dialog = new ConsutaFarmacos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnConsulta.setBounds(65, 100, 306, 35);
			contentPanel.add(btnConsulta);
		}
		{
			JButton btnIncidencias = new JButton("Incidencias paciente");
			btnIncidencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					IncidenciaPaciente dialog = new IncidenciaPaciente();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnIncidencias.setBounds(65, 176, 306, 35);
			contentPanel.add(btnIncidencias);
		}
		{
			JButton btnConsultasIncidencias = new JButton("Consultas incidencias");
			btnConsultasIncidencias.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					ConsultaIncidencias dialog = new ConsultaIncidencias();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnConsultasIncidencias.setBounds(65, 252, 306, 35);
			contentPanel.add(btnConsultasIncidencias);
		}
		{
			JButton btnreaPersonal = new JButton("\u00C1rea personal");
			btnreaPersonal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AreaPersonal dialog = new AreaPersonal();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					
				}
			});
			btnreaPersonal.setBounds(65, 395, 306, 35);
			contentPanel.add(btnreaPersonal);
		}
		
		JButton btnSolicitud = new JButton("Solicitud f\u00E1rmacos");
		btnSolicitud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				SolicitudMedicamentos dialog = new SolicitudMedicamentos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnSolicitud.setBounds(65, 327, 306, 35);
		contentPanel.add(btnSolicitud);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(VentanaEnfermeras.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 437, 480);
			contentPanel.add(lblNewLabel);
		}
	}
}
