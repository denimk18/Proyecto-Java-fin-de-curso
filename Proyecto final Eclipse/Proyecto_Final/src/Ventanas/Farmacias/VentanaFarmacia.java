package Ventanas.Farmacias;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Ventanas.AreaPersonal;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class VentanaFarmacia extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaFarmacia dialog = new VentanaFarmacia();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaFarmacia() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaFarmacia.class.getResource("/Imagenes/icons8-cl\u00EDnica-40.png")));
		setBounds(100, 100, 450, 478);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("Pedidos");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Pedido dialog = new Pedido();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnNewButton.setBounds(61, 23, 315, 38);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnInventario = new JButton("Inventario");
			btnInventario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Inventario dialog = new Inventario();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnInventario.setBounds(61, 88, 315, 38);
			contentPanel.add(btnInventario);
		}
		{
			JButton btnreaPersonal = new JButton("\u00C1rea Personal");
			btnreaPersonal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AreaPersonal dialog = new AreaPersonal();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnreaPersonal.setBounds(61, 377, 315, 38);
			contentPanel.add(btnreaPersonal);
		}
		
		JButton btnConsulta = new JButton("Consulta pedidos");
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ConsultaPedidos dialog = new ConsultaPedidos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnConsulta.setBounds(61, 163, 315, 38);
		contentPanel.add(btnConsulta);
		
		JButton btnSolicitudes = new JButton("Solicitudes f\u00E1rmacos");
		btnSolicitudes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ConsultaSolicitudes dialog = new ConsultaSolicitudes();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnSolicitudes.setBounds(61, 232, 315, 38);
		contentPanel.add(btnSolicitudes);
		{
			JButton btnRecepcion = new JButton("Recepci\u00F3n pedidos");
			btnRecepcion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					RecepcionPedidos dialog = new RecepcionPedidos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}
			});
			btnRecepcion.setBounds(61, 300, 315, 38);
			contentPanel.add(btnRecepcion);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(VentanaFarmacia.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 434, 439);
			contentPanel.add(lblNewLabel);
		}
	}
	

}
