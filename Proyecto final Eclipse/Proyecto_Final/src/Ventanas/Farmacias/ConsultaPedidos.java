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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ConsultaPedidos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTable table2;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	OperacionesFarmacia operaciones = new OperacionesFarmacia();
	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE", "UNIDADES PEDIDAS", "FECHA PEDIDO" };
	DefaultTableModel dtm;
	DefaultTableModel dtm2;
	private JComboBox comboBox;
	private JButton btnBorrar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsultaPedidos dialog = new ConsultaPedidos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsultaPedidos() {
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
		setBounds(100, 100, 573, 605);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 526, 495);
		contentPanel.add(tabbedPane_1);
		table = new JTable(dtm);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				table.setPreferredScrollableViewportSize(new Dimension(100, 70));
				
						// Creamos un JscrollPane y le agregamos la JTable
						JScrollPane scrollPane = new JScrollPane(table);
						scrollPane.addComponentListener(new ComponentAdapter() {
							@Override
							public void componentShown(ComponentEvent e) {
								LimpiarJTable(table);
								operaciones.listadoPedidos(dtm);
							}
						});
						tabbedPane_1.addTab("Listado pedidos", null, scrollPane, null);
						operaciones.listadoPedidos(dtm);
						
						/**
						 * El panel llamado panel contiene un comboBox, un boton y otro panel que contiene un JTable
						 * En ese JTable pintamos todos los fármacos que se le han administrado al paciente seleccionado
						 */
						
						JPanel panel = new JPanel();
						panel.addComponentListener(new ComponentAdapter() {
							@Override
							public void componentShown(ComponentEvent arg0) {
								btnBorrar.setVisible(false);
								comboBox.removeAllItems();
								llenaComboBox(comboBox);
								LimpiarJTable(table2);
							}
						});
						tabbedPane_1.addTab("Consulta pedido", null, panel, null);
						panel.setLayout(null);
						
						comboBox = new JComboBox();
						comboBox.setBounds(36, 23, 193, 20);
						panel.add(comboBox);
						llenaComboBox(comboBox);
						
						JButton btnNewButton = new JButton("Consultar");
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								LimpiarJTable(table2);								
								operaciones.pedidosMedicamento(dtm2, comboBox);
							}
						});
						btnNewButton.setBounds(261, 22, 89, 23);
						panel.add(btnNewButton);
						
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
						table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						table2.setPreferredScrollableViewportSize(new Dimension(100, 70));

						JScrollPane scrollPane2 = new JScrollPane(table2);
						scrollPane2.setBounds(0, 0, 501, 429);
						panel_1.add(scrollPane2);
						
						/**
						 * Comprueba si hay fila seleccionada. Si no la hay avisa de que no se puede borrar.
						 * Si la hay pide confirmación. En caso de pulsar ok se borra el pedido
						 */
						JButton btnBorrarCombo = new JButton("Borrar");
						btnBorrarCombo.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if (table2.getSelectedRow() != -1) {
									int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar la solicitud?", "", JOptionPane.YES_NO_OPTION);
									if (reply == JOptionPane.YES_OPTION) {
										String nombre2 = (String) table2.getValueAt(table2.getSelectedRow(), 0);
										if (operaciones.borrarPedido(nombre2)) {
											removeSelectedRows(table2);
											comboBox.removeAllItems();
											llenaComboBox(comboBox);
										}
									}								

								} else {
									JOptionPane.showMessageDialog(null, "Seleccione pedido a borrar");

								}
							}
						});
						btnBorrarCombo.setBounds(404, 22, 89, 23);
						panel.add(btnBorrarCombo);
	
		btnVolver.setBounds(10, 517, 143, 23);
		contentPanel.add(btnVolver);
		
	    btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar la solicitud?", "", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						String nombre = (String) table.getValueAt(table.getSelectedRow(), 0);
						if (operaciones.borrarPedido(nombre)) {
							removeSelectedRows(table);
						}
					}
				

				} else {
					JOptionPane.showMessageDialog(null, "Seleccione pedido a borrar");

				}			
			}
		});
		btnBorrar.setBounds(384, 517, 152, 23);
		contentPanel.add(btnBorrar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 564, 565);
		lblNewLabel.setIcon(new ImageIcon(ConsultaPedidos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		contentPanel.add(lblNewLabel);
	}
	
	public void llenaComboBox(JComboBox cmb) {
		String s = "Selecciones medicamento";
		cmb.addItem(s);
		
		try {
			Statement sentencia = bd.createStatement();
			String sql = ("select distinct nombre from pedido_farmacia");
			ResultSet rs = sentencia.executeQuery(sql);
			
			while(rs.next()) {
				String nombre = rs.getString(1);
				cmb.addItem(nombre);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Lo utilizo para limpiar el JTable de la pestaña Consulta fármacos paciente. Cada vez que se selecciona un paciente y se
	 * presiona consultar se limpia la tabla mediante este método y luego se muestran los datos.
	 */
	
	public void LimpiarJTable(JTable table) {
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
	
	
	/**
	 * Borra la fila seleccionada.
	 */
	public void removeSelectedRows(JTable tabla){
		   DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		   int[] rows = tabla.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }
	}
}
