/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 * Operaciones relacionados con los fármacos que administra la enfermera 
 * @author Denitsa
 */
public interface FarmacosEnfermeraDAO {
    
    public boolean insertaFarmacos(FarmacosEnfermera f);
    public ArrayList muestraDiagnostico (int codPaciente); //un paciente puede tener varios diagnosticos. Por eso arrayList
    
    public ArrayList listadoFarmacosAdministrados();
    public ArrayList pacienteFarmacoAdministrado(int codPaciente);
    
    public boolean solicitaFarmacos(PedidoFarmacia pedido);
 
}
