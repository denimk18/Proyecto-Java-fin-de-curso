package Ventanas.Medicos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Operaciones.OperacionesMedicos;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BajaPacientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesMedicos operaciones = new OperacionesMedicos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BajaPacientes dialog = new BajaPacientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BajaPacientes() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaMedicos dialog = new VentanaMedicos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 475, 231);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPacienteADar = new JLabel("Paciente a dar de baja:");
			lblPacienteADar.setBounds(41, 52, 137, 35);
			contentPanel.add(lblPacienteADar);
		}
		
		
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(176, 59, 256, 20);
			contentPanel.add(comboBox);
			llenaComboBox(comboBox);
		
		
		{
			JButton btnNewButton = new JButton("Baja");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea dar de baja al paciente?", "", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						operaciones.bajaPaciente(comboBox);
						llenaComboBox(comboBox);
					}
					
				}
			});
			btnNewButton.setBounds(285, 131, 147, 23);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					VentanaMedicos dialog = new VentanaMedicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnVolver.setBounds(41, 131, 147, 23);
			contentPanel.add(btnVolver);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(BajaPacientes.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 459, 192);
			contentPanel.add(lblNewLabel);
		}
	}
	
	public void llenaComboBox(JComboBox cmb) {
		
		try {
			String a = "Seleccione paciente";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_paciente, nombre from pacientes");
			while(rs.next()) {
				String s = rs.getInt(1) + "/" + rs.getString(2);
				cmb.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
