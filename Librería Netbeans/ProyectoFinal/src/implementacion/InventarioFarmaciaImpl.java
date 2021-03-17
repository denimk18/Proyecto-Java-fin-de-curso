/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.InventarioFarmacia;
import dao.InventarioFarmaciaDAO;
import dao.PedidoFarmacia;
import dao.RecepcionMedicacion;
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
public class InventarioFarmaciaImpl implements InventarioFarmaciaDAO {

     Connection conexion;

   
    public InventarioFarmaciaImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    /**
     * Devuelve un arrayList con todo el inventario
     * @return arrayList listado
     */
    @Override
    public ArrayList inventario() {
        ArrayList<InventarioFarmacia> listado = new ArrayList();
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from inventario_farmacia");
             while(rs.next()){
                 InventarioFarmacia inven = new InventarioFarmacia(rs.getInt(1),rs.getString(2), rs.getInt(3),
                 rs.getDate(4));
                 listado.add(inven);
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return listado;
    }

    /**
     * Devuelve un objeto InventarioFarmacia cuando se consulta cierto medicamento
     * @param codMedicamento
     * @return inven InventarioFarmacia
     */
    @Override
    public InventarioFarmacia consultaMedicamento(int codMedicamento) {
         InventarioFarmacia inven = null;
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from inventario_farmacia where cod_medicamento = " + codMedicamento);
             while(rs.next()){
                 inven = new InventarioFarmacia(rs.getInt(1),rs.getString(2), rs.getInt(3),
                 rs.getDate(4));
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return inven;   
    }

  
    /**
     * Este método sirve para registrar los medicamentos que se han solicitado y llegado a la residencia desde la farmacia
     * que los suministra.
     * Primero se comprueba si el medicamento ya existe en el inventario. Si existe, se hace un update.
     * Para ello primero se saca el stock actual del medicamento del inventario y se suma a la cantidad
     * que se recibe. Esto se hace sobretodo porque se suelen hacer pedidos cuando aún quedan algunas unidades
     * del medicamento.
     * 
     * Una vez hecho esto hacemos el update actualizando la fecha de llegada y las unidades. Se elimina el pedido
     * de la tabla pedidos que se corresponde con el nombre del medicamento recepcionado
     * 
     * Por otra parte si no existe en el inventario de la farmacia (es decir, es la primera vez que se pide), se
     * inserta y se borra el registro de la tabla pedidos.
     * @param medicamento
     * @return boolean
     */
    
    
    @Override
    public boolean recepcionMedicamento(RecepcionMedicacion medicamento) {
        boolean recepcionado = false;
        
        if(existeMedicamento(medicamento.getCod_medicamento())){
            try {
                Statement sentencia = conexion.createStatement();
                    
                String sql = String.format("Select unidades from inventario_farmacia where cod_medicamento = %d ",
                        medicamento.getCod_medicamento());
                
                ResultSet rs = sentencia.executeQuery(sql);
                int unidades = 0;
                while(rs.next()){
                    unidades = rs.getInt(1);
                }
                
                unidades = unidades + medicamento.getUnidades_recibidas();                
                String update = String.format("UPDATE INVENTARIO_FARMACIA SET UNIDADES = %d, fecha_llegada = '%s'"
                        + " where cod_medicamento = %d ",unidades, medicamento.getFecha_llegada(),medicamento.getCod_medicamento());
           
                int filas = sentencia.executeUpdate(update);
                if(filas >= 1){recepcionado = true;}
                if(recepcionado){
                    sentencia.executeUpdate("delete from pedido_farmacia where nombre = '" + medicamento.getNombre() + "'");
                    sentencia.executeUpdate("delete from recepcion_medicacion where nombre = '" + medicamento.getNombre() + "'");

                }
            } catch (SQLException ex) {
                Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            
            String sql = String.format("INSERT INTO INVENTARIO_FARMACIA VALUES(%d,'%s',%d,'%s');", medicamento.getCod_medicamento(),
                    medicamento.getNombre(), medicamento.getUnidades_recibidas(),medicamento.getFecha_llegada());
            
            try {
                Statement sentencia = conexion.createStatement();
                int filas = sentencia.executeUpdate(sql);
                if(filas >= 1){ recepcionado = true; }
                if(recepcionado){
                    sentencia.executeUpdate("delete from pedido_farmacia where nombre = '" + medicamento.getNombre() + "'");
                    sentencia.executeUpdate("delete from recepcion_medicacion where nombre = '" + medicamento.getNombre() + "'");

                }
            } catch (SQLException ex) {
                Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return recepcionado;
    }
    
    /**
     * Comprueba si existe un medicamento
     * @param cod
     * @return existe booleano
     */
    public boolean existeMedicamento(int cod){
        boolean existe = false;
        String sql = "select * from inventario_farmacia where cod_medicamento = " + cod + "";
        
        try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 existe = true;
             }
            
         } catch (SQLException ex) {
             Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }

    /**
     * Devuelve un listado con todos los medicamento a recepcionar.
     * @return  listado Recepcionmedicacion
     */
    @Override
    public ArrayList listadoRecepcion() {
        ArrayList<RecepcionMedicacion> listado = new ArrayList();
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from recepcion_medicacion");
             while(rs.next()){
                 
                 RecepcionMedicacion r = new RecepcionMedicacion(rs.getInt(1),rs.getString(2),rs.getDate(3),
                         rs.getInt(4),rs.getInt(5));
                 listado.add(r);
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return listado;
    }

    /**
     * Devielve un objeto RecepcionMedicacion que son los datos de un medicamento que está para recepcionar
     * @param codMedicamento
     * @return Recepcionmedicacion medicamento;
     */
    @Override
    public RecepcionMedicacion consultaMedicamentoRecepcion(int codMedicamento) {
        RecepcionMedicacion medicamento = null;
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from recepcion_medicacion where cod_medicamento = " + codMedicamento);
             while(rs.next()){
                 medicamento = new RecepcionMedicacion(rs.getInt(1),rs.getString(2),rs.getDate(3),
                         rs.getInt(4),rs.getInt(5));
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return medicamento;
    }
    
    
   
  
}
