package Operaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Ventanas.VentanaAutentificacion;
import Ventanas.VentanaInicio;

public class OperacionesAreaPersonal {

	static Connection bd = VentanaInicio.bd.devuelveConexion();
	static String usuario = VentanaAutentificacion.usuario;

	/**
	 * Pinta los datos de la persona que ha iniciado sesión.
	 * 
	 * @param cod
	 * @param nombre
	 * @param dni
	 * @param oficio
	 * @param direccion
	 * @param titulacion
	 * @param cursos
	 */

	public void areaPersonal(JTextField cod, JTextField nombre, JTextField dni, JTextField oficio, JTextField direccion,
			JTextField titulacion, JTextArea cursos) {

		if (!usuario.equals("")) {
			try {
				Statement sentencia = bd.createStatement();
				String sql = "select p.*\r\n" + "from personal p , autentificacion a\r\n"
						+ "where p.cod_personal = a.cod_personal && a.usuario = '" + usuario + "'";

				ResultSet rs = sentencia.executeQuery(sql);
				while (rs.next()) {
					cod.setText(String.valueOf(rs.getInt(1)));
					nombre.setText(rs.getString(2) + " " + rs.getString(3));
					dni.setText(rs.getString(4));
					oficio.setText(rs.getString(5));
					direccion.setText(rs.getString(6));
					titulacion.setText(rs.getString(9));
					String[] array = rs.getString(7).split(",");
					pintaCursos(array, cursos);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pinta en el textArea los cursos que tiene cada empleado hechos. Asociado al
	 * método areaPersonal
	 * 
	 * @param array
	 * @param cursos
	 */
	public void pintaCursos(String[] array, JTextArea cursos) {
		if (array.length > 0) {
			cursos.append(" ------------------------------------------------------------------\n");

			for (int i = 0; i < array.length; i++) {
				String s = String.format(" %5s %n", array[i]);
				cursos.append(s);
			}
			cursos.append(" ------------------------------------------------------------------\n");

		}
	}

	/**
	 * Comprueba si hay algún campo vacío y si hay algún elemento seleccionado en el
	 * comboBox
	 * 
	 * @param codPaciente
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 * @param fecha
	 * @param medico
	 * @return boolean vacios
	 */
	public boolean camposVacios(JTextField codPaciente, JTextField nombre, JTextField direccion, JTextField telefono,
			JTextField fecha, JComboBox medico) {
		boolean vacios = false;
		if (codPaciente.getText().length() == 0 || nombre.getText().length() == 0 || direccion.getText().length() == 0
				|| telefono.getText().length() == 0 || fecha.getText().length() == 0
				|| medico.getSelectedIndex() == -1) {
			vacios = true;
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}

		if (medico.getSelectedIndex() == 0) {
			vacios = true;
			JOptionPane.showMessageDialog(null, "Seleccione un médico");
		}
		return vacios;
	}
}
