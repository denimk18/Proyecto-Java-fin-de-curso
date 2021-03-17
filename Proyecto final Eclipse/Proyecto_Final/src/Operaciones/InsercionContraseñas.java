package Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Datos.BaseDatos;
import Datos.Encriptacion;

public class InsercionContrase�as {

	static Encriptacion encripta = new Encriptacion();
	static BaseDatos bd = new BaseDatos();
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		bd.creaConexion();		
		creaContrase�a();
	}
	
	/**
	 * M�todo que crea una contrase�a y la encripta a partir de un string y el c�digo personal de cada trabajador.
	 * El array numCodPersonal tiene un tama�o de 8 porque hay 8 trabajadores y almacena su c�digo personal
	 * El array contrase�asEncriptadas tiene un tama�o de 8 por la misma raz�n y almaceno el string encriptada. (Es la contrase�a encriptada)
	 * Dicha contrase�a est� conformado por la palabra usuario+cod personal de cada trabajador
	 * Haci�ndolo as� se consigue que no haya dos iguales.
	 * 
	 * Una vez creadas las 8 contrase�as se recorre el array y se hace el update sobre la tabla autentificacion
	 */
	
	@SuppressWarnings({ "unused", "static-access" })
	public static void creaContrase�a() {
		Connection conexion = bd.devuelveConexion();
		String usuario = "usuario";
		String encriptada = "";
		
		try {
			Statement sentencia = conexion.createStatement();
			String sql = "SELECT COD_PERSONAL FROM AUTENTIFICACION;";
			ResultSet rs = sentencia.executeQuery(sql);
			int[] numCodPersonal = new int[10];
			String[] contrase�asEncriptadas = new String[10];
			int i = 0;
				while (rs.next()) {
					usuario = usuario + rs.getInt(1);
					encriptada = encripta.encode(usuario);
					numCodPersonal[i] = rs.getInt(1);
					contrase�asEncriptadas[i] = encriptada;
					i++;
				}

				for(int ii=0;ii<numCodPersonal.length;ii++) {					
					String update = "UPDATE  AUTENTIFICACION SET CONTRASE�A = '" + contrase�asEncriptadas[ii] + "'"
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
