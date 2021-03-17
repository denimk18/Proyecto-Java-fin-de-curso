package Datos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Denitsa
 * Esta clase implementa la interfaz ISecurity que contiene el método Encriptacion
 * Desarrolla el método Encriptación y Hexadecimal 
 * Se utiliza la librería MessageDigest para encriptar
 */

public class Encriptacion implements ISecurity {

	/**
	 * Encripta en sha1 el string que le pasamos y lo devuelve 
	 * 
	 * @param text , texto a encriptar
	 * @return String s, que en este caso sería la contraseña encriptada
	 */
	
	@Override
	public String encode(String text) {

		MessageDigest md; String s = "";
		try {
			md = MessageDigest.getInstance("sha1");

			byte dataBytes[] = (text).getBytes(); 
			md.update(dataBytes);// SE INTRODUCE TEXTO A RESUMIR
			byte resumen[] = md.digest(); // SE CALCULA EL RESUMEN
			s = Hexadecimal(resumen);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	
	/**
	 * Recibe un array de bytes que pasa a String.
	 * En el método encode al generar la contraseña encriptada esta es un array de bytes, es por eso que necesitamos convertirla
	 * a cadena.
	 * @param resumen
	 * @return String hex
	 */
	static String Hexadecimal(byte[] resumen) {
		String hex = "";
		for (int i = 0; i < resumen.length; i++) {
			String h = Integer.toHexString(resumen[i] & 0xFF);
			if (h.length() == 1)
				hex += "0";
			hex += h;
		}
		return hex.toUpperCase();
	}// Hexadecimal
	
	
	
	

}
