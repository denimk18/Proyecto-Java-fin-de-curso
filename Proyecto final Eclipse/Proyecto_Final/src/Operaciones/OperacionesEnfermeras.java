package Operaciones;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Ventanas.VentanaInicio;
import Ventanas.Enfermeras.IncidenciaPaciente;
import Ventanas.Farmacias.Pedido;
import dao.DiagnosticoPaciente;
import dao.FarmacosEnfermera;
import dao.FarmacosEnfermeraDAO;
import dao.IncidenciasPaciente;
import dao.IncidenciasPacienteDAO;
import dao.PedidoFarmacia;
import factory.DAOFactory;

public class OperacionesEnfermeras {

	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	static DAOFactory bdFactory = VentanaInicio.getDAO();
	static FarmacosEnfermeraDAO farmacosDAO = bdFactory.getFarmacosEnfermeraDAO();
	static IncidenciasPacienteDAO incidenciasDAO = bdFactory.getIncidenciasPacienteDAO();

	/**
	 * Inserta los datos que mete la enfermera al administrarle los f�rmacos a un
	 * paciente. Comprueba que todos los campos NO est�n vac�os.
	 *  Una vez est� todo bien procede a insertar los datos.
	 * Si no hay suficiente stock avisa de ello y no hace la inserci�n.
	 * @param paciente
	 * @param medico
	 * @param fecha
	 */
	public void insertaFarmacosAdministrados(JComboBox paciente, JComboBox medico, String fecha, JTextField cantidad) {
		if (paciente.getSelectedIndex() != 0 && paciente.getSelectedIndex() != -1) {
			if (medico.getSelectedIndex() != 0 && medico.getSelectedIndex() != -1) {
				if (cantidad.getText().length() != 0) {
					Date fec = convierteFecha(fecha);

					FarmacosEnfermera farmaco = new FarmacosEnfermera(codigoPaciente(paciente),
							codigoMedicamento(medico), Integer.parseInt(cantidad.getText()), fec);
					if (farmacosDAO.insertaFarmacos(farmaco)) {
						JOptionPane.showMessageDialog(null, "Datos guardados");
						paciente.setSelectedIndex(0);
						medico.setSelectedIndex(0);
						cantidad.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "No hay suficiente stock del medicamento seleccionado");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Inserte cantidad");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Seleccione medicaci�n");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	}
	
	
	/**
	 * Pinta el listado de todos los f�rmacos administrados a todos los pacientes
	 * @param txt
	 */
	public void listadoFarmacos(DefaultTableModel dtm) {
		ArrayList<FarmacosEnfermera> listado = farmacosDAO.listadoFarmacosAdministrados();
		Iterator it = listado.iterator();
		
		if(listado.size() > 0) {				
			while(it.hasNext()) {
				FarmacosEnfermera f = (FarmacosEnfermera) it.next();
				Object[] fila = { nombrePaciente(f.getCod_paciente()),						
						nombreMedicamento(f.getCod_farmaco()),
						f.getFecha_administracion(),
						f.getCantidad()
						};
				
				//llenamos DefaultTableModel
				dtm.addRow(fila);
				
			}
		
		}
		
	
	}
	
	
	
	/**
	 * Pinta todos los f�rmacos administrados al paciente que se consulta en un jtable
	 * Por eso se pasa un DefaultTableModel
	 */
	
	public void farmacosPaciente(JComboBox cmb, DefaultTableModel dtm) {
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			ArrayList<FarmacosEnfermera> listado = farmacosDAO.pacienteFarmacoAdministrado(codigoPaciente(cmb));
			if(listado.size() > 0) {
				Iterator it = listado.iterator();
				while(it.hasNext()) {
					FarmacosEnfermera f = (FarmacosEnfermera) it.next();
					Object[] fila = { nombrePaciente(f.getCod_paciente()),						
							nombreMedicamento(f.getCod_farmaco()),
							f.getFecha_administracion(),
							f.getCantidad()
							};
					dtm.addRow(fila);
				}
			}else {
				JOptionPane.showMessageDialog(null,"No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null,"Seleccione paciente");
		}
	}
	
	
	/**
	 * M�todo para insertar la incidencia.
	 * Comprueba primero que no haya ning�n campo vac�o.
	 * Comprueba si hay suficiente stock del medicamento seleccionado que se va a administrar al paciente. Si no hay stock pinta mensaje.
	 * Si hay stock inserta la incidencia siempre y cuando no haya una incidencia con ese c�digo. A la vez hace la carga del medicamento. Ser�a lo mismo que ir
	 * al apartado farmacos administrados y hacerlo manualmente pero se hace desde aqu� para facilitar la labor.
	 * @param paciente
	 * @param medicacion
	 * @param txtCodigo
	 * @param causa
	 * @param cura
	 * @param familiares
	 * @param cantidad
	 * @param fecha
	 */
	public boolean insertaIncidencia(JComboBox paciente, JComboBox medicacion, JTextField txtCodigo, JTextArea causa,
			JTextArea cura, JTextArea familiares, JTextField cantidad, String fecha) {

		boolean insertado = false;
		if (paciente.getSelectedIndex() != 0 && paciente.getSelectedIndex() != -1) {
			if (medicacion.getSelectedIndex() != 0 && medicacion.getSelectedIndex() != -1) {
				if (txtCodigo.getText().length() != 0) {
					if (causa.getText().length() != 0 && cura.getText().length() != 0
							&& familiares.getText().length() != 0 && cantidad.getText().length() != 0) {

						Date fec = convierteFecha(fecha);
						IncidenciasPaciente inci = new IncidenciasPaciente(Integer.parseInt(txtCodigo.getText()),
								codigoPaciente(paciente), codigoMedicamento(medicacion),
								Integer.parseInt(cantidad.getText()), fec, causa.getText(), cura.getText(),
								familiares.getText());

						FarmacosEnfermera f = new FarmacosEnfermera(codigoPaciente(paciente),
								codigoMedicamento(medicacion), Integer.parseInt(cantidad.getText()), fec);

						if (farmacosDAO.insertaFarmacos(f)) {
							if (incidenciasDAO.insertaIncidencia(inci)) {
								JOptionPane.showMessageDialog(null, "Incidencia insertada");
								insertado = true;
							} else {
								JOptionPane.showMessageDialog(null, "Ya existe una incidencia con ese c�digo");
							}
						} else {
							JOptionPane.showMessageDialog(null, "No hay suficiente stock del medicamento seleccionado");
						}

					} else {
						JOptionPane.showMessageDialog(null, "No puede haber campos vac�os");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Introduzca c�digo de incidencia");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione medicaci�n");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}

		return insertado;
	}
	
	/**
	 * Pinta un listado de todas las incidencias de todos los pacientes en un JTable. Por eso se le pasa un defaultTableModel
	 * @param dtm
	 */
	public void listaIncidencias(DefaultTableModel dtm) {
		ArrayList<IncidenciasPaciente> lista = incidenciasDAO.listadoIncidencias();
		Iterator it = lista.iterator();
		
		if(lista.size() >0) {
			while(it.hasNext()) {
				IncidenciasPaciente inci = (IncidenciasPaciente) it.next();
				
				Object[] fila = { inci.getCod_incidencia(),						
						nombrePaciente(inci.getCod_paciente()),
						nombreMedicamento(inci.getCod_medicamento()),
						inci.getCantidad(),
						inci.getFecha(),
						inci.getCausa(),
						inci.getCura(),
						inci.getComunicado_familiares()
						};
				dtm.addRow(fila);
				
				
			}
		}
	}
	
	
	/**
	 * Pinta todas las incidencias de un paciente. Por eso se pasa un dtm y un comboBox
	 * @param dtm
	 * @param cmb
	 */
	public void incidenciasPaciente(DefaultTableModel dtm, JComboBox cmb) {
		
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			ArrayList<IncidenciasPaciente> lista = incidenciasDAO.incidenciasPaciente(codigoPaciente(cmb));
			Iterator it = lista.iterator();
			
			if(lista.size() >0) {
				while(it.hasNext()) {
					IncidenciasPaciente inci = (IncidenciasPaciente) it.next();
					
					Object[] fila = { inci.getCod_incidencia(),						
							nombrePaciente(inci.getCod_paciente()),
							nombreMedicamento(inci.getCod_medicamento()),
							inci.getCantidad(),
							inci.getFecha(),
							inci.getCausa(),
							inci.getCura(),
							inci.getComunicado_familiares()
							};
					dtm.addRow(fila);
					
					
				}
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	
	}
	
	
	/**
	 * Inserta la solicitud de la enfermera en la tabla solicitud_medicamento cuando le salta el aviso de que no hay suficiente
	 * stock de alg�n medicamento o cuando ve que el m�dico receta un medicamento que no est� en stock.
	 * Comprueba que no haya ning�n campo vac�o y que la fecha sea correcta. Si todo est� ok completa la solicitud.
	 * @param nombre
	 * @param cantidad
	 * @param fecha
	 */
	public void insertaSolicitudFarmacos(JTextField nombre, JTextField cantidad, JTextField fecha) {
		if(nombre.getText().length() != 0 && cantidad.getText().length() != 0 && fecha.getText().length() != 0) {
			Date fec = convierteFecha(fecha.getText());
			if(validarFecha(fecha.getText())) {
				PedidoFarmacia p = new PedidoFarmacia(nombre.getText(),Integer.parseInt(cantidad.getText()), fec);
				if(farmacosDAO.solicitaFarmacos(p)) {
					JOptionPane.showMessageDialog(null, "Solicitud enviada");
					nombre.setText(""); cantidad.setText(""); fecha.setText("");
				}else {
					JOptionPane.showMessageDialog(null,"No se ha podido completar la solicitud");
				}
			}else {
				JOptionPane.showMessageDialog(null, "La fecha es incorrecta");
			}
		}else {
			JOptionPane.showMessageDialog(null,"No puede haber campos vac�os");
		}
	}
	
	/**
	 * Recibe un string y lo pasa a sql.Date para despu�s devolverlo.	 * 
	 * @param fecha
	 * @return Date
	 */
	public static Date convierteFecha(String fecha) {
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
	 * M�todo que comprueba si una fecha es v�lida
	 * 
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
	 * M�todo que devuelve el codigo del f�rmaco seleccionado en el comboBox
	 * 
	 * @param cmb
	 * @return
	 */
	public int codigoMedicamento(JComboBox cmb) {
		int codigo = 0;
		String resultado = cmb.getSelectedItem().toString();
		String[] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);
		return codigo;
	}

	/**
	 * Recibe un objeto comboBox , el cual est� relleno de datos del paciente y
	 * devuelve un entero que corresponde al c�digo del paciente seleccionado.
	 * 
	 * @param cmb
	 * @return int codigoPaciente
	 */
	public int codigoPaciente(JComboBox cmb) {
		int codigo = 0;
		String resultado = cmb.getSelectedItem().toString();
		String[] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);
		return codigo;
	}
	
	
	/**
	 * Devuelve el nombre del paciente que corresponde a ese c�digo
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
}
