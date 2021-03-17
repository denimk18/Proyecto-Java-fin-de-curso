package Ventanas.Farmacias;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesFarmacia;
import Ventanas.VentanaInicio;
import dao.PedidoFarmacia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConsultaSolicitudes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE", "UNIDADES PEDIDAS", "FECHA SOLICITUD" };
	DefaultTableModel dtm;
	OperacionesFarmacia operaciones = new OperacionesFarmacia();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsultaSolicitudes dialog = new ConsultaSolicitudes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsultaSolicitudes() {
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
		setBounds(100, 100, 554, 471);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 22, 518, 338);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		table = new JTable(dtm);

		table.setPreferredScrollableViewportSize(new Dimension(100, 70));

		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 518, 338);

		panel.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(ConsultaSolicitudes.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel_1.setBounds(0, 0, 518, 338);
		panel.add(lblNewLabel_1);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaFarmacia dialog = new VentanaFarmacia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnVolver.setBounds(10, 377, 138, 23);
		contentPanel.add(btnVolver);
		
		JButton btnBorrarSolicitud = new JButton("Borrar solicitud");
		btnBorrarSolicitud.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				
				int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar la solicitud?", "", JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					borraSolicitud();
					removeSelectedRows();
				} else {
				   
				}
				
				
			}
		});
		btnBorrarSolicitud.setBounds(390, 377, 138, 23);
		contentPanel.add(btnBorrarSolicitud);
		
		JButton btnRealizarPedido = new JButton("Realizar pedido");
		btnRealizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				  if (table.getSelectedRow() != -1) {
			            String nombre = (String) table.getValueAt(table.getSelectedRow(), 0);
			            int unidades = (int) table.getValueAt(table.getSelectedRow(), 1);
			            Date fecha = (Date) table.getValueAt(table.getSelectedRow(), 2);

			            PedidoFarmacia p = new PedidoFarmacia(nombre,unidades,fecha);
			           if(operaciones.insertaPedido(p)) {
				            removeSelectedRows();
			           }
				  } else {
			            JOptionPane.showMessageDialog(null, "Seleccione medicamento a recepcionar");
			        }
			}
		});
		btnRealizarPedido.setBounds(196, 377, 138, 23);
		contentPanel.add(btnRealizarPedido);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ConsultaSolicitudes.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 538, 440);
		contentPanel.add(lblNewLabel);
		
		operaciones.listadoSolicitudes(dtm);
	}
	
	/**
	 * Borra la fila seleccionada. Sirve para que cuando se haya hecho el pedido con la solicitud que corresponde borrarla de la tabla
	 */
	public void removeSelectedRows(){
		   DefaultTableModel model = (DefaultTableModel) table.getModel();
		   int[] rows = table.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }
	}
	
	/**
	 * Borra la solicitud seleccionada en el JTable de la base de datos
	 */
	public void borraSolicitud() {
		 if (table.getSelectedRow() != -1) {
	            String nombre = (String) table.getValueAt(table.getSelectedRow(), 0);
	        
	            try {
					Statement sentencia = bd.createStatement();
					sentencia.executeUpdate("delete from solicitud_medicamento where nombre = '" + nombre + "'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  } else {
	            JOptionPane.showMessageDialog(null, "Seleccione medicamento a recepcionar");
	        }
	}
}
