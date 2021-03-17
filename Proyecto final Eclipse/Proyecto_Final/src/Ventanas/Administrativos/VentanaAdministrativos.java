package Ventanas.Administrativos;

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

public class VentanaAdministrativos extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaAdministrativos dialog = new VentanaAdministrativos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaAdministrativos() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAdministrativos.class.getResource("/Imagenes/icons8-recepci\u00F3n-40.png")));
		setBounds(100, 100, 450, 556);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Visitas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Visitas dialog = new Visitas();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnNewButton.setBounds(61, 33, 313, 41);
		contentPanel.add(btnNewButton);
		
		JButton btnPedidosMaterialOficina = new JButton("Pedidos material oficina");
		btnPedidosMaterialOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				PedidosMaterial dialog = new PedidosMaterial();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnPedidosMaterialOficina.setBounds(61, 107, 313, 41);
		contentPanel.add(btnPedidosMaterialOficina);
		
		JButton btnListadoMaterial = new JButton("Inventario material oficina");
		btnListadoMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				InventarioOficina dialog = new InventarioOficina();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnListadoMaterial.setBounds(61, 188, 313, 41);
		contentPanel.add(btnListadoMaterial);
		
		JButton btnListadoMaterialOficina = new JButton("Recepci\u00F3n pedidos oficina");
		btnListadoMaterialOficina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RecepcionPedidos dialog = new RecepcionPedidos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnListadoMaterialOficina.setBounds(61, 267, 313, 41);
		contentPanel.add(btnListadoMaterialOficina);
		
		JButton btnRecepcinPedidosFarmacia = new JButton("Recepci\u00F3n pedidos farmacia");
		btnRecepcinPedidosFarmacia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				RecepcionPedidosFarmacia dialog = new RecepcionPedidosFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnRecepcinPedidosFarmacia.setBounds(61, 342, 313, 41);
		contentPanel.add(btnRecepcinPedidosFarmacia);
		
		JButton btnreaPersonal = new JButton("\u00C1rea personal");
		btnreaPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setModal(false);
				AreaPersonal dialog = new AreaPersonal();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnreaPersonal.setBounds(61, 421, 313, 41);
		contentPanel.add(btnreaPersonal);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaAdministrativos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 434, 517);
		contentPanel.add(lblNewLabel);
	}
}
