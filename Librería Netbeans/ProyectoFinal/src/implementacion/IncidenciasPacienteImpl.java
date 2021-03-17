/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.IncidenciasPaciente;
import dao.IncidenciasPacienteDAO;
import factory.SqlDbDAOFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * En esta clase se harán a cabo todas las operaciones que tienen que ver con las incidencias de los pacientes
 * Por ejemplo: caidas, lesiones étc...
 * @author Denitsa
 */
public class IncidenciasPacienteImpl implements IncidenciasPacienteDAO {

     Connection conexion;

   
    public IncidenciasPacienteImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    
    /**
     * Inserta la incidencia. Primero comprueba que no existe ninguna con el código que se crea.
     * Si no existe la inserta
     * @param incidencia
     * @return boolean insertado
     */
    @Override
    public boolean insertaIncidencia(IncidenciasPaciente incidencia) {
        boolean insertado = false;
        if(!existeIncidencia(incidencia.getCod_incidencia())){
            String sql = String.format("INSERT INTO INCIDENCIAS_PACIENTE VALUES(%d, %d, %d, %d, '%s', '%s',"
                    + " '%s', '%s' )", incidencia.getCod_incidencia(),incidencia.getCod_paciente(),
                    incidencia.getCod_medicamento(),incidencia.getCantidad(),incidencia.getFecha(),
                    incidencia.getCausa(), incidencia.getCura(),
                    incidencia.getComunicado_familiares());
            
            try {
                Statement sentencia = conexion.createStatement();
                int filas = sentencia.executeUpdate(sql);
                
                if(filas >= 1){ insertado = true; }
            } catch (SQLException ex) {
                Logger.getLogger(IncidenciasPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return insertado;
    }

    /**
     * Devuelve un arrayList con todas las incidencias que se han creado
     * @return ArrayList
     */
    @Override
    public ArrayList listadoIncidencias() {
        ArrayList<IncidenciasPaciente> incidencias = new ArrayList();
        String sql = "Select * from incidencias_paciente";
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 IncidenciasPaciente incidencia = new IncidenciasPaciente(rs.getInt(1), rs.getInt(2),
                 rs.getInt(3),rs.getInt(4), rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8));
                 
                 incidencias.add(incidencia);
             }
         } catch (SQLException ex) {
             Logger.getLogger(IncidenciasPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        return incidencias;
    }

    /**
     * Devuelve un listado con todas las incidencias de un paciente
     * @param codPaciente
     * @return ArrayList
     */
    @Override
    public ArrayList incidenciasPaciente(int codPaciente) {
        ArrayList<IncidenciasPaciente> incidencias = new ArrayList();
        String sql = "Select * from incidencias_paciente where cod_paciente = " + codPaciente;
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                  IncidenciasPaciente incidencia = new IncidenciasPaciente(rs.getInt(1), rs.getInt(2),
                 rs.getInt(3),rs.getInt(4), rs.getDate(5), rs.getString(6), rs.getString(7), rs.getString(8));
                 
                 incidencias.add(incidencia);
             }
         } catch (SQLException ex) {
             Logger.getLogger(IncidenciasPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        return incidencias;
    }
    
    public boolean existeIncidencia(int cod){
        boolean existe = false;
        String sql = "select * from incidencias_paciente where cod_incidencia = " + cod;
        
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery(sql);
             while(rs.next()){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(IncidenciasPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
}
