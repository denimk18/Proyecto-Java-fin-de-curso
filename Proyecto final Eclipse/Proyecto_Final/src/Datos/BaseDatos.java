package Datos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Denitsa
 * Clase que crea la conexión a la base de datos.
 * Tiene un método que crea la conexión y otro que la cierra
 */

public class BaseDatos {
	
	public static Connection conexion ;
	
	
	
	/**
	 * En este método creamos la conexión. El nombre de la bd, usuario y contraseña es proyecto. No es recomendable tener una 
	 * contraseña tan fácil para la bd por motivos de seguridad pero aquí se ha puesto para agilizar los trámites.
	 * 
	 * Devuelve true o false en función de si está conectado o no
	 * @return boolean conectado
	 */
	public static boolean creaConexion() {
		boolean conectado = true;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Establecemos la conexion con la BD
			 conexion = DriverManager.getConnection ("jdbc:mysql://localhost/proyecto","proyecto", "proyecto");   
				//JOptionPane.showMessageDialog(null, "CONEXIÓN ABIERTA");

		} catch (ClassNotFoundException e) {
			conectado = false;
		} catch (SQLException e) {
			conectado = false;
		}				
		return conectado;
	}
	
	
	public static boolean cierraConexion() {
		boolean cerrado = true;
		
		try {
			conexion.close();
			JOptionPane.showMessageDialog(null, "CONEXIÓN CERRADA");
		} catch (SQLException e) {
			cerrado = false;
		}
		return cerrado;
	}

	public static Connection devuelveConexion() {
		return conexion;
	}
	
	

}
