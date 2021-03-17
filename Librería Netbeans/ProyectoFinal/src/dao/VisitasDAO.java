/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 * Operaciones relacionadas con el registro de visitas. Los llevan a cabo los administrativos
 * @author Denitsa
 */
public interface VisitasDAO {
    
   public boolean insertaVisitas(Visitas v);
   public ArrayList listadoVisitas();
   public ArrayList consultaVisita(int codPaciente);
}
