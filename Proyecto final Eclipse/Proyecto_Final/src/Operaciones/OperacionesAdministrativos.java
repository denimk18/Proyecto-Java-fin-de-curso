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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Ventanas.VentanaInicio;
import dao.InventarioAdministrativo;
import dao.InventarioRecepcionAdminDAO;
import dao.PedidosAdministrativo;
import dao.PedidosAdministrativoDAO;
import dao.RecepcionMedicacion;
import dao.RecepcionOficina;
import dao.Visitas;
import dao.VisitasDAO;
import factory.DAOFactory;
import implementacion.InventarioRecepcionAdminImpl;
import implementacion.PedidosAdministrativoImpl;
import implementacion.VisitasImpl;

public class OperacionesAdministrativos {

	static Connection bd = VentanaInicio.bd.devuelveConexion();
	static DAOFactory bdFactory = VentanaInicio.getDAO();
	static VisitasDAO visitaDAO = new VisitasImpl();
	static PedidosAdministrativoDAO pedidosDAO = new PedidosAdministrativoImpl();
	static InventarioRecepcionAdminDAO invenDAO = new InventarioRecepcionAdminImpl();
	
	/**
	 * Método para insertar visitas en la tabla visitas. Comprueba que la fecha sea correcta y que no haya ningún elemento vacío.
	 * Si todo está ok inserta registro.
	 * @param nombre
	 * @param dni
	 * @param fecha
	 * @param cod
	 */
	public void insertaVisita(JTextField nombre ,JTextField dni, String fecha, JComboBox cod) {
		if(cod.getSelectedIndex() != 0 && cod.getSelectedIndex() != -1) {
			if(nombre.getText().length() != 0 && dni.getText().length() != 0) {
				Date fec = convierteFecha(fecha);
					Visitas v = new Visitas(nombre.getText(), dni.getText(), fec, codigoPaciente(cod));
					if(visitaDAO.insertaVisitas(v)) {
						JOptionPane.showMessageDialog(null, "Registro insertado");
						nombre.setText(""); dni.setText(""); cod.setSelectedIndex(0);
					}else {
						JOptionPane.showMessageDialog(null, "Error");
					}
			
			}else {
				JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	}
	
	
	/**
	 * Pinta el listado de visitas en un jtable
	 * @param dtm
	 */
	public void listadoVisitas(DefaultTableModel dtm) {
		ArrayList<Visitas> lista = visitaDAO.listadoVisitas();
		Iterator it = lista.iterator();
		
		if(lista.size() > 0 ) {
			while(it.hasNext()) {
				Visitas v = (Visitas) it.next();
				Object[] fila = {
						v.getNombre_familiar(),
						v.getDni(),
						v.getFecha(),
						nombrePaciente(v.getCod_paciente())
				};
				
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}
	
	
	/**
	 * Pinta las visitas del paciente seleccionado en el comboBox que se le pasa en un jtable. Para eso utiliza DefaultTableModel
	 * @param cmb
	 * @param dtm
	 */
	public void consultaVisita(JComboBox cmb, DefaultTableModel dtm) {

		if (cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			ArrayList<Visitas> lista = visitaDAO.consultaVisita(codigoPaciente(cmb));
			Iterator it = lista.iterator();
			
			if(lista.size() > 0 ) {
				while(it.hasNext()) {
					Visitas v = (Visitas) it.next();
					Object[] fila = {
							v.getNombre_familiar(),
							v.getDni(),
							v.getFecha(),
							nombrePaciente(v.getCod_paciente())
					};
					
					dtm.addRow(fila);
				}
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione paciente");
		}
	}
	
	
	/**
	 * Inserta el pedido. Comprueba que no haya campos vacíos. Si es así lo inserta.
	 */

	public void insertaPedido(JTextField txtNombre, JTextField txtUnidades, String fecha) {
		if (txtNombre.getText().length() != 0 && txtUnidades.getText().length() != 0) {
			Date fec = convierteFecha(fecha);

			PedidosAdministrativo p = new PedidosAdministrativo(txtNombre.getText(),
					Integer.parseInt(txtUnidades.getText()), fec);
			if (pedidosDAO.insertaPedido(p)) {
				JOptionPane.showMessageDialog(null, "Pedido insertado");
				txtNombre.setText("");
				txtUnidades.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "Ya existe un pedido en curso del material solicitado");
			}

		} else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}
	}
	
	/**
	 * Pinta el listado de pedidos en un JTable
	 * 
	 */
	
	public void listadoPedidos(DefaultTableModel dtm) {
		ArrayList lista = pedidosDAO.listadoPedidos();
		if(lista.size() > 0) {
			Iterator it = lista.iterator();
			while(it.hasNext()) {
				PedidosAdministrativo p = (PedidosAdministrativo) it.next();
				Object[] fila = { p.getNombre(),
						p.getUnidades(),
						p.getFecha_pedido()						
				};
				
				dtm.addRow(fila);
			}
		}
	}
	
	/**
	 * Pinta los datos de un pedido consultado en el comboBox
	 */
	
	public static void consultaPedido (DefaultTableModel dtm, JComboBox cmb) {
		
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			PedidosAdministrativo p = pedidosDAO.consultaPedido((String) cmb.getSelectedItem());
			Object[] fila = {
					p.getNombre(),
					p.getUnidades(),
					p.getFecha_pedido()
			};
			
			dtm.addRow(fila);
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione pedido");
		}
		
	}
	
	/**
	 * Pinta un listado de todo el inventario de materiales de oficina
	 */
	
	public static void listadoInventario(DefaultTableModel dtm) {
		ArrayList<InventarioAdministrativo> lista = invenDAO.listadoInventario();
		if(lista.size() > 0) {
			Iterator it = lista.iterator();
			while(it.hasNext()) {
				InventarioAdministrativo i = (InventarioAdministrativo) it.next();
				Object[] fila = {
						i.getNombre(),
						i.getUnidades(),
						i.getFecha_llegada()
				};
				
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}
	
	/**
	 * Método que borra el pedido que corresponda al String que se pasa al método.
	 * @param nombre
	 */
	public void borraPedido(String nombre) {
		if(pedidosDAO.borraPedido(nombre)) {
			JOptionPane.showMessageDialog(null, "Pedido borrado");
		}else {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	
	/**
	 * Devuelve datos sobre el material de oficina consultado
	 */
	
	public static void consultaMaterial(DefaultTableModel dtm, JComboBox cmb) {
		if(cmb.getSelectedIndex() != 0  && cmb.getSelectedIndex() != -1) {
			InventarioAdministrativo i = invenDAO.consultaMaterial((String) cmb.getSelectedItem());
			if(i != null) {
				Object[] fila = {
						i.getNombre(),
						i.getUnidades(),
						i.getFecha_llegada()
				};
				
				dtm.addRow(fila);
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione material a consultar");
		}
	}
	
	/**
	 * Recepciona el material de oficina insertado. Comprueba que ningún campo esté vacío.
	 * Si todo está ok, lo inserta/actualiza en el inventario. (Dependiendo de si el material se pide por primera vez o no)
	 * 
	 */

	public void recepcionaOficina(JComboBox nombre, JTextField unidades, String fecha) {
		if (nombre.getSelectedIndex() != 0 && nombre.getSelectedIndex() != -1) {
			if (unidades.getText().length() != 0) {
				Date fec = convierteFecha(fecha);

				RecepcionOficina r = new RecepcionOficina((String) nombre.getSelectedItem(),
						Integer.parseInt(unidades.getText()), fec);
				int resultado = invenDAO.recepcionaOficina(r);
				if (resultado == 0) {
					JOptionPane.showMessageDialog(null, "Inventario actualizado");
					nombre.setSelectedIndex(0);
					unidades.setText("");
				}
				if (resultado == 1) {
					JOptionPane.showMessageDialog(null, "El material a recepcionar no ha sido pedido");
				}

			} else {
				JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione material");

		}
	}
	
	/**
	 * Método para recepcionar la medicación que llega al centro. Los medicamentos llegan etiquetados por código y nombre.
	 * Para recepcionarlo se tienen que dar alguna de estas dos situaciones:
	 * ---> 1ª vez que se pide
	 * ---> Ya existe en el inventario
	 * 
	 * Si es la 1ª vez que se pide significa que en el inventario de la farmacia no figura ni el nombre ni el código que nos
	 * ha llegado, por lo tanto se inserta en la tabla de recepcion. Si la operación es correcta el método devuelve un 0.
	 * 
	 * Si ya se ha pedido anteriormente, se comprueba que los datos sean correctos. Es decir, si en el inventario tenemos registrado
	 * con cod 12 ibuprofeno e introducimos al programa cod 12 paracetamol el nombre estará mal, para ello el método devuelve 3.
	 * Si nos equivocamos en el código (introducimos cod 13 ibuprofeno en vez de cod 12) devuelve 4, para que rectifiquemos el cod.
	 * 
	 * Por otra parte si ya existe el medicamento en la tabla recepción devuelve 1, para evitar duplicados.
	 * Si el método nos devuelve un 2 significa que no figura en la tabla pedidos, por lo tanto no se puede recepcionar.
	 * (No tiene lógica registrar algo en el inventario que no hemos pedido)
	 * @param cod
	 * @param nombre
	 * @param unidades
	 * @param fecha
	 */
	public static void recepcionaFarmacia(JTextField cod, JTextField nombre, JTextField unidades, String fecha) {
		if(cod.getText().length() != 0 && nombre.getText().length() != 0 && unidades.getText().length() != 0 ) {
			Date fec = convierteFecha(fecha);
						
				int uniPedidas = unidadesPedidas(nombre.getText()); //Para guardar las unidades que se han pedido del medicamento
				RecepcionMedicacion r = new RecepcionMedicacion(Integer.parseInt(cod.getText()), nombre.getText(),fec,uniPedidas, Integer.parseInt(unidades.getText()));
				int resultado = invenDAO.recepcionaFarmacia(r);
			
				if(resultado == 0) {
					JOptionPane.showMessageDialog(null, "Recepcionado correctamente");
					cod.setText(""); nombre.setText(""); unidades.setText(""); 
				}
				
				if(resultado == 1) {
					JOptionPane.showMessageDialog(null, "El medicamento " + nombre.getText() + " ya está registrado para recepción");
				}
				
				if(resultado == 2) {
					JOptionPane.showMessageDialog(null,"El medicamento a recepcionar no existe en la tabla pedidos");
				}
				
				if(resultado == 3) {
					JOptionPane.showMessageDialog(null,"El nombre introducido no es correcto");
				}
				
				if(resultado == 4) {
					JOptionPane.showMessageDialog(null,"El código introducido no es correcto");
				}
		
		}else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}
	}
	
	
	/**
	 * Devuelve el nº de unidades pedidas para el medicamento que se pasa por parámetro.
	 * @param nombre
	 * @return
	 */
	public static int unidadesPedidas(String nombre) {
		int unidades = -1;
		
		try {
			Statement sentencia = bd.createStatement();
			ResultSet rs = sentencia.executeQuery("select unidades_pedidas from pedido_farmacia where nombre = '" + nombre + "'");
			while(rs.next()) {
				unidades = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return unidades;
	}
	
	
	/**
	 * Recibe un string y lo pasa a sql.Date para después devolverlo.
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
		 * Método que comprueba si una fecha es válida
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
	 * Recibe un objeto comboBox , el cual está relleno del paciente y devuelve un entero que corresponde
	 * al código del paciente seleccionado.
	 * @param cmb
	 * @return int codigo
	 */
	public int codigoPaciente(JComboBox cmb) {
		int codigo = 0;
		String resultado= cmb.getSelectedItem().toString();
		String [] arreglo = resultado.split("/");
		codigo = Integer.parseInt(arreglo[0]);		
		return codigo;
	}

	
	
	
	/**
	 * Devuelve el nombre del paciente que corresponde a ese código
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
	
}
