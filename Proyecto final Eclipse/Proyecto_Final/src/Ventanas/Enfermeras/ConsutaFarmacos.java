package Ventanas.Enfermeras;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Operaciones.OperacionesEnfermeras;
import Ventanas.VentanaInicio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConsutaFarmacos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	OperacionesEnfermeras operaciones = new OperacionesEnfermeras();
	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	private JTable table;
	private JTable table2;
	
	Object[][] datos;
	String[] nombreColumnas = { "PACIENTE", "MEDICAMENTO" , "FECHA ADMINISTRACIÓN", "CANTIDAD" };
	DefaultTableModel dtm;
	DefaultTableModel dtm2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConsutaFarmacos dialog = new ConsutaFarmacos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ConsutaFarmacos() {
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		setBounds(100, 100, 573, 656);
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
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 24, 526, 524);
		contentPanel.add(tabbedPane_1);
		table = new JTable(dtm);
		
				table.setPreferredScrollableViewportSize(new Dimension(100, 70));
				
						// Creamos un JscrollPane y le agregamos la JTable
						JScrollPane scrollPane = new JScrollPane(table);
						tabbedPane_1.addTab("Listado medicaci\u00F3n", null, scrollPane, null);
						operaciones.listadoFarmacos(dtm);

						
						/**
						 * El panel llamado panel contiene un comboBox, un boton y otro panel que contiene un JTable
						 * En ese JTable pintamos todos los fármacos que se le han administrado al paciente seleccionado
						 */
						
						JPanel panel = new JPanel();
						tabbedPane_1.addTab("Consulta fármacos paciente", null, panel, null);
						panel.setLayout(null);
						
						JComboBox comboBox = new JComboBox();
						comboBox.setBounds(70, 23, 193, 20);
						panel.add(comboBox);
						
						JButton btnNewButton = new JButton("Consultar");
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								LimpiarJTable();
								operaciones.farmacosPaciente(comboBox, dtm2);
							}
						});
						btnNewButton.setBounds(305, 22, 89, 23);
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
						table2.setPreferredScrollableViewportSize(new Dimension(100, 70));

						JScrollPane scrollPane2 = new JScrollPane(table2);
						scrollPane2.setBounds(0, 0, 501, 429);
						panel_1.add(scrollPane2);
				
						llenaComboBox(comboBox);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaEnfermeras dialog = new VentanaEnfermeras();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);

			}
		});
		btnVolver.setBounds(10, 570, 89, 23);
		contentPanel.add(btnVolver);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ConsutaFarmacos.class.getResource("/Imagenes/53143755fa1f7f6113fdc5ecb8580ef4.jpg")));
		lblNewLabel.setBounds(0, 0, 557, 617);
		contentPanel.add(lblNewLabel);
	}
	
	/**
	 * LLena el comboBox con los pacientes
	 * @param cmb
	 */
	public void llenaComboBox(JComboBox cmb) {
		
		try {
			String a = "Seleccione paciente";
			cmb.addItem(a);
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
	
	/**
	 * Lo utilizo para limpiar el JTable de la pestaña Consulta fármacos paciente. Cada vez que se selecciona un paciente y se
	 * presiona consultar se limpia la tabla mediante este método y luego se muestran los datos.
	 */
	
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
}
