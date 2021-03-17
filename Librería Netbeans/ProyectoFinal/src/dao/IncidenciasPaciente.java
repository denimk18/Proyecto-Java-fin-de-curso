package dao;

import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Denitsa
 */
public class IncidenciasPaciente {
    
    int cod_incidencia;
    int cod_paciente;
    int cod_medicamento;
    int cantidad;
    Date fecha;
    String causa;
    String cura;
    String comunicado_familiares;

    public IncidenciasPaciente(int cod_incidencia, int cod_paciente, int cod_medicamento, int cantidad, Date fecha, String causa, String cura, String comunicado_familiares) {
        this.cod_incidencia = cod_incidencia;
        this.cod_paciente = cod_paciente;
        this.cod_medicamento = cod_medicamento;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.causa = causa;
        this.cura = cura;
        this.comunicado_familiares = comunicado_familiares;
    }

  

  

    public int getCod_incidencia() {
        return cod_incidencia;
    }

    public void setCod_incidencia(int cod_incidencia) {
        this.cod_incidencia = cod_incidencia;
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

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getCura() {
        return cura;
    }

    public void setCura(String cura) {
        this.cura = cura;
    }

    public String getComunicado_familiares() {
        return comunicado_familiares;
    }

    public void setComunicado_familiares(String comunicado_familiares) {
        this.comunicado_familiares = comunicado_familiares;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "IncidenciasPaciente{" + "cod_incidencia=" + cod_incidencia + ", cod_paciente=" + cod_paciente + ", cod_medicamento=" + cod_medicamento + ", cantidad=" + cantidad + ", fecha=" + fecha + ", causa=" + causa + ", cura=" + cura + ", comunicado_familiares=" + comunicado_familiares + '}';
    }

 
    
    
}
