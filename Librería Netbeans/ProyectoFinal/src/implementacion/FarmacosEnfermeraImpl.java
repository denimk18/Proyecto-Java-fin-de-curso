/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.DiagnosticoPaciente;
import dao.FarmacosEnfermera;
import dao.FarmacosEnfermeraDAO;
import dao.Paciente;
import dao.PedidoFarmacia;
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
 * En esta clase se realizarán operaciones para el personal de enfermería que tienen que ver con la
 * administración de fármacos. Los métodos hechos aquí únicamente se utilizaran para esto.
 * Las lesiones de los pacientes (incidencias) se llevaran en la clase IncidenciasPacienteImpl
 */
public class FarmacosEnfermeraImpl implements FarmacosEnfermeraDAO {

     Connection conexion;

   
    public FarmacosEnfermeraImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    /**
     *Comprueba si antes de insertar la administración si hay stock. Si no lo hay se queda en false.
     * Si hay stock inserta la administración en la bd y resta la cantidad administrada de las unidades
     * de la base de datos con el método restaFarmacos.
     * 
     * **/
    @Override
    public boolean insertaFarmacos(FarmacosEnfermera f) {
        boolean inserta = false;

        if (hayStock(f.getCantidad(),f.getCod_farmaco())) {
            String sql = String.format("INSERT INTO FARMACOS_ENFERMERA(cod_paciente, cod_farmaco,cantidad,fecha_administracion) VALUES"
                    + " (%d, %d,%d, '%s')", f.getCod_paciente(), f.getCod_farmaco(), f.getCantidad(), f.getFecha_administracion());

            try {
                Statement sentencia = conexion.createStatement();
                int filas = sentencia.executeUpdate(sql);
                if (filas >= 1) {
                    inserta = true;
                    restaFarmacos(f.getCantidad(),f.getCod_farmaco());
                }
            } catch (SQLException ex) {
                Logger.getLogger(FarmacosEnfermeraImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            inserta = false;
        }
        return inserta;
    }

    /**
     * Muestra el diagnóstico del paciente seleccionado. (en este caso del cod paciente que se pasa).
     * **/
    @Override
    public ArrayList muestraDiagnostico(int codPaciente) {
        ArrayList<DiagnosticoPaciente> diagnosticos = new ArrayList();
        String sql = "select * from diagnostico_paciente where cod_paciente = " + codPaciente;
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 DiagnosticoPaciente d = new DiagnosticoPaciente(rs.getInt(1), rs.getInt(2), rs.getString(3), 
                  rs.getInt(4), rs.getString(5), rs.getDate(6));
                 
                 diagnosticos.add(d);
             }
         } catch (SQLException ex) {
             Logger.getLogger(FarmacosEnfermeraImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return diagnosticos;
    }
    
    /**
     * Muestra un listado de todos los fármacos administrados a todos los pacieentes
     * @return  ArrayList FarmacosEnfermera
     */

    @Override
    public ArrayList listadoFarmacosAdministrados() {
        ArrayList<FarmacosEnfermera> listado = new ArrayList();
        String sql = "select * from farmacos_enfermera";
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
              ResultSet rs = sentencia.executeQuery(sql);
              while(rs.next()){
                 FarmacosEnfermera f = new FarmacosEnfermera(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                 listado.add(f);
              }
         } catch (SQLException ex) {
             Logger.getLogger(FarmacosEnfermeraImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
       
        return listado;
    }

    
    /**
     * Muestra un listado de los fármacos que se le han administrado al paciente seleccionado
     * @param codPaciente
     * @return ArrayList<FarmacosEnfermera>
     */
    @Override
    public ArrayList pacienteFarmacoAdministrado(int codPaciente) {
        ArrayList<FarmacosEnfermera> listado = new ArrayList();
        String sql = "select * from farmacos_enfermera where cod_paciente = " + codPaciente;
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 FarmacosEnfermera f = new FarmacosEnfermera(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getDate(5));
                 listado.add(f);
             }
         } catch (SQLException ex) {
             Logger.getLogger(FarmacosEnfermeraImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return listado;
    }
    
     /**
      * Comprueba si hay suficiente stock del medicamento que se pasa por parámetro
      * Si el stock que hay en la base de datos - la cantidad es menor o igual que 0 devuelve false.
      * (No suficiente stock)
      * @param cantidad
      * @param farmaco
      * @return boolean stock
      */
    public boolean hayStock(int cantidad ,int farmaco){
        boolean stock = true;
        int stockFarmaco = 0;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select unidades from inventario_farmacia where "
                    + " cod_medicamento = " + farmaco);
            while(rs.next()){
                stockFarmaco = rs.getInt(1);
            }
            
            if(stockFarmaco - cantidad <=0){
                stock = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }

    /**
     * Resta la cantidad (el nº de pastillas administradas) - stock de pastillas de la base de datos
     * @param cantidad
     * @param farmaco 
     */
    public void restaFarmacos(int cantidad, int farmaco){
        int stockFarmaco = 0;
        int nuevoStock = 0;
         try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select unidades from inventario_farmacia where "
                    + " cod_medicamento = " + farmaco);
            while(rs.next()){
                stockFarmaco = rs.getInt(1);
            }
            
            nuevoStock = stockFarmaco - cantidad;
            sentencia.executeUpdate("update inventario_farmacia set unidades = " + nuevoStock + " where cod_medicamento = "
            + farmaco);

                    
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean solicitaFarmacos(PedidoFarmacia pedido) {
        boolean insertado = false;
         try {
             Statement sentencia = conexion.createStatement();
             String sql = String.format("INSERT INTO solicitud_medicamento (nombre, unidades_pedidas, fecha_pedido) values("
                     + " '%s', %d, '%s')", pedido.getNombre(),pedido.getUnidades_pedidas(),pedido.getFecha_pedido());
         
             int filas = sentencia.executeUpdate(sql);
             if(filas >= 1){ insertado = true; }
         
         } catch (SQLException ex) {
             Logger.getLogger(FarmacosEnfermeraImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return insertado;
    }
}
