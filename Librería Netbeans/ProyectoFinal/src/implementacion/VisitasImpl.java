/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.Visitas;
import dao.VisitasDAO;
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
public class VisitasImpl implements VisitasDAO {

     Connection conexion;

   
    public VisitasImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    
    /**
     * Inserta un registro en la tabla visitas
     * @param v
     * @return boolean insertado
     */
    @Override
    public boolean insertaVisitas(Visitas v) {
        boolean insertado = false;
        
         try {
             Statement sentencia = conexion.createStatement();
             String sql = String.format("INSERT INTO VISITAS (nombre_familiar,dni,fecha,cod_paciente) VALUES ('%s', '%s', '%s', %d)",
                     v.getNombre_familiar(),v.getDni(), v.getFecha(),v.getCod_paciente());
             
             int filas = sentencia.executeUpdate(sql);
             if(filas >=1){ insertado = true;}
         } catch (SQLException ex) {
             Logger.getLogger(VisitasImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return insertado;
    }

    
    /**
     * Devuelve un arrayList de visitas
     * @return arraylist lista
     */
    @Override
    public ArrayList listadoVisitas() {
        ArrayList<Visitas> lista = new ArrayList();
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from visitas");
             while(rs.next()){
                 Visitas v = new Visitas(rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5));
                 lista.add(v);
             }
         } catch (SQLException ex) {
             Logger.getLogger(VisitasImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }

    /**
     * Devuelve un arrayList con todas las visitas de un paciente
     * Se buscan todos los registros de la tabla visitas que correspondan al codigo paciente pasado
     * @param codPaciente
     * @return arrayList lista
     */
    @Override
    public ArrayList consultaVisita(int codPaciente) {
       ArrayList<Visitas> lista = new ArrayList();
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from visitas where cod_paciente = " + codPaciente);
             while(rs.next()){
                 Visitas v = new Visitas(rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5));
                 lista.add(v);
             }
         } catch (SQLException ex) {
             Logger.getLogger(VisitasImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }
    
}
