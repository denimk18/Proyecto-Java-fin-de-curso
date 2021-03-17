/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 * Operaciones realcionadas con el inventario de la farmacia.
 * También con la recepción (al recepcionar un medicamento este se incluye en el stock)
 * @author Denitsa
 */
public interface InventarioFarmaciaDAO {
    
    public ArrayList inventario();
    public InventarioFarmacia consultaMedicamento(int codMedicamento);
    public boolean recepcionMedicamento(RecepcionMedicacion medicamento);
    public ArrayList listadoRecepcion();
    public RecepcionMedicacion consultaMedicamentoRecepcion(int codMedicamento);
}
