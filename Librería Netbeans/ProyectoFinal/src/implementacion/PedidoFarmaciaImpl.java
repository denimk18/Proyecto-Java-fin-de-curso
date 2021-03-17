/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.PedidoFarmacia;
import dao.PedidoFarmaciaDAO;
import factory.SqlDbDAOFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Denitsa
 */
public class PedidoFarmaciaImpl implements PedidoFarmaciaDAO{

    
     Connection conexion;

    public PedidoFarmaciaImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
        //FALTA HACER QUE CUANDO SE HACE EL PEDIDO ELIMINAR DE LA TABLA SOLICITUDES!!!

    /**
     * Inserta pedido. Comprueba antes que no exista. Si existe devuelve false
     * @param p
     * @return boolean insertado.
     */
    @Override
    public boolean insertarPedido(PedidoFarmacia p) {
        boolean insertado = false;
        
        if (!existePedido(p.getNombre())) {
            try {
                Statement sentencia = conexion.createStatement();
                String sql = String.format("INSERT INTO PEDIDO_FARMACIA(NOMBRE,UNIDADES_PEDIDAS,FECHA_PEDIDO)"
                        + " VALUES('%s', %d , '%s')", p.getNombre(), p.getUnidades_pedidas(), p.getFecha_pedido());

                int filas = sentencia.executeUpdate(sql);
                if (filas >= 1) {
                    insertado = true;
                    sentencia.executeUpdate("delete from solicitud_medicamento where nombre = '" + p.getNombre() + "';");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            insertado = false;
        }
        return insertado;
    }

    /**
     * Devuelve arrayList con todos los pedidos 
     * @return arrayList lista
     */
    @Override
    public ArrayList todosPedidos() {
        ArrayList<PedidoFarmacia> lista = new ArrayList();
        String sql = "select * from pedido_farmacia";
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 PedidoFarmacia pedido = new PedidoFarmacia(rs.getString(1), rs.getInt(2), rs.getDate(3));
                 lista.add(pedido);
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }

   /**
    * Devuelve los datos sobre un pedido consultado .
    * @param nombre
    * @return 
    */
     @Override
    public ArrayList consultaPedido(String nombre) {

        ArrayList<PedidoFarmacia> lista = new ArrayList();
        String sql = "select distinct nombre, unidades_pedidas, fecha_pedido from pedido_farmacia where nombre = '" + nombre + "'";
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 PedidoFarmacia pedido = new PedidoFarmacia(rs.getString(1), rs.getInt(2), rs.getDate(3));
                 lista.add(pedido);
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }

    /**
     * Devuelve arrayList con todas las solicitudes de pedidos hechas por personal de enfermería
     * @return arrayList listado
     */
    @Override
    public ArrayList solicitudPedidos() {
        ArrayList<PedidoFarmacia> listado = new ArrayList();
        String sql = "Select * from solicitud_medicamento";
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 PedidoFarmacia pedido = new PedidoFarmacia(rs.getString(2), rs.getInt(3), rs.getDate(4));
                 listado.add(pedido);
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return listado;
    }

 
    /**
     * Comprueba si ya existe un pedido de ese medicamento. Busca en la tabla un pedido con ese nombre y lo comapra
     * al que le pasamos. Los pasa a mayúsculas para evitar que por ejemplo, un pedido ibuprofeno y otro llamado
     * Ibuprofeno los dé por distintos.
     * @param nombre
     * @return 
     */
    public boolean existePedido(String nombre){
        boolean existe = false;
        String nombreBuscado = "";
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from pedido_farmacia where nombre = '" + nombre + "'");
             while(rs.next()){
                 nombreBuscado = rs.getString(1);
             }
             
             if(nombreBuscado.toLowerCase().equals(nombre.toLowerCase())){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }

    /**
     * Borra el pedido de la tabla pedido_farmacia que corresponda a ese nombre
     * @param nombre
     * @return boolean existe
     */
    @Override
    public boolean borrarPedido(String nombre) {
        boolean existe = false;
         try {
             Statement sentencia = conexion.createStatement();
             int filas = sentencia.executeUpdate("delete from pedido_farmacia where nombre = '" + nombre + "'");
             if(filas >= 1){ existe = true; }
         } catch (SQLException ex) {
             Logger.getLogger(PedidoFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
    
   
}
