package Operaciones;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Ventanas.Enfermeras.FarmacosAdministrados;

import javax.swing.JScrollPane;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

/**
 * JTABLE USADO PARA MOSTRAR LOS DIAGNÓSTICOS DE LOS PACIENTES.
 * @author Denitsa
 *
 */
//LLENAR JTABLE CON DefaultTableModel
public class JTablaDiagnóstico extends JFrame {

	static Connection conexion;

	Object[][] datos;
	String[] nombreColumnas = { "NOMBRE FÁRMACO", "CANTIDAD", "DIAGNÓSTICO" , "FECHA DIAGNÓSTICO" };
	JTable tabla;
	DefaultTableModel dtm;
	

	public JTablaDiagnóstico() {
		super("DIAGNÓSTICOS DEL PACIENTE");
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
			ResultSet resultado = sentencia.executeQuery("SELECT * FROM diagnostico_paciente where cod_paciente = " + codPaciente );

			while (resultado.next()) {
				Object[] fila = { resultado.getString(3),
						resultado.getInt(4),
						resultado.getString(5) ,
						resultado.getDate(6)						
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

	

}//fin
