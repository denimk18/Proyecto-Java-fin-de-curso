package Operaciones;

import java.sql.Connection;
import java.sql.Date;
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
import dao.InventarioFarmacia;
import dao.InventarioFarmaciaDAO;
import dao.PedidoFarmacia;
import dao.PedidoFarmaciaDAO;
import dao.RecepcionMedicacion;
import factory.DAOFactory;
import implementacion.InventarioFarmaciaImpl;
import implementacion.PedidoFarmaciaImpl;

public class OperacionesFarmacia {

	private static Connection bd = VentanaInicio.bd.devuelveConexion();
	static DAOFactory bdFactory = VentanaInicio.getDAO();
	static PedidoFarmaciaDAO farmaciaDAO = new PedidoFarmaciaImpl();
	static InventarioFarmaciaDAO inventarioDAO = new InventarioFarmaciaImpl();
	
	public boolean borrarPedido(String nombre) {
		boolean ok = false;
		if(farmaciaDAO.borrarPedido(nombre)) {
			JOptionPane.showMessageDialog(null, "Pedido borrado");
			ok = true;
		}else {
			JOptionPane.showMessageDialog(null, "Error");
		}
		
		return true;
	}
	
	public void listadoSolicitudes(DefaultTableModel dtm) {
		ArrayList<PedidoFarmacia> lista = farmaciaDAO.solicitudPedidos();
		Iterator it = lista.iterator();
		
		if(lista.size() > 0) {
			while(it.hasNext()) {
				
				PedidoFarmacia pedido = (PedidoFarmacia) it.next();
				Object[] fila = { pedido.getNombre(),						
						pedido.getUnidades_pedidas(),
						pedido.getFecha_pedido(),
						};
				
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	
	}
	
	/**
	 * Inserta un pedido. Comprueba que la fecha sea correcta y que no haya ningún campo vacío. 
	 * @param nombre
	 * @param cantidad
	 * @param fecha
	 */
	public void insertaPedido(JTextField nombre, JTextField cantidad, String fecha) {
		if(nombre.getText().length() != 0 && cantidad.getText().length() != 0) {
			Date fec = convierteFecha(fecha);			
				PedidoFarmacia p = new PedidoFarmacia(nombre.getText(),Integer.parseInt(cantidad.getText()),fec);
				if(farmaciaDAO.insertarPedido(p)) {
					JOptionPane.showMessageDialog(null, "Pedido insertado");
					nombre.setText(""); cantidad.setText(""); 
				}else {
					JOptionPane.showMessageDialog(null, "Ya existe un pedido en curso de este medicamento.");
				}		
		}else {
			JOptionPane.showMessageDialog(null, "No puede haber campos vacíos");
		}
	}
	
	
	
	/**
	 * Inserta un pedido. Lo utilizo en la ventana ConsultaSolicitudes. En esta ventana hay un JTable con todas
	 * las solicitudes que han realizado los del personal de enfermería. A partir de los datos que se seleccionan creo un objeto
	 * de PedidoFarmacia y lo paso a este método para insertarlo.
	 * @param nombre
	 * @param cantidad
	 * @param fecha
	 */
	public boolean insertaPedido(PedidoFarmacia pedido) {
		boolean insertado = false;
				if(farmaciaDAO.insertarPedido(pedido)) {
					JOptionPane.showMessageDialog(null, "Pedido insertado");
					insertado = true;
				}else {
					JOptionPane.showMessageDialog(null, "Ya existe un pedido en curso de este medicamento.");
				}
		return insertado;
	}
		
	
	/**
	 * Para recepcionar ( es decir, registrar en el inventario ) , los medicamentos que han llegado a la residencia.
	 * @param recepcion
	 * @return
	 */
	public boolean recepcionPedidos(RecepcionMedicacion recepcion) {
		boolean recep = false;
		if(inventarioDAO.recepcionMedicamento(recepcion)) {
			JOptionPane.showMessageDialog(null, "Medicamento registrado en el inventario");
			recep= true;
		}else {
			JOptionPane.showMessageDialog(null, "Error");
		}
		
		return recep;
	}
	
	
	
	/**
	 * Recibe un string y lo pasa a sql.Date para después devolverlo.	 * 
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
	 * Pinta un listado de pedidos en un JTable
	 * @param dtm
	 */
	public void listadoPedidos(DefaultTableModel dtm) {
		ArrayList<PedidoFarmacia> listado = farmaciaDAO.todosPedidos();
		Iterator it = listado.iterator();
		
		if(listado.size() > 0) {
			while(it.hasNext()) {
				PedidoFarmacia p = (PedidoFarmacia) it.next();
				
				Object[] fila = { p.getNombre(),
						p.getUnidades_pedidas(),
						p.getFecha_pedido()
						};
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}
	
	/**
	 * Pinta los datos del pedido del medicamento seleccionado en el comboBox
	 * @param dtm
	 * @param cmb
	 */
	public void pedidosMedicamento(DefaultTableModel dtm, JComboBox cmb) {
		
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			String s = (String) cmb.getSelectedItem();
			ArrayList<PedidoFarmacia> listado = farmaciaDAO.consultaPedido(s);
			Iterator it = listado.iterator();
			
			if(listado.size() > 0) {
				while(it.hasNext()) {
					PedidoFarmacia p = (PedidoFarmacia) it.next();
					
					Object[] fila = { p.getNombre(),
							p.getUnidades_pedidas(),
							p.getFecha_pedido()
							};
					dtm.addRow(fila);
				}
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione medicamento a consultar");
		}
		
	}
	
	/**
	 * Pinta un listado con todo el inventario en un jtable
	 * @param dtm
	 */
	public void listadoInventario(DefaultTableModel dtm) {
		ArrayList<InventarioFarmacia> listado = inventarioDAO.inventario();
		Iterator it = listado.iterator();
		
		if(listado.size() > 0) {
			while(it.hasNext()) {
				InventarioFarmacia inven =  (InventarioFarmacia) it.next();
				
				Object[] fila = { inven.getCod_medicamento(),
						inven.getNombre(),
						inven.getUnidades(),
						inven.getFecha_llegada()
						};
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}

	/**
	 * Pinta en un JTable todos los datos del medicamento seleccionado en el comboBox
	 * @param dtm
	 * @param cmb
	 */
	public void consultaMedicamento(DefaultTableModel dtm , JComboBox cmb) {
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {

			InventarioFarmacia inven = inventarioDAO.consultaMedicamento(codigoMedicamento(cmb));
			
			if(inven != null) {
					
				Object[] fila = { inven.getCod_medicamento(),
						inven.getNombre(),
						inven.getUnidades(),
						inven.getFecha_llegada()
						};
					dtm.addRow(fila);
				
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione medicamento a consultar");
		}
	}
	
	
	/**
	 * Pinta un listado de medicación que ha llegado en un JTable
	 * @param dtm
	 */
	public void recepcionMedicamento (DefaultTableModel dtm) {
		ArrayList<PedidoFarmacia> listado = inventarioDAO.listadoRecepcion();
		Iterator it = listado.iterator();
		
		if(listado.size() > 0) {
			while(it.hasNext()) {
				RecepcionMedicacion r = (RecepcionMedicacion) it.next();
				
				Object[] fila = { r.getCod_medicamento(),
						r.getNombre(),
						r.getUnidades_pedidas(),
						r.getUnidades_recibidas(),
						r.getFecha_llegada()
						};
				dtm.addRow(fila);
			}
		}else {
			JOptionPane.showMessageDialog(null, "No hay datos");
		}
	}	
	
	
	
	/**
	 * Pinta los datos de la recepción del medicamento seleccionado en el comboBox
	 * @param dtm
	 * @param cmb
	 */
	public void recepcionMedicamento(DefaultTableModel dtm, JComboBox cmb) {
		
		if(cmb.getSelectedIndex() != 0 && cmb.getSelectedIndex() != -1) {
			String s = (String) cmb.getSelectedItem();
			RecepcionMedicacion r = inventarioDAO.consultaMedicamentoRecepcion(codigoMedicamento(cmb));
			
			if(r != null) {
				
				Object[] fila = { r.getCod_medicamento(),
						r.getNombre(),
						r.getUnidades_pedidas(),
						r.getUnidades_recibidas(),
						r.getFecha_llegada()
						};
				dtm.addRow(fila);
				
			}else {
				JOptionPane.showMessageDialog(null, "No hay datos");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Seleccione medicamento a consultar");
		}
		
	}
	
	/**
	 * Método que devuelve el codigo del fármaco seleccionado en el comboBox
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

}
