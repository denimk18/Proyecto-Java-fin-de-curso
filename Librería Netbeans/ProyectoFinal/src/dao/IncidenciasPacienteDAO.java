/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *Operaciones relacionadas con las incidencias de paciente. Las realizan las enfermeras.
 * @author Denitsa
 */
public interface IncidenciasPacienteDAO {
    
    public boolean insertaIncidencia(IncidenciasPaciente incidencia);
    public ArrayList listadoIncidencias();
    public ArrayList incidenciasPaciente(int codPaciente); //devolver arrayList de pacientes con todas sus incidencias
    
}
