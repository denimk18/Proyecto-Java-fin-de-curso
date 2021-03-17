package Operaciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Ventanas.VentanaAutentificacion;
import Ventanas.VentanaInicio;
import dao.*;
import dao.DiagnosticoPaciente;
import dao.Paciente;
import factory.DAOFactory;

public class OperacionesMedicos {

	/**
	 * Con connection obtengo la conexi�n que se abre en la ventanaInicio
	 * Con DAOFactory obtengo el objeto que se crea al iniciar la aplicaci�n para poder realizar las operaciones de alta, baja...
	 * DiagnosticoPacienteDAO es la clase de la librer�a DAOProyectoFinal que contiene todas las operaciones que realizan los m�dicos
	 */
	static Connection bd = VentanaInicio.bd.devuelveConexion();
	static DAOFactory bdFactory = VentanaInicio.getDAO();
    static DiagnosticoPacienteDAO diagnosticoDAO = bdFactory.getDiagnosticoPacienteDAO();
    static String usuario = VentanaAutentificacion.usuario;
    
    /**
     * M�todo para dar de alta los pacientes. Se comprueba si hay alg�n campo vac�o con el m�todo camposVacios.
     * Si devuelve false se realiza el alta. Para ello convertimos el string fecha a sql.Date y creamos el objeto paciente.
     * Dicho objeto lo pasamos al m�todo altaPacientes(Paciente p)
     * Dicho m�todo nos devuelve 0 (lo ha insertado bien), -1 (el c�digo introducido ya existe)
     */
	public void altaPacientes(JTextField codPaciente, JTextField nombre, JTextField direccion, JTextField telefono, String fecha, JComboBox medico) {
		if(!camposVacios(codPaciente,nombre,direccion,telefono,medico)) {
			int cod = Integer.parseInt(codPaciente.getText());
			Date fechaAlta = convierteFecha(fecha);
			Paciente p = new Paciente(cod,nombre.getText(),direccion.getText(),telefono.getText(),fechaAlta,codigoMedico(medico));
			int alta = diagnosticoDAO.altaPacientes(p);
			
			if(alta == 0) {
				JOptionPane.showMessageDialog(null, "Paciente dado de alta correctamente.");
				codPaciente.setText(""); nombre.setText(""); direccion.setText(""); telefono.setText("");  medico.setSelectedIndex(0);
			}
			
			if(alta == -1) {
				JOptionPane.showMessageDialog(null, "Ya existe un paciente registrado con el c�digo " + codPaciente.getText());
			}
			
		
		}
	}
		
	
	
	/**
	 * M�todo para dar de baja a un paciente. Se pasa por par�metro un comboBox el cual est� lleno de los pacientes.
	 * Se llama al m�todo bajaPacientes de la librer�a que devuelve un bool. Este bool indica si se ha eliminado o no
	 * @param cmb
	 */
	public void bajaPaciente(JComboBox cmb) {
		if(cmb.getSelectedIndex() != -1 && cmb.getSelectedIndex() != 0) {
			if(diagnosticoDAO.bajaPacientes(codigoPaciente(cmb))) { //codigoPaciente es un m�todo que saca el cod_paciente seleccionado del cmb
				JOptionPane.showMessageDialog(null, "Paciente dado de baja con �xito");
			}else {
				JOptionPane.showMessageDialog(null,"Error, no se ha podido dar de baja");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente a eliminar");
		}
	}
	
	/**
	 * Obtiene todos los pacientes que hay en la base de datos y los pinta en un jtable
	 * @param dtm
	 */
	public void listadoPacientes(DefaultTableModel dtm) {
		ArrayList<Paciente> pacientes = diagnosticoDAO.listadoPacientes();
		Iterator it = pacientes.iterator();
		
		

		while(it.hasNext()) {
			Paciente p = (Paciente) it.next();
			
			Object[] fila = { p.getCod_paciente(),
					p.getNombre(),
					p.getDireccion(),
					p.getTelefono_urgencia(),
					p.getFecha_alta(),
					nombreMedico(p.getCod_medico())			
					};
			dtm.addRow(fila);
		}
	}
	
	/**
	 * Pinta los datos del paciente seleccionado de un comboBox en los textField
	 * @param cmb
	 * @param direccion
	 * @param telefono
	 * @param fecha
	 * @param medico
	 */
	public void consultaPaciente(JComboBox cmb,  JTextField direccion, JTextField telefono, JTextField fecha, JComboBox medico) {
		
		if(cmb.getSelectedIndex() != -1 && cmb.getSelectedIndex() != 0) {
			Paciente p = diagnosticoDAO.consultaPaciente(codigoPaciente(cmb));
			direccion.setText(p.getDireccion());
			telefono.setText(p.getTelefono_urgencia());
			fecha.setText(p.getFecha_alta().toString());
			seleccionaMedico(medico, p.getCod_medico());
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente a consultar");
		}
		
	}
	
	
	/**
	 * Pinta los datos del paciente seleccionado en un JTable
	 * @param cmb
	 * @param direccion
	 * @param telefono
	 * @param fecha
	 * @param medico
	 */
	public void consultaPacienteTabla(JComboBox cmb, DefaultTableModel dtm) {
		
		if(cmb.getSelectedIndex() != -1 && cmb.getSelectedIndex() != 0) {
			Paciente p = diagnosticoDAO.consultaPaciente(codigoPaciente(cmb));
			Object[] fila = { p.getCod_paciente(),
					p.getNombre(),
					p.getDireccion(),
					p.getTelefono_urgencia(),
					p.getFecha_alta(),
					nombreMedico(p.getCod_medico())			
					};
			dtm.addRow(fila);
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente a consultar");
		}
		
	}
	
	
	/**
	 * Modifica los datos del paciente
	 * @param cmb
	 * @param direccion
	 * @param telefono
	 * @param fecha
	 * @param medico
	 */
	public void modificaPaciente(JComboBox cmb,  JTextField direccion, JTextField telefono, String fecha, JComboBox medico) {
		if (cmb.getSelectedIndex() != -1 && cmb.getSelectedIndex() != 0) {
			if (medico.getSelectedIndex() != 0 && medico.getSelectedIndex() != -1) {
				if (direccion.getText().length() != 0 && telefono.getText().length() != 0) {
					Date fec = convierteFecha(fecha);
					Paciente p = new Paciente(codigoPaciente(cmb), nombrePaciente(cmb), direccion.getText(),
							telefono.getText(), fec, codigoMedico(medico));
					if (diagnosticoDAO.modificaPacientes(p.getCod_paciente(), p)) {
						JOptionPane.showMessageDialog(null, "Paciente modificado con �xito");
					} else {
						JOptionPane.showMessageDialog(null, "No se ha podido modificar el paciente");

					}
				} else {
					JOptionPane.showMessageDialog(null, "No puede haber campos vac�os");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un m�dico");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un paciente");
		}
	}
	
	/**
	 * Inserta tratamiento para un paciente. Comprueba que no haya campos vac�os y seg�n lo que devuelva el m�todo
	 * insertaTratamientos se notifica que ha pasado
	 * @param codigo
	 * @param cantidad
	 * @param paciente
	 * @param farmaco
	 * @param diagnostico
	 */
	public void insertaTratamiento(JTextField codigo, JTextField cantidad, JComboBox paciente, JComboBox farmaco,
			JTextArea diagnostico, String fecha) {
		if (paciente.getSelectedIndex() != 0 || paciente.getSelectedIndex() != -1) {
			if (farmaco.getSelectedIndex() != 0 || farmaco.getSelectedIndex() != -1) {
				if (codigo.getText().length() != 0 && cantidad.getText().length() != 0
						&& diagnostico.getText().length() != 0) {
					Date fec = convierteFecha(fecha);
					DiagnosticoPaciente tratamiento = new DiagnosticoPaciente(Integer.parseInt(codigo.getText()),
							codigoPaciente(paciente), nombreMedicamento(farmaco), Integer.parseInt(cantidad.getText()),
							diagnostico.getText(), fec);

					int resultado = diagnosticoDAO.insertaTratamientos(codigoPaciente(paciente), tratamiento);
					if (resultado == 1) {
						JOptionPane.showMessageDialog(null, "El paciente no existe");
					}

					if (resultado == -1) {
						JOptionPane.showMessageDialog(null, "Ya existe un diagn�stico con ese c�digo");
					}

					if (resultado == 0) {
						JOptionPane.showMessageDialog(null, "Tratamiento insertado con �xito");
						codigo.setText("");
						cantidad.setText("");
						paciente.setSelectedIndex(0);
						farmaco.setSelectedIndex(0);
						diagnostico.setText("");
					}

				} else {
					JOptionPane.showMessageDialog(null, "No puede haber campos vac�os");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione medicaci�n");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	}
	
	/**
	 * M�todo que pinta las recetas administradas de un paciente que est� seleccionado en el comboBox seleccionado.
	 * @param textArea
	 * @param paciente
	 */
	public void listadoRecetas(DefaultTableModel dtm, JComboBox paciente) {
		if(paciente.getSelectedIndex() != 0 && paciente.getSelectedIndex() != -1) {
			int codPaciente = codigoPaciente(paciente);
			ArrayList<Receta> recetas = diagnosticoDAO.listadoRecetas(codPaciente);
			Iterator it = recetas.iterator();
			
			if(recetas.size() > 0) {
			
			while(it.hasNext()) {
				Receta r = (Receta) it.next();
				Object[] fila = {nombrePaciente(paciente),
						nombreMedicamento(r.getCod_medicamento()),
						r.getFecha_administracion(),
						r.getAdministracion(),							
						};
				dtm.addRow(fila);
			}
			
			}else {
				JOptionPane.showMessageDialog(null,"Sin recetas");
			}
		}else {
			JOptionPane.showMessageDialog(null,"Seleccione un paciente");
		}
	}
	

	
	
	/**
	 * M�todo que inserta recetas. Comprueba que no haya ning�n campo vac�o y que la fecha sea correcta. Si todo esta ok,
	 * inserta la receta. Si se inserta bien vac�a los campos para poder insertar otra si fuera necesario.
	 * 
	 * @param paciente
	 * @param medicacion
	 */
	public void insertaReceta(JComboBox paciente, JComboBox medicacion, JTextArea textArea, String fecha) {
		if (paciente.getSelectedIndex() != 0 && paciente.getSelectedIndex() != -1) {
			if (medicacion.getSelectedIndex() != 0 && medicacion.getSelectedIndex() != -1) {
				if (textArea.getText().length() != 0) {
					Date fec = convierteFecha(fecha);
						Receta receta = new Receta(codigoPaciente(paciente), codigoMedicamento(medicacion),
								textArea.getText(), fec);
						int resultado = diagnosticoDAO.insertaRecetas(receta);
						if (resultado == 0) {
							JOptionPane.showMessageDialog(null, "Receta guardada");
							paciente.setSelectedIndex(0);
							medicacion.setSelectedIndex(0);
							textArea.setText("");
						}

				} else {
					JOptionPane.showMessageDialog(null, "Inserte forma de administraci�n");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione medicaci�n");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	}

	/**
	 * Pinta por pantalla un listado de todos los tratamientos de todos los pacientes.
	 * @param dtm
	 */
	public void listadoTratamientos(DefaultTableModel dtm) {
		ArrayList<DiagnosticoPaciente> lista = diagnosticoDAO.listadoTratamientos();
		if(lista.size() > 0) {
			Iterator it = lista.iterator();
			while(it.hasNext()) {
				DiagnosticoPaciente p = (DiagnosticoPaciente) it.next();
				Object[] fila = {p.getCod_diagnostico(),
						nombrePacienteCod(p.getCod_paciente()),
						p.getNombre_farmaco(),
						p.getCantidad(),
						p.getDiagnostico(),
						p.getFecha_diagnostico()
						};
				dtm.addRow(fila);
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}
	
	
	/**
	 * Pinta en un jtable todos los tratamientos y di�gnosticos que ha recibido el paciente seleccionado en el comboBox
	 * @param dtm
	 * @param cmb
	 */
	public void listadoTratamientosPaciente(DefaultTableModel dtm, JComboBox cmb) {
		ArrayList<DiagnosticoPaciente> lista = diagnosticoDAO.consultaTratamientos(codigoPaciente(cmb));
		if(lista.size() > 0) {
			Iterator it = lista.iterator();
			while(it.hasNext()) {
				DiagnosticoPaciente p = (DiagnosticoPaciente) it.next();
				Object[] fila = {p.getCod_diagnostico(),
						p.getCod_paciente(),
						p.getNombre_farmaco(),
						p.getCantidad(),
						p.getDiagnostico(),
						p.getFecha_diagnostico()
						};
				dtm.addRow(fila);
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}
	
	

	/**
	 * Comprueba si hay alg�n campo vac�o y si hay alg�n elemento seleccionado en el comboBox
	 * @param codPaciente
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 * @param fecha
	 * @param medico
	 * @return boolean vacios
	 */
	
	public boolean camposVacios(JTextField codPaciente, JTextField nombre, JTextField direccion, JTextField telefono, JComboBox medico) {
		boolean vacios = false;
		if(codPaciente.getText().length() == 0 || nombre.getText().length() == 0 || direccion.getText().length() == 0 
				|| telefono.getText().length() == 0 ||  medico.getSelectedIndex() == -1 ) {
			vacios = true;
			JOptionPane.showMessageDialog(null, "No puede haber campos vac�os");
		}
		
		if(medico.getSelectedIndex() == 0) {
			vacios = true;
			JOptionPane.showMessageDialog(null, "Seleccione un m�dico");
		}
		return vacios;
	}
	
	/**
	 * Recibe un string y lo pasa a sql.Date para despu�s devolverlo.
	 * @param fecha
	 * @return Date
	 */
	 public static Date convierteFecha(String fecha)
	    {
				 java.sql.Date fecFormatoDate = null;
				 try {
				       SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
				       fecFormatoDate = new java.sql.Date(sdf.parse(fecha).getTime());
				 } catch (Exception ex) {
					 JOptionPane.showMessageDialog(null, "La fecha introducida no es correcta. Formato: yyyy-MM-dd");
				 }
				 
				 return fecFormatoDate;
	    }

	 
	/**
	 * Recibe un objeto comboBox , el cual est� relleno del personal m�dico y devuelve un entero que corresponde
	 * al c�digo del m�dico seleccionado.
	 * @param cmb
	 * @return int codigoMedico
	 */
	public int codigoMedico(JComboBox cmb) {
		int codigo = 0;
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);		
		return codigo;
	}
	
	
	 
		/**
		 * Recibe un objeto comboBox , el cual est� relleno de datos del paciente y devuelve un entero que corresponde
		 * al c�digo del paciente seleccionado.
		 * @param cmb
		 * @return int codigoPaciente
		 */
	public int codigoPaciente(JComboBox cmb) {
		int codigo = 0;
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);		
		return codigo;
	}
	
	/**
	 * Devuelve el nombre del m�dico que corresponde al c�digo que se le pasa.
	 * @param codMedico
	 * @return
	 */
	public String nombreMedico(int codMedico) {
		String nombre = "";
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre, apellidos from personal where cod_personal = " + codMedico);
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
	 * Devuelve el nombre del medicamento que corresponde al c�digo que se le pasa.
	 * @param codMedico
	 * @return
	 */
	public String nombreMedicamento(int codMedicamento) {
		String nombre = "";
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select nombre from inventario_farmacia where cod_medicamento = " + codMedicamento);
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
	 * Separa el nombre y el c�digo paciente del comboBox que se pasa por par�metro. Devuelve el nombre del paciente
	 * @param cmb
	 * @return
	 */
	public String nombrePaciente(JComboBox cmb) {
		String nombre = "";
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		nombre = arreglo[1];
		return nombre;
	}
	
	
	/**
	 * Devuelve el nombre del paciente que corresponde al c�digo que se le pasa
	 * @param cod
	 * @return
	 */
	public String nombrePacienteCod(int cod) {
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
	 * Deja seleccionado el elemento que corresponde al c�digo que se pasa en el combobox
	 * @param cmb
	 * @param cod
	 */
	public void seleccionaMedico(JComboBox cmb, int cod) {
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select  nombre, apellidos from personal where cod_personal = " + cod);
			while(rs.next()) {
				String s =cod + "/" + rs.getString(1) + " " + rs.getString(2);
				cmb.setSelectedItem(s);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * M�todo que comprueba si una fecha es v�lida
	 * @param fecha
	 * @return boolean
	 */
	public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

	/**
	 * M�todo que devuelve el nombre del f�rmaco seleccionado en el comboBox
	 * @param cmb
	 * @return
	 */
	public String nombreMedicamento(JComboBox cmb) {
		String nombre = "";
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		nombre = arreglo[1];
		return nombre;
	}
	
	/**
	 * M�todo que devuelve el codigo del f�rmaco seleccionado en el comboBox
	 * @param cmb
	 * @return
	 */
	public int codigoMedicamento(JComboBox cmb) {
		int codigo = 0;
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);
		return codigo;
	}
}
