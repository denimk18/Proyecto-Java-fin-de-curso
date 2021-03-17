package Datos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Denitsa
 * Clase que crea la conexi�n a la base de datos.
 * Tiene un m�todo que crea la conexi�n y otro que la cierra
 */

public class BaseDatos {
	
	public static Connection conexion ;
	
	
	
	/**
	 * En este m�todo creamos la conexi�n. El nombre de la bd, usuario y contrase�a es proyecto. No es recomendable tener una 
	 * contrase�a tan f�cil para la bd por motivos de seguridad pero aqu� se ha puesto para agilizar los tr�mites.
	 * 
	 * Devuelve true o false en funci�n de si est� conectado o no
	 * @return boolean conectado
	 */
	public static boolean creaConexion() {
		boolean conectado = true;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Establecemos la conexion con la BD
			 conexion = DriverManager.getConnection ("jdbc:mysql://localhost/proyecto","proyecto", "proyecto");   
				//JOptionPane.showMessageDialog(null, "CONEXI�N ABIERTA");

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
			JOptionPane.showMessageDialog(null, "CONEXI�N CERRADA");
		} catch (SQLException e) {
			cerrado = false;
		}
		return cerrado;
	}

	public static Connection devuelveConexion() {
		return conexion;
	}
	
	

}
