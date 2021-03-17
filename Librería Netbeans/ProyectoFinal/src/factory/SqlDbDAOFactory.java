/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import dao.*;
import implementacion.DiagnosticoPacienteImpl;
import implementacion.FarmacosEnfermeraImpl;
import implementacion.IncidenciasPacienteImpl;
import implementacion.InventarioFarmaciaImpl;
import implementacion.InventarioRecepcionAdminImpl;
import implementacion.PedidoFarmaciaImpl;
import implementacion.PedidosAdministrativoImpl;
import implementacion.VisitasImpl;
/**
 *
 * @author Denitsa
 */

    
 public class SqlDbDAOFactory extends DAOFactory {

    static Connection conexion = null;
    static String DRIVER = "";
    static String URLDB = "";
    static String USUARIO = "proyecto";
    static String CLAVE = "proyecto";

    public SqlDbDAOFactory() {
        DRIVER = "com.mysql.jdbc.Driver";
        URLDB = "jdbc:mysql://localhost/proyecto";
    }

    // crear la conexion
    public static Connection crearConexion() {
        if (conexion == null) {
            try {
                Class.forName(DRIVER); // Cargar el driver
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SqlDbDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                conexion = DriverManager.getConnection(URLDB, USUARIO, CLAVE);
            } catch (SQLException ex) {
                System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
                System.out.printf("Mensaje   : %s %n", ex.getMessage());
                System.out.printf("SQL estado: %s %n", ex.getSQLState());
                System.out.printf("Cód error : %s %n", ex.getErrorCode());
            }
        }
        return conexion;
    }

    @Override
    public DiagnosticoPacienteDAO getDiagnosticoPacienteDAO() {
        return new DiagnosticoPacienteImpl();
    }

    @Override
    public FarmacosEnfermeraDAO getFarmacosEnfermeraDAO() {
        return new FarmacosEnfermeraImpl();
    }

    @Override
    public IncidenciasPacienteDAO getIncidenciasPacienteDAO() {
        return new IncidenciasPacienteImpl();
    }

    @Override
    public InventarioFarmaciaDAO getInventarioFarmaciaDAO() {
        return new InventarioFarmaciaImpl();
    }

    @Override
    public PedidoFarmaciaDAO getPedidoFarmaciaDAO() {
        return new PedidoFarmaciaImpl();
    }

    @Override
    public VisitasDAO getVisitasDAO() {
         return new VisitasImpl();
    }

    @Override
    public PedidosAdministrativoDAO getPedidosAdministrativoDAO() {
        return new PedidosAdministrativoImpl();
    }

    @Override
    public InventarioRecepcionAdminDAO getInventarioRecepcionAdminDAO() {
        return new InventarioRecepcionAdminImpl();
    }


  
    
}
