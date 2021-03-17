package Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Datos.Encriptacion;
import Ventanas.VentanaInicio;
import Ventanas.Administrativos.VentanaAdministrativos;
import Ventanas.Enfermeras.VentanaEnfermeras;
import Ventanas.Farmacias.VentanaFarmacia;
import Ventanas.Medicos.VentanaMedicos;

public class OperacionesContraseñas {
	
	/**
	 * Clase con operaciones para realizar con el inicio de sesión
	 */
	
	static Encriptacion encripta = new Encriptacion();
	@SuppressWarnings("static-access")
	static Connection bd = VentanaInicio.bd.devuelveConexion();

	/**
	 * Comprueba si los textField pasados por parámetro están vacíos
	 * @param nombre usuario
	 * @param contraseña
	 * @return boolean vacios
	 */
	public static boolean camposVacios(JTextField nombre, JTextField contraseña) {
		boolean vacios = false;
		String usuario = nombre.getText();
		String scontraseña = contraseña.getText();
		if (usuario.length() == 0 || scontraseña.length() == 0) {
			vacios = true;
		}
		return vacios;
	}
	
	/**
	 * Inicia sesión. Primero comprueba si los campos de usuario y contraseña están vacíos,
	 * luego si el usuario introducido existe y por último si la contraseña introducida en el textField coincide
	 * con la de la base de datos. Para ello le aplicamos la funcion hash al texto del TextField y lo comparamos con la 
	 * almacenada de la base de datos. Si coincide se inicia sesión
	 * 
	 * @param nombre
	 * @param contraseña
	 * @return boolean inicio , indica si se ha iniciado sesión correctamente
	 */
	
	public static boolean iniciaSesion(JTextField nombre, JTextField contraseña) {
		boolean inicio = false;
		if (!camposVacios(nombre, contraseña)) {
			if (existeUsuario(nombre.getText())) {
				String contraIntroducida = encripta.encode(contraseña.getText());
				String contraBD = contraseñaBD(nombre.getText());
				if (contraIntroducida.equals(contraBD)) {
					JOptionPane.showMessageDialog(null, "Iniciando sesión...");
					inicio = true;
				} else {
					JOptionPane.showMessageDialog(null, "La contraseña introducida es incorrecta.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "El usuario introducido no existe. ");
			}
		} else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}

		return inicio;
	}
	
	/**
	 * Abre la ventana que corresponde según el oficio de la persona
	 * En el método oficioBD sacamos el oficio de la persona según su nombre, el cual se encuentra en el textFIeld
	 * @param nombre
	 */
	public static void abreVentana(JTextField nombre) {
		String oficio = oficioBD(nombre.getText());
		if(oficio.equals("MÉDICO")) {
			VentanaMedicos dialog = new VentanaMedicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		
		if(oficio.equals("ENFERMERA")) {
			VentanaEnfermeras dialog = new VentanaEnfermeras();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		
		if(oficio.equals("FARMACIA")) {
			VentanaFarmacia dialog = new VentanaFarmacia();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		
		if(oficio.equals("ADMINISTRATIVO")) {
			VentanaAdministrativos dialog = new VentanaAdministrativos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
	}

	/**
	 * Comprueba si el usuario introducido existe en la base de datos
	 * @param nombre
	 * @return boolean existe
	 */
	public static boolean existeUsuario(String nombre) {
		boolean existe = false;
		try {
			Statement sentencia = bd.createStatement();
		    String sql = "SELECT * FROM AUTENTIFICACION WHERE USUARIO = '" + nombre + "'";
		    ResultSet rs = sentencia.executeQuery(sql);
		    while(rs.next()) {
		    	existe = true;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}
	
	/**
	 * Extrae la contraseña (cifrada) de la base de datos para el usuario que hemos introducido en el textField
	 * @param nombre
	 * @return String contraseña
	 */
	public static String contraseñaBD(String nombre) {
		String contraseña = "";
		
		try {
			Statement sentencia = bd.createStatement();
		    String sql = "SELECT CONTRASEÑA FROM AUTENTIFICACION WHERE USUARIO = '" + nombre + "'";
		    ResultSet rs = sentencia.executeQuery(sql);
		    while(rs.next()) {
		    	contraseña = rs.getString(1);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contraseña;
	}

	/**
	 * Extrae el oficio de la bd del usuario que hemos introducido en el textField. Se usa en el método abreVentana() 
	 * para abrir la ventana que corresponde a cada trabajador
	 * @param nombre
	 * @return String oficio
	 */
	public static String oficioBD(String nombre) {
		String oficio = "";
		try {
			Statement sentencia = bd.createStatement();
			String sql = "select oficio\r\n" + 
					"from personal pe, autentificacion au\r\n" + 
					"where pe.cod_personal = au.cod_personal && usuario = '" + nombre + "'";
			
			ResultSet rs = sentencia.executeQuery(sql);
			while(rs.next()) {
				oficio = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oficio;
	}

	/**
	 * Comprueba que el textField no esté vacío y que el usuario existe.
	 * Si existe guarda en la variable pregunta el string que corresponde al usuario con su pregunta secreta.
	 * Se saca mediante el método preguntaBD
	 * @param nombre
	 * @return String pregunta
	 */
	public static String preguntaSecreta(JTextField nombre) {
		String pregunta = "";
		String usuario = nombre.getText();
		if(usuario.length() != 0) {
			if(existeUsuario(usuario)) {
				pregunta = preguntaBD(usuario);
			}else {
				JOptionPane.showMessageDialog(null, "El usuario introducido no existe");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Introduzca usuario");
		}
		return pregunta;
	}
	
	/**
	 * Saca la pregunta secreta del usuario pasado por parámetro y lo devuelve.
	 * @param usuario
	 * @return String pregunta
	 */
	public static String preguntaBD(String usuario) {
		String pregunta = "";
		try {
			Statement sentencia = bd.createStatement();
			String sql = "select pregunta\r\n" + 
					"from preguntas_personal p, autentificacion a\r\n" + 
					"where p.cod_personal = a.cod_personal && usuario = '" + usuario + "'";
			ResultSet rs = sentencia.executeQuery(sql);
			while(rs.next()) {
				pregunta = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pregunta;
	}

	/**
	 * Comprueba si la respuesta introducida en el txtField respuesta coincide con el que hay en la base de datos
	 * @param nombre
	 * @param respuesta
	 * @return correcta, indica si las contraseñas coinciden (true) o no (false)
	 */
	public static boolean respuestaCorrecta(JTextField nombre, JTextField respuesta) {
		boolean correcta = false;
		if (respuesta.getText().length() != 0) {
			String respuestabd = respuestaBD(nombre);
			if (respuestabd.equals(respuesta.getText())) {
				correcta = true;
			} else {
				JOptionPane.showMessageDialog(null, "Respuesta incorrecta");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Introduzca una respuesta.");
		}
		return correcta;
	}

	/**
	 * Extrae la respuesta del usuario pasado por parámetro (txtNombre) almacenada en la base de datos
	 * y la devuelve
	 * @param nombre
	 * @return respuestabd , string con la respuesta almacenada en la bd
	 */
	public static String respuestaBD(JTextField nombre) {
		String respuestabd = "";
		try {
			Statement sentencia = bd.createStatement();
			String sql ="select respuesta\r\n" + 
					"from preguntas_personal p, autentificacion a\r\n" + 
					"where p.cod_personal = a.cod_personal && usuario = '" + nombre.getText() + "'";

			ResultSet rs = sentencia.executeQuery(sql);
			while(rs.next()) {
				respuestabd = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuestabd;
	}

	
	/**
	 * Realiza el cambio de contraseña cuando el usuario lo solicita desde su área personal.
	 * Comprueba que los textFields  no estén vacíos y que los datos de ambos campos coincidan.
	 * Comprueba que la contraseña anterior coincida con la registrada en la base de datos (es para asegurarnos que la persona 
	 * que cambia la contraseña es la propiertaria).
	 * Si coinciden encripta el texto que se encuentra en el textField contra y lo pasa el método guardaContraseña donde
	 * lo almacena en la base de datos
	 * 
	 * @param contra
	 * @param repiteContra
	 * @param usuario
	 * @return boolean cambio, indica si e cambio se ha hecho o no
	 */
	public static boolean cambioContraseña(JTextField anterior,JTextField contra, JTextField repiteContra ,JTextField usuario) {
		boolean cambio = false;
		if(contra.getText().length() != 0 && repiteContra.getText().length() != 0 && anterior.getText().length() != 0) {
			if(contraseñaCorrecta(anterior,usuario.getText())) {
				if(contra.getText().equals(repiteContra.getText())) {
					String encriptada = encripta.encode(contra.getText());
					guardaContraseña(encriptada,usuario.getText());
					JOptionPane.showMessageDialog(null, "Contraseña actualizada");
					cambio = true;
					contra.setText(""); repiteContra.setText(""); 
				}else {
					JOptionPane.showMessageDialog(null, "La contraseña nueva no coincide con la repetida");
				}
			}else {
				JOptionPane.showMessageDialog(null, "La contraseña antigua no es correcta");
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}
		return cambio;
	}
	
	/**
	 * Lo utilizo para cambiar la contraseña cuando el usuario no se acuerda de esta.
	 * Compruebo que los textField no estén vacios. Si tienen datos recoge la contraseña del textField contra y lo encripta.
	 * Lo manda al método guardaContraseña y muestra mensaje de si ha sio actualizado.
	 * @param contra
	 * @param repiteContra
	 * @param usuario
	 * @return
	 */
	public static boolean contraseñaOlvidada(JTextField contra, JTextField repiteContra ,JTextField usuario) {
		boolean cambio = false;
		if(contra.getText().length() != 0 && repiteContra.getText().length() != 0 ) {
				if(contra.getText().equals(repiteContra.getText())) {
					String encriptada = encripta.encode(contra.getText());
					guardaContraseña(encriptada,usuario.getText());
					JOptionPane.showMessageDialog(null, "Contraseña actualizada");
					cambio = true;
					contra.setText(""); repiteContra.setText(""); 
				}else {
					JOptionPane.showMessageDialog(null, "La contraseña nueva no coincide con la repetida");
				}
		
		}else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}
		return cambio;
	}
	
	/**
	 * Guarda la contraseña encriptada al usuario que corresponde
	 * @param encriptada
	 * @param usuario
	 */
	public static void guardaContraseña(String encriptada, String usuario) {

		try {
			Statement sentencia = bd.createStatement();
			String sql = ("UPDATE AUTENTIFICACION SET CONTRASEÑA = '" + encriptada + "' WHERE USUARIO = '" + usuario + "'");
			sentencia.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Lo utilizo cuando el trabajador va a cambiar su contraseña desde el área personal. Lo que hace este método es recoger la contraseña
	 * introducida en el textField y saca la contraseña del usuario de la base de datos. (Está encriptada).
	 * Encripta también lo que el usuario ha introducido en el JTextField contraseña y si no coinciden devuelve false.
	 * Si los strings coinciden devuelve true, lo que significa que es correcta y puede cambiarla por una nueva.
	 * @param contraseña
	 * @param usuario
	 * @return
	 */
	public static boolean contraseñaCorrecta(JTextField contraseña, String usuario) {
		boolean correcta = false;
		String encriptada = encripta.encode(contraseña.getText());
	    String bd = contraseñaBD(usuario);
	    
	    if(bd.equals(encriptada)) {
	    	correcta = true;
	    }
	    
		return correcta;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * En este apartado voy a desarrollar una serie de métodos para comprobar la robustez de la contraseña de los trabajadores.
	 * Si no introducen contraseñas lo suficientemente seguras no les dejaré cambiarlas o recuperarlas en caso de pérdida u olvido.
	 */
	
	/**
	 * Comprueba si la cadena que le pasamos contiene algún número
	 * Pasa el string a un array de carácteres y pregunta si cada carácter es un número. Itera sobre el array hasta que encuentra
	 * un caracter numérico y retorna true
	 * @param clave
	 * @return boolean tieneNumero
	 */
	public boolean tieneNumeros(String clave) {
		boolean tieneNumero = false;
		char[] array = clave.toCharArray();
		for(int i=0;i<clave.length();i++) {
			if(Character.isDigit(array[i])) {
				tieneNumero = true;
				break;
			}
		}
		
		return tieneNumero;
	}
	
	/**
	   Comprueba si la cadena que le pasamos contiene alguna letra
	 * Pasa el string a un array de carácteres y pregunta si cada carácter es una letra. Itera sobre el array hasta que encuentra
	 * una letra y retorna true
	 * @param clave
	 * @return boolean tieneLetras
	 */
	public boolean tieneLetras(String clave) {
		boolean tieneLetras = false;
		char[] array = clave.toCharArray();
		for(int i=0;i<clave.length();i++) {
			if(Character.isLetter(array[i])) {
				tieneLetras = true;
				break;
			}
		}
		return tieneLetras;
	}
	
	/**
	 * Comprueba si la cadena que le pasamos contiene alguna minúscula
	 * Pasa el string a un array de carácteres y pregunta si cada carácter es una minúscula. Itera sobre el array hasta que encuentra
	 * una minúscula y retorna true
	 * @param clave
	 * @return
	 */
	public boolean tieneMinusculas(String clave) {
		boolean tieneMinus = false;
		char[] array = clave.toCharArray();
		for(int i=0;i<clave.length();i++) {
			if(Character.isLowerCase(array[i])) {
				tieneMinus = true;
				break;
			}
		}
		return tieneMinus;
	}
	
	/**
	 * Comprueba si la cadena que le pasamos contiene alguna mayúscula
	 * Pasa el string a un array de carácteres y pregunta si cada carácter es una mayúscula. Itera sobre el array hasta que encuentra
	 * una mayúscula y retorna true
	 * @param clave
	 * @return
	 */
	public boolean tieneMayusculas(String clave) {
		boolean tieneMayus = false;
		char[] array = clave.toCharArray();
		for(int i=0;i<clave.length();i++) {
			if(Character.isUpperCase(array[i])) {
				tieneMayus = true;
				break;
			}
		}
		return tieneMayus;
	}
	
	
	/**
	 * Comprueba lo robusta que es una clave. Para que sea 100% segura tiene que contener números, letras mayúsculas, minúsculas,
	 * y más de 8 carácteres de longitud. En seguridad vamos almacenando el % de seguridad que tiene y lo retornamos en un int.
	 * @param clave
	 * @return int seguridad
	 */
	public int seguridadClave(String clave) {
		int seguridad = 0;
		   if (clave.length()!=0){
			      if (tieneNumeros(clave) && tieneLetras(clave)){
			         seguridad += 30;
			      }
			      if (tieneMayusculas(clave) && tieneMinusculas(clave)){
			         seguridad += 30;
			      }
			      if (clave.length() >= 4 && clave.length() <= 5){
			         seguridad += 10;
			      }else{
			         if (clave.length() >= 6 && clave.length() <= 8){
			            seguridad += 30;
			         }else{
			            if (clave.length() > 8){
			               seguridad += 40;
			            }
			         }
			      }
			   }
		return seguridad;
	}
}
