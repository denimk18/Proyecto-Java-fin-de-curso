
package Ventanas.Administrativos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;

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

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.InputMap;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ListSelectionModel;

public class PedidosMaterial extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombre;
	private JTextField txtUnidades;
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private JTable table;
	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE", "UNIDADES", "FECHA PEDIDO" };
	DefaultTableModel dtm;
	OperacionesAdministrativos operaciones = new OperacionesAdministrativos();
	JComboBox comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PedidosMaterial dialog = new PedidosMaterial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PedidosMaterial() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 545, 579);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(20, 506, 146, 23);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VentanaAdministrativos dialog = new VentanaAdministrativos();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		contentPanel.add(btnVolver);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(20, 11, 488, 473);
		contentPanel.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Pedido", null, panel, null);
		panel.setLayout(null);
		
		JDateChooser txtFecha = new JDateChooser();
		txtFecha.setBounds(249, 214, 166, 20);
		txtFecha.setDateFormatString("yyyy-MM-dd\r\n");
		JTextFieldDateEditor editor = (JTextFieldDateEditor) txtFecha.getDateEditor();
		editor.setEditable(false);
		panel.add(txtFecha);
		
		JButton btnPedido = new JButton("Realizar pedido");
		btnPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(editor.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Seleccione fecha");
				}else {
					String fecha = editor.getText();
					operaciones.insertaPedido(txtNombre, txtUnidades, fecha);
				}
			}
		});
		btnPedido.setBounds(269, 296, 146, 23);
		panel.add(btnPedido);
		
		JLabel lblFechaPedido = new JLabel("Fecha pedido:");
		lblFechaPedido.setBounds(72, 211, 120, 23);
		panel.add(lblFechaPedido);
		lblFechaPedido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtUnidades = new JTextField();
		txtUnidades.setBounds(249, 138, 166, 20);
		panel.add(txtUnidades);
		txtUnidades.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtUnidades.getText();
				if (Caracteres.length() >= 4) {
					e.consume();
				}

				char caracter = e.getKeyChar();
				// comprobar si los caracteres
				// son digitos o backspace
				if ((caracter < '0' || caracter > '9') && caracter != '\b') {
					e.consume();
				}
			}
		});
		txtUnidades.setColumns(10);
		evitarPegar(txtUnidades);
		
		JLabel lblUnidadesPedidas = new JLabel("Unidades pedidas:");
		lblUnidadesPedidas.setBounds(72, 136, 120, 23);
		panel.add(lblUnidadesPedidas);
		lblUnidadesPedidas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblNewLabel = new JLabel("Nombre material:");
		lblNewLabel.setBounds(72, 66, 120, 23);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtNombre = new JTextField();
		txtNombre.setBounds(249, 68, 166, 20);
		panel.add(txtNombre);
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Caracteres = txtNombre.getText();
				if (Caracteres.length() >= 25) {
					e.consume();
				}

			}
		});
		txtNombre.setColumns(10);
		evitarPegar(txtNombre);
		
		JPanel panel_1 = new JPanel();
		panel_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				vaciaComboBox(comboBox_1);
				llenaComboBox(comboBox_1);
				LimpiarJTable();
				operaciones.listadoPedidos(dtm);
			}
		});
		tabbedPane.addTab("Consulta pedidos", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 66, 463, 368);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		panel.setLayout(null);
		
	
		
		
		table = new JTable(dtm);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(100, 70));
		
				JScrollPane scrollPane2 = new JScrollPane(table);
				scrollPane2.setBounds(0, 0, 463, 368);
				panel_2.add(scrollPane2);
		
				
				comboBox_1 = new JComboBox();
				comboBox_1.setBounds(82, 22, 170, 20);
				panel_1.add(comboBox_1);
				llenaComboBox(comboBox_1);
				
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();
				operaciones.consultaPedido(dtm, comboBox_1);
			}
		});
		btnConsultar.setBounds(289, 21, 89, 23);
		panel_1.add(btnConsultar);
		
		JButton btnListadoPedidos = new JButton("Listado pedidos");
		btnListadoPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LimpiarJTable();
				panel_2.show();
				operaciones.listadoPedidos(dtm);

			}
		});
		btnListadoPedidos.setBounds(362, 506, 146, 23);
		contentPanel.add(btnListadoPedidos);
		
		JButton btnBorrar = new JButton("Borrar pedido");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar la solicitud?", "", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						String nombre = (String) table.getValueAt(table.getSelectedRow(), 0);
						operaciones.borraPedido(nombre);
						removeSelectedRows(); //Borra la fila que hemos seleccionado en la tabla
						vaciaComboBox(comboBox_1);
						llenaComboBox(comboBox_1);
					}				
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione pedido a borrar");
				}
			}
		});
		btnBorrar.setBounds(194, 506, 146, 23);
		contentPanel.add(btnBorrar);
	
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(0, 0, 529, 540);
		lblNewLabel_1.setIcon(new ImageIcon(PedidosMaterial.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		contentPanel.add(lblNewLabel_1);
		
		operaciones.listadoPedidos(dtm);

	}
	
	
	/**
	 * Evita pegar en un textfield
	 * @param campo TextArea
	 */
	public static void evitarPegar(JTextField campo) {
		 InputMap map2 = campo.getInputMap(JTextField.WHEN_FOCUSED);
		 map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
	}
	
	/**
	 * LLena el comboBox con el nombre de los materials que se han pedido hasta ahora
	 * @param cmb
	 */
	public void llenaComboBox(JComboBox cmb) {
		String a = "Seleccione pedido";
		cmb.addItem(a);
		
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select * from pedidos_oficina");
			while(rs.next()) {
				cmb.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void vaciaComboBox(JComboBox cmb) {
		cmb.removeAllItems();
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
	
	public void removeSelectedRows(){
		   DefaultTableModel model = (DefaultTableModel) table.getModel();
		   int[] rows = table.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
		     model.removeRow(rows[i]-i);
		   }
	}}
