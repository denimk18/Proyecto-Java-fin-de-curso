/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.PedidoFarmacia;
import dao.PedidosAdministrativo;
import dao.PedidosAdministrativoDAO;
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
public class PedidosAdministrativoImpl implements PedidosAdministrativoDAO {

     Connection conexion;

    public PedidosAdministrativoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    
    /**
     * Inserta un pedido. Comprueba que no exista ningún pedido con el mismo nombre.
     * Si no existe procede a insertarlo en la tabla. Si ya existe devuelve false.
     * @param p
     * @return 
     */
    @Override
    public boolean insertaPedido(PedidosAdministrativo p) {
        boolean insertado = false;
        if(!existePedido(p.getNombre())){
            try {
                Statement sentencia = conexion.createStatement();
                  String sql = String.format("INSERT INTO PEDIDOS_OFICINA VALUES('%s', %d, '%s')",p.getNombre(),
                    p.getUnidades(),p.getFecha_pedido());
                  
                  int filas = sentencia.executeUpdate(sql);
                  if(filas >= 1){ insertado = true; }
            } catch (SQLException ex) {
                Logger.getLogger(PedidosAdministrativoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        }
        return insertado;
    }

    /**
     * Devuelve un arrayList de todos los pedidos
     * @return 
     */
    @Override
    public ArrayList listadoPedidos() {

        ArrayList<PedidosAdministrativo> lista = new ArrayList();
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from pedidos_oficina");
             while(rs.next()){
                 PedidosAdministrativo p = new PedidosAdministrativo(rs.getString(1), rs.getInt(2), rs.getDate(3));
                 lista.add(p);
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidosAdministrativoImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }

    /**
     * Devuelve datos de un pedido
     * @param nombre
     * @return PedidosAdministrativo p
     */
    @Override
    public PedidosAdministrativo consultaPedido(String nombre) {
        PedidosAdministrativo p = null;
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from pedidos_oficina where nombre = '" + nombre + "'");
             while(rs.next()){
                  p = new PedidosAdministrativo(rs.getString(1), rs.getInt(2), rs.getDate(3));                 
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidosAdministrativoImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        
        return p;
    }
    
    /**
     * Comprueba que no existe un pedido . Para eso busca en la tabla un pedido con ese nombre. Si lo hay
     * lo guardamos en un String. Este string lo comparamos con el strin que se pasa al método pasándolo
     * a mayúsculas. Así evitamos que se dupliquen pedido como por ejemplo, folios y Folios.
     * @param nombre
     * @return 
     */
    public boolean existePedido(String nombre){
        boolean existe = false;
        String buscado = "";
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select *  from pedidos_oficina where nombre = '" + nombre + "'");
             while(rs.next()){
                 buscado = rs.getString(1);
             }
         
             if(buscado.toLowerCase().equals(nombre.toLowerCase())){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(PedidosAdministrativoImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }

    @Override
    public boolean borraPedido(String nombre) {
        boolean borrado = false;
        
         try {
             Statement sentencia = conexion.createStatement();
             int filas = sentencia.executeUpdate("delete from pedidos_oficina where nombre = '" + nombre + "'");
             if(filas >= 1){ borrado = true; }
         } catch (SQLException ex) {
             Logger.getLogger(PedidosAdministrativoImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return borrado;
        
    }
}
