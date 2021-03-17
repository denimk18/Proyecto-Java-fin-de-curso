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
public class DiagnosticoPaciente {
    int cod_diagnostico;
    int cod_paciente;
    String nombre_farmaco;
    int cantidad;
    String diagnostico;
    Date fecha_diagnostico;

    public DiagnosticoPaciente(int cod_diagnostico, int cod_paciente, String nombre_farmaco, int cantidad, String diagnostico, Date fecha_diagnostico) {
        this.cod_diagnostico = cod_diagnostico;
        this.cod_paciente = cod_paciente;
        this.nombre_farmaco = nombre_farmaco;
        this.cantidad = cantidad;
        this.diagnostico = diagnostico;
        this.fecha_diagnostico = fecha_diagnostico;
    }
    
   
    
    
    public int getCod_diagnostico() {
        return cod_diagnostico;
    }

    public void setCod_diagnostico(int cod_diagnostico) {
        this.cod_diagnostico = cod_diagnostico;
    }

    public int getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(int cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public String getNombre_farmaco() {
        return nombre_farmaco;
    }

    public void setNombre_farmaco(String nombre_farmaco) {
        this.nombre_farmaco = nombre_farmaco;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Date getFecha_diagnostico() {
        return fecha_diagnostico;
    }

    public void setFecha_diagnostico(Date fecha_diagnostico) {
        this.fecha_diagnostico = fecha_diagnostico;
    }

    @Override
    public String toString() {
        return "DiagnosticoPaciente{" + "cod_diagnostico=" + cod_diagnostico + ", cod_paciente=" + cod_paciente + ", nombre_farmaco=" + nombre_farmaco + ", cantidad=" + cantidad + ", diagnostico=" + diagnostico + ", fecha_diagnostico=" + fecha_diagnostico + '}';
    }

    
    
}
