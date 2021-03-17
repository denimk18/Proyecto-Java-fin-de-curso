package Operaciones;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Ventanas.VentanaInicio;
import Ventanas.Enfermeras.FarmacosAdministrados;
import Ventanas.Enfermeras.VentanaEnfermeras;

import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.*;
import java.sql.*;

/**
 * JTABLE USADO PARA MOSTRAR LAS RECETAS DE LOS PACIENTES.
 * @author Denitsa
 *
 */
//LLENAR JTABLE CON DefaultTableModel
public class JTablaRecetas extends JFrame {

	static Connection conexion;
	static Connection bd = VentanaInicio.bd.devuelveConexion();

	Object[][] datos;
	String[] nombreColumnas = { "CÓDIGO RECETA", "PACIENTE", "MEDICAMENTO" , "FECHA RECETA", "ADMINISTRACIÓN" };
	JTable tabla;
	DefaultTableModel dtm;
	

	public JTablaRecetas() {
		super("RECETAS DEL PACIENTE");
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		dtm = new DefaultTableModel(datos, nombreColumnas){

		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		
		LLenarJTableConsulta(FarmacosAdministrados.codPaciente);
		tabla = new JTable(dtm);
		
    	tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));

		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(tabla);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		//
		// manejamos la salida
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						dispose();
					}
				});
			}
	//constructor

	public void LLenarJTableConsulta(int codPaciente) {				
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection ("jdbc:mysql://localhost/proyecto","proyecto", "proyecto");   

			Statement sentencia = conexion.createStatement();
			ResultSet resultado = sentencia.executeQuery("SELECT * FROM recetas where cod_paciente = " + codPaciente );

			while (resultado.next()) {
				Object[] fila = { resultado.getInt(1),
						nombrePaciente(resultado.getInt(2)),
						nombreMedicamento(resultado.getInt(3)),
						resultado.getDate(4),
						resultado.getString(5)
						};
				
				//llenamos DefaultTableModel
				dtm.addRow(fila);
			}

		} catch (SQLException ex) {
			System.out.println("<< ERROR CONSULTAS >>: SQLException :" + ex.getMessage());
			System.out.println("<< ERROR: SQLState: " + ex.getSQLState());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// LLenarJTableConsulta

	/**
	 * Devuelve el nombre del paciente que corresponde a ese código
	 * @param cod
	 * @return String nombre
	 */
	public String nombrePaciente(int cod) {
		String nombre = "";
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre from pacientes where cod_paciente = " + cod);
				while(rs.next()) {
				nombre = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombre;
	}
	
	
	/**
	 * Devuelve el nombre del medicamento que corresponde a ese codigo
	 * @param cod
	 * @return String nombre
	 */
	public String nombreMedicamento(int cod) {
		String nombre = "";
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre from inventario_farmacia where cod_medicamento = " + cod);
				while(rs.next()) {
				nombre = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombre;
	}

}//fin

