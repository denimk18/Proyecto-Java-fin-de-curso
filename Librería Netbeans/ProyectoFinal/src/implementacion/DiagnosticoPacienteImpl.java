/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.DiagnosticoPaciente;
import dao.DiagnosticoPacienteDAO;
import dao.Paciente;
import dao.Receta;
import factory.SqlDbDAOFactory;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Denitsa
 * En esta clase se van a desarrollar todas las operaciones relacionadas con el sector médico
 * (alta, baja de pacientes, consulta, modificación, tratamientos y recetas)
 */
public class DiagnosticoPacienteImpl implements DiagnosticoPacienteDAO {

    Connection conexion;

   
    public DiagnosticoPacienteImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    /**
     * Método para dar de alta pacientes. Si el paciente no existe lo inserta. En filas
     * guarda el nº de filas afectadas y si es mayor que 0 es que se ha ejecutado bien. (alta = true)
     * De lo contrario, alta = false. También estará a false si el paciente ya existe.
     * @param p
     * @return int alta.
     *          -1 si ya existe el codigo
     *          0 si se inserta bien
     */
    
    @Override
    public int altaPacientes(Paciente p) {
        int alta = -2;
        if (!existeCodigo(p.getCod_paciente())) {
                String sql = String.format("INSERT INTO PACIENTES VALUES (%d , '%s' , '%s', '%s', '%s', %d)",
                        p.getCod_paciente(), p.getNombre(), p.getDireccion(), p.getTelefono_urgencia(),
                        p.getFecha_alta(), p.getCod_medico());
                Statement sentencia;

                try {
                    sentencia = conexion.createStatement();
                    sentencia.executeUpdate(sql);
                    alta = 0;
                } catch (SQLException ex) {
                    //Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
          
        } else {
            alta = -1;
        }

        return alta;
    }
    
    /**
     * Método para dar de baja pacientes. Se pasa el codigo por parámetro.
     * Se comprueba si existe el paciente. De lo contrario devuelve error.
     * @param codPaciente
     * @return baja , indica si la baja se ha hecho correctamente
     */

    @Override
    public boolean bajaPacientes(int codPaciente) {
        boolean baja = false;
        if(existeCodigo(codPaciente)){
            String sql = "DELETE FROM PACIENTES WHERE COD_PACIENTE = " + codPaciente;
            
            try {
                Statement sentencia = conexion.createStatement();
                int filas = sentencia.executeUpdate(sql.toString());
                baja = true;
            } catch (SQLException ex) {
                Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return baja;
    }

    /**
     * Devuelve listado de todos los pacientes
     * @return ArrayList pacientes
     */
    @Override
    public ArrayList listadoPacientes() {
        ArrayList<Paciente> pacientes = new ArrayList();
        String sql = "select * from pacientes";
        
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                Paciente p = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getDate(5),rs.getInt(6));
                pacientes.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pacientes;
    }

    /**
     * Devuelve un objeto paciente que corresponde al que tiene el código pasado por parámetro
     * @param cod
     * @return  Paciente
     */
    @Override
    public Paciente consultaPaciente(int cod) {
        Paciente p = null;
        
        if(existeCodigo(cod)){
        String sql = "SELECT * FROM PACIENTES WHERE COD_PACIENTE = " + cod;
          try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                p = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getDate(5),rs.getInt(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }}
        return p;
    }

    /**
     * Modifica los datos del paciente pasados 
     * @param cod
     * @param p
     * @return modificado, indica si se ha modificado correctamente
     */
    @Override
    public boolean modificaPacientes(int cod, Paciente p) {
        boolean modificado = false;
        if(existePaciente(p.getNombre())){
            String sql = String.format("UPDATE PACIENTES SET NOMBRE = '%s' , DIRECCION = '%s' ,"
                    + " TELEFONO_URGENCIA = '%s', FECHA_ALTA = '%s', COD_MEDICO = %d WHERE COD_PACIENTE = %d", 
                    p.getNombre(), p.getDireccion(),p.getTelefono_urgencia(), p.getFecha_alta(),
                    p.getCod_medico(), cod);
            
            try {
                Statement sentencia = conexion.createStatement();
                sentencia.executeUpdate(sql);
                modificado = true;
            } catch (SQLException ex) {
                //Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return modificado;
    }

    /**
     * Inserta el tratamiento. (Tratamiento = diagnostico_paciente.) 
     * @param codPaciente
     * @param diag
     * @return insertado int. Se inicia a -2. Si devuelve -1 es que el diagnóstico ya existe.
     * Si devuelve 1 es que el paciente no existe. 
     * Si devuelve 0 es que la operación se ha reallizado bien.
     */
    @Override
    public int insertaTratamientos(int codPaciente, DiagnosticoPaciente diag) {
      int insertado = -2;
      
      if(!existeDiagnostico(diag.getCod_diagnostico())){
          if(existeCodigo(codPaciente)){
              try {
                  Statement sentencia = conexion.createStatement();
                  String sql = String.format("INSERT INTO DIAGNOSTICO_PACIENTE VALUES(%d, %d, '%s', %d, '%s' , '%s')",
                          diag.getCod_diagnostico(), diag.getCod_paciente(), diag.getNombre_farmaco(),
                          diag.getCantidad(), diag.getDiagnostico(), diag.getFecha_diagnostico());
                  int filas = sentencia.executeUpdate(sql);
                  if(filas >= 1){ insertado = 0;}
              } catch (SQLException ex) {
                  Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
              }
              
            
          }else{
              insertado = 1;
          }
      }else{
          insertado = -1;
      }
      
      return insertado;
    }
    
    
    
    /**
     * Comprueba si existe el paciente con el nombre que se le pasa
     * @param nombre
     * @return existe boolean
     */
    
    public boolean existePaciente(String nombre){
        boolean existe = false;
        try {
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM PACIENTES WHERE NOMBRE = '" + nombre + "'";
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                existe = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
      /**
     * Comprueba si existe el codigo pasado por parámetro
     * @param cod
     * @return existe boolean
     */
    
     public boolean existeCodigo(int cod){
        boolean existe = false;
        try {
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM PACIENTES WHERE COD_PACIENTE = " + cod;
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                existe = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    /**
     * Comprueba si existe el diagnostico con el cod que se pasa
     * @param cod
     * @return existe booleano
     */
     public boolean existeDiagnostico(int cod){
        boolean existe = false;
        try {
            Statement sentencia = conexion.createStatement();
            String sql = "SELECT * FROM DIAGNOSTICO_PACIENTE  where cod_diagnostico = " + cod;
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                existe = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    
    /**
     * Método para insertar recetas. Si no existe el medicamento seleccionado devuelve -1
     * Si no existe el paciente devuelve 1
     * Si se inserta bien devuelve 0
     * @param r
     * @return 
     */
    @Override
    public int insertaRecetas(Receta r) {
        int resultado = -2;
        if(existeMedicacion(r.getCod_medicamento())){
            if(existeCodigo(r.getCod_paciente())){
                try {
                    Statement sentencia = conexion.createStatement();
                    String sql = String.format("INSERT INTO RECETAS (cod_paciente, cod_medicamento, fecha_receta, administracion) VALUES"
                            + " (%d, %d, '%s', '%s') ", r.getCod_paciente(), r.getCod_medicamento(),r.getFecha_administracion(), r.getAdministracion());
                    int filas = sentencia.executeUpdate(sql);
                    if(filas > 0){ resultado = 0;}
                } catch (SQLException ex) {
                    Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                resultado = 1;
            }
        }else{
            resultado = -1;
        }
        return resultado;
    }
    
    public boolean existeMedicacion(int cod){
        boolean existe = false;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select * from inventario_farmacia where cod_medicamento = " + cod);
            while(rs.next()){
                existe = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existe;
    }

    @Override
    public ArrayList listadoRecetas(int cod_paciente) {
        ArrayList<Receta> recetas = new ArrayList();
        String sql = "select * from recetas where cod_paciente = " + cod_paciente;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while(rs.next()){
                Receta r = new Receta(rs.getInt(2), rs.getInt(3), rs.getString(5), rs.getDate(4));
                recetas.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recetas;
    }

    @Override
    public ArrayList listadoTratamientos() {
        ArrayList<DiagnosticoPaciente> d = new ArrayList();
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select * from diagnostico_paciente");
            while(rs.next()){
                DiagnosticoPaciente dp = new DiagnosticoPaciente(rs.getInt(1),rs.getInt(2),rs.getString(3),
                rs.getInt(4),rs.getString(5),rs.getDate(6));
                
                d.add(dp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    @Override
    public ArrayList consultaTratamientos(int cod) {
        ArrayList<DiagnosticoPaciente> d = new ArrayList();
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select * from diagnostico_paciente where cod_paciente = " + cod);
            while(rs.next()){
                DiagnosticoPaciente dp = new DiagnosticoPaciente(rs.getInt(1),rs.getInt(2),rs.getString(3),
                rs.getInt(4),rs.getString(5),rs.getDate(6));
                
                d.add(dp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosticoPacienteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }


}
