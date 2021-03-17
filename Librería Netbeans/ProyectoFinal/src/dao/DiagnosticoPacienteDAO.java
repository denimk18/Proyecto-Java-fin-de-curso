/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *  Todas las operaciones que pueden hacer los médicos con los pacientes
 * @author Denitsa
 */
public interface  DiagnosticoPacienteDAO  {
    
    public int altaPacientes(Paciente p);
    public boolean bajaPacientes(int codPaciente);
    public ArrayList listadoPacientes();
    public Paciente consultaPaciente(int cod);
    public boolean modificaPacientes(int cod, Paciente p);
    public int insertaTratamientos(int codPaciente, DiagnosticoPaciente diag);
    //tratamientos = DiagnosticoPaciente
    public ArrayList listadoTratamientos();
    public ArrayList consultaTratamientos(int cod);
    
    public int insertaRecetas(Receta r);
    public ArrayList listadoRecetas(int cod_paciente);
   
}
