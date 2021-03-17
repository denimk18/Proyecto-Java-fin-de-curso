/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;

/**
 *
 * @author Denitsa
 */
public class Receta {
    
    int cod_paciente;
    int cod_medicamento;
    String administracion;
    Date fecha_administracion;

    public Receta(int cod_paciente, int cod_medicamento, String administracion, Date fecha_administracion) {
        this.cod_paciente = cod_paciente;
        this.cod_medicamento = cod_medicamento;
        this.administracion = administracion;
        this.fecha_administracion = fecha_administracion;
    }

  

   

    public int getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(int cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public int getCod_medicamento() {
        return cod_medicamento;
    }

    public void setCod_medicamento(int cod_medicamento) {
        this.cod_medicamento = cod_medicamento;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public Date getFecha_administracion() {
        return fecha_administracion;
    }

    public void setFecha_administracion(Date fecha_administracion) {
        this.fecha_administracion = fecha_administracion;
    }

    @Override
    public String toString() {
        return "Receta{" + "cod_paciente=" + cod_paciente + ", cod_medicamento=" + cod_medicamento + ", administracion=" + administracion + ", fecha_administracion=" + fecha_administracion + '}';
    }

      
    
}
