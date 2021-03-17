package Ventanas.Farmacias;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesFarmacia;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Inventario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTable table2;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesFarmacia operaciones = new OperacionesFarmacia();
	Object[][] datos;
	String[] nombreColumnas = {"COD MEDICAMENTO", "NOMBRE", "UNIDADES", "FECHA LLEGADA" };
	DefaultTableModel dtm;
	DefaultTableModel dtm2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Inventario dialog = new Inventario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Inventario() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 571, 621);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnNewButton.setBounds(456, 548, 89, 23);
		contentPanel.add(btnNewButton);
		
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 535, 526);
		contentPanel.add(tabbedPane_1);
		table = new JTable(dtm);
		
				table.setPreferredScrollableViewportSize(new Dimension(100, 70));
				
						// Creamos un JscrollPane y le agregamos la JTable
						JScrollPane scrollPane = new JScrollPane(table);
						tabbedPane_1.addTab("Listado medicación", null, scrollPane, null);
						operaciones.listadoInventario(dtm);
						
						/**
						 * El panel llamado panel contiene un comboBox, un boton y otro panel que contiene un JTable
						 * En ese JTable pintamos todos los fármacos que se le han administrado al paciente seleccionado
						 */
						
						JPanel panel = new JPanel();
						tabbedPane_1.addTab("Consulta medicamento", null, panel, null);
						panel.setLayout(null);
						
						JComboBox comboBox = new JComboBox();
						comboBox.setBounds(70, 23, 193, 20);
						panel.add(comboBox);
						llenaMedicacion(comboBox);
						
					
						JPanel panel_1 = new JPanel();
						panel_1.setBounds(10, 56, 501, 429);
						panel.add(panel_1);
						
						dtm2 = new DefaultTableModel(datos, nombreColumnas){

						    @Override
						    public boolean isCellEditable(int row, int column) {
						       //all cells false
						       return false;
						    }
						};
						
						panel_1.setLayout(null);
						table2 = new JTable(dtm2);
						table2.setPreferredScrollableViewportSize(new Dimension(100, 70));

						JScrollPane scrollPane2 = new JScrollPane(table2);
						scrollPane2.setBounds(0, 0, 501, 429);
						panel_1.add(scrollPane2);
						
						JButton btnNewButton_1 = new JButton("Consultar");
						btnNewButton_1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								LimpiarJTable();
								operaciones.consultaMedicamento(dtm2, comboBox);
							}
						});
						btnNewButton_1.setBounds(297, 22, 89, 23);
						panel.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Inventario.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 560, 582);
		contentPanel.add(lblNewLabel);
	}
	
	
	public void LimpiarJTable() {
		  try {
	            DefaultTableModel modelo=(DefaultTableModel) table2.getModel();
	            int filas=table2.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	
	
	/**
	 * LLena el comboBox con los medicamentos
	 * @param cmb
	 */
	public void llenaMedicacion(JComboBox cmb) {
		try {
			String a = "Seleccione fármaco";
			cmb.addItem(a);
			cmb.setSelectedIndex(0);
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select cod_medicamento, nombre from inventario_farmacia");
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
