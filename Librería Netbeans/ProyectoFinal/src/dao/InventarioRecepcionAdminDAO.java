/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 * En este paquete incluyo las operaciones necesarias para que los administrativos
 * recepcionen la medicación, el material de oficina pedido y mostrar y consultar el inventario de materiales
 * de oficina. He considerado hacerlo así porque al recepcionar un material de oficina ese se incluye en el 
 * inventario. Por otra parte los administrativos también recepcionan los medicamentos que llegan al centro , por
 * eso incluyo esa operación aquí. Al recepcionar un medicamento lo que se hace es insertarlo en la tabla
 * recepcion_medicación. Cuando un farmacéutico  consulta dicha tabla desde su inicio de sesión puede incluirlo en el inventario
 * de farmacia.
 * @author Denitsa
 */
public interface InventarioRecepcionAdminDAO {
    
    public ArrayList listadoInventario();
    public InventarioAdministrativo consultaMaterial(String nombre);
    public int recepcionaOficina(RecepcionOficina r);
    public int recepcionaFarmacia(RecepcionMedicacion r);
}
