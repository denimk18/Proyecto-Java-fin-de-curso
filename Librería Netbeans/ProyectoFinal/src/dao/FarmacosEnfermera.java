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
public class FarmacosEnfermera {
    
    int cod_paciente;
    int cod_farmaco;
    int cantidad;
    Date fecha_administracion;

    public FarmacosEnfermera() {
    }

    public FarmacosEnfermera(int cod_paciente, int cod_farmaco, int cantidad, Date fecha_administracion) {
        this.cod_paciente = cod_paciente;
        this.cod_farmaco = cod_farmaco;
        this.cantidad = cantidad;
        this.fecha_administracion = fecha_administracion;
    }

  

   

    public int getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(int cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public int getCod_farmaco() {
        return cod_farmaco;
    }

    public void setCod_farmaco(int cod_farmaco) {
        this.cod_farmaco = cod_farmaco;
    }

    public Date getFecha_administracion() {
        return fecha_administracion;
    }

    public void setFecha_administracion(Date fecha_administracion) {
        this.fecha_administracion = fecha_administracion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "FarmacosEnfermera{" + "cod_paciente=" + cod_paciente + ", cod_farmaco=" + cod_farmaco + ", cantidad=" + cantidad + ", fecha_administracion=" + fecha_administracion + '}';
    }

    
  
  
    
    
}
