/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;
import dao.*;
/**
 *
 * @author Denitsa
 */
public abstract class DAOFactory {
    
  public static final int MYSQL = 1;  

 
 public abstract DiagnosticoPacienteDAO getDiagnosticoPacienteDAO();
 public abstract FarmacosEnfermeraDAO getFarmacosEnfermeraDAO();
 public abstract IncidenciasPacienteDAO getIncidenciasPacienteDAO();
 public abstract InventarioFarmaciaDAO getInventarioFarmaciaDAO(); 
 public abstract PedidoFarmaciaDAO getPedidoFarmaciaDAO();
 public abstract VisitasDAO getVisitasDAO();
 public abstract PedidosAdministrativoDAO getPedidosAdministrativoDAO();
 public abstract InventarioRecepcionAdminDAO getInventarioRecepcionAdminDAO();
  
  public static DAOFactory getDAOFactory(int bd) {  
    switch (bd) {
      case MYSQL:          
           return new SqlDbDAOFactory();     
     
      default           : 
          return null;
    }
  }
}
