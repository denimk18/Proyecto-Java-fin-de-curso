package Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Datos.BaseDatos;
import Datos.Encriptacion;

public class InsercionContraseñas {

	static Encriptacion encripta = new Encriptacion();
	static BaseDatos bd = new BaseDatos();
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		bd.creaConexion();		
		creaContraseña();
	}
	
	/**
	 * Método que crea una contraseña y la encripta a partir de un string y el código personal de cada trabajador.
	 * El array numCodPersonal tiene un tamaño de 8 porque hay 8 trabajadores y almacena su código personal
	 * El array contraseñasEncriptadas tiene un tamaño de 8 por la misma razón y almaceno el string encriptada. (Es la contraseña encriptada)
	 * Dicha contraseña está conformado por la palabra usuario+cod personal de cada trabajador
	 * Haciéndolo así se consigue que no haya dos iguales.
	 * 
	 * Una vez creadas las 8 contraseñas se recorre el array y se hace el update sobre la tabla autentificacion
	 */
	
	@SuppressWarnings({ "unused", "static-access" })
	public static void creaContraseña() {
		Connection conexion = bd.devuelveConexion();
		String usuario = "usuario";
		String encriptada = "";
		
		try {
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT COD_PERSONAL FROM AUTENTIFICACION;";
			ResultSet rs = sentencia.executeQuery(sql);
			int[] numCodPersonal = new int[10];
			String[] contraseñasEncriptadas = new String[10];
			int i = 0;
				while (rs.next()) {
					usuario = usuario + rs.getInt(1);
					encriptada = encripta.encode(usuario);
					numCodPersonal[i] = rs.getInt(1);
					contraseñasEncriptadas[i] = encriptada;
					i++;
				}

				for(int ii=0;ii<numCodPersonal.length;ii++) {					
					String update = "UPDATE  AUTENTIFICACION SET CONTRASEÑA = '" + contraseñasEncriptadas[ii] + "'"
							+ " WHERE COD_PERSONAL = " + numCodPersonal[ii];
					int resultado = sentencia.executeUpdate(update);
					System.out.println(resultado + " fila afectada.");
				}		
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
