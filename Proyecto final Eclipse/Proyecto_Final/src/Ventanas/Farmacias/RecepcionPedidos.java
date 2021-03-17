package Ventanas.Farmacias;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesFarmacia;
import Ventanas.VentanaInicio;
import dao.RecepcionMedicacion;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RecepcionPedidos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JLabel lblNewLabel;
	private static JTable tabla;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();

	Object[][] datos;
	String[] nombreColumnas = { "COD MEDICAMENTO", "NOMBRE", "UNIDADES PEDIDAS", "UNIDADES RECIBIDAS", "FECHA LLEGADA" };
	DefaultTableModel dtm;
	OperacionesFarmacia operaciones = new OperacionesFarmacia();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecepcionPedidos dialog = new RecepcionPedidos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecepcionPedidos() {
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
		setBounds(100, 100, 572, 645);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		tabla = new JTable(dtm);		
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setPreferredScrollableViewportSize(new Dimension(536, 457));		
		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(0, 0, 536, 429);

		operaciones.recepcionMedicamento(dtm);
				
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(88, 27, 193, 20);
		contentPanel.add(comboBox);
		llenaComboBox(comboBox);
		
		JPanel panel = new JPanel();
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				comboBox.removeAllItems();
				llenaComboBox(comboBox);
				LimpiarJTable();
				operaciones.recepcionMedicamento(dtm);
			}
		});
		panel.setBounds(10, 67, 536, 431);
		contentPanel.add(panel);
		panel.setLayout(null);
		panel.add(scrollPane);
		
	
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(322, 26, 89, 23);
		contentPanel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();								
				operaciones.recepcionMedicamento(dtm, comboBox);
			}
		});
		
		
		/**
		 * Compruebo si hay alguna fila seleccionada en la tabla. Si la hay creo un objeto del tipo RecepcionMedicacion
		 * para pasarlo al método recepcionPedidos. Esto sirve para registrar los fármacos que hemos solicitado y nos han llegado
		 * a la residencia. Al hacer la recepción estos quedan registrados en el inventario.
		 * Si ya existe el fármaco en el inventario simplemente se actualiza la fecha de llegada y las unidades
		 * Si no existía se inserta en el inventario.
		 */
		JButton btnRecepcionar = new JButton("Recepcionar pedido");
		btnRecepcionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  if (tabla.getSelectedRow() != -1) {
			            int codigo = (int) tabla.getValueAt(tabla.getSelectedRow(), 0);
			            String nombre= (String) tabla.getValueAt(tabla.getSelectedRow(), 1);
			            int unidadesPedidas = (int) tabla.getValueAt(tabla.getSelectedRow(), 2);
			            int unidadesRecibidas = (int) tabla.getValueAt(tabla.getSelectedRow(), 3);
			            Date fecha = (Date) tabla.getValueAt(tabla.getSelectedRow(), 4);

			            RecepcionMedicacion r = new RecepcionMedicacion(codigo, nombre, fecha,unidadesPedidas,unidadesRecibidas);
			            if(operaciones.recepcionPedidos(r)) {
			            	removeSelectedRows();
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Seleccione medicamento a recepcionar");
			        }
			}
		});
		btnRecepcionar.setBounds(391, 536, 155, 23);
		contentPanel.add(btnRecepcionar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(10, 536, 139, 23);
		contentPanel.add(btnVolver);
		
		JButton btnListadoPedidos = new JButton("Listado pedidos");
		btnListadoPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimpiarJTable();
				operaciones.recepcionMedicamento(dtm);
			}
		});
		btnListadoPedidos.setBounds(200, 536, 155, 23);
		contentPanel.add(btnListadoPedidos);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(RecepcionPedidos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 564, 606);
		contentPanel.add(lblNewLabel);
		
	
	}
	
	
	public void llenaComboBox(JComboBox cmb) {
		String s = "Seleccione medicamento";
		cmb.addItem(s);
		
		try {
			Statement sentencia = bd.createStatement();
			String sql = ("select cod_medicamento, nombre from recepcion_medicacion");
			ResultSet rs = sentencia.executeQuery(sql);
			
			while(rs.next()) {
				int cod = rs.getInt(1);
				String nombre = rs.getString(2);
				String item = cod + "/" + nombre;
				cmb.addItem(item);
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
	
	public void LimpiarJTable() {
		  try {
	            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
	            int filas=tabla.getRowCount();
	            for (int i = 0;filas>i; i++) {
	                modelo.removeRow(0);
	            }
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
	        }
	}
	
	
	/**
	 * Borra la fila seleccionada. Sirve para que cuando se haya hecho el pedido con la solicitud que corresponde borrarla de la tabla
	 */
	public void removeSelectedRows(){
		   DefaultTableModel model = (DefaultTableModel) tabla.getModel();
		   int[] rows = tabla.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }
	}
	
	
	
}
