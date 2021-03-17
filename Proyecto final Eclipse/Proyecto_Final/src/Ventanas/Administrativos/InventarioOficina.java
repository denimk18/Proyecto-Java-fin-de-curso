package Ventanas.Administrativos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesAdministrativos;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InventarioOficina extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE", "UNIDADES", "FECHA LLEGADA" };
	DefaultTableModel dtm;
	
	private JTable table;

	OperacionesAdministrativos operaciones = new OperacionesAdministrativos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InventarioOficina dialog = new InventarioOficina();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Create the dialog.
	 */
	public InventarioOficina() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 551, 564);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 74, 515, 395);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		
		dtm = new DefaultTableModel(datos, nombreColumnas) {

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		panel.setLayout(null);
		table = new JTable(dtm);
		table.setPreferredScrollableViewportSize(new Dimension(100, 70));
		
				JScrollPane scrollPane2 = new JScrollPane(table);
				scrollPane2.setBounds(0, 0, 512, 395);
				panel.add(scrollPane2);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(86, 28, 199, 20);
		contentPanel.add(comboBox);
		llenaComboBox(comboBox);
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimpiarJTable();
				operaciones.consultaMaterial(dtm, comboBox);
			}
		});
		btnNewButton.setBounds(325, 27, 89, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(307, 480, 89, 23);
		contentPanel.add(btnVolver);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();
				operaciones.listadoInventario(dtm);
			}
		});
		btnListado.setBounds(436, 480, 89, 23);
		contentPanel.add(btnListado);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(InventarioOficina.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
			lblNewLabel.setBounds(0, 0, 535, 525);
			contentPanel.add(lblNewLabel);
		}
		
		operaciones.listadoInventario(dtm);
		dtm.fireTableDataChanged();
	}
	
	/**
	 * Método para llenar comboBox con los materiales
	 * */
	
	
	public void llenaComboBox(JComboBox cmb) {
		cmb.addItem("Seleccione material");
		
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre from material_oficina");
			while(rs.next()) {
				String s = rs.getString(1);
				cmb.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LimpiarJTable() {
		  try {
	            DefaultTableModel modelo=(DefaultTableModel) table.getModel();
	            int filas=table.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
}
