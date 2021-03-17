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
public class RecepcionMedicacion {
    
     
    int cod_medicamento;
    String nombre;
    Date fecha_llegada;
    int unidades_pedidas;
    int unidades_recibidas;

    public RecepcionMedicacion(int cod_medicamento, String nombre, Date fecha_llegada, int unidades_pedidas, int unidades_recibidas) {
        this.cod_medicamento = cod_medicamento;
        this.nombre = nombre;
        this.fecha_llegada = fecha_llegada;
        this.unidades_pedidas = unidades_pedidas;
        this.unidades_recibidas = unidades_recibidas;
    }

   
    

    
    public int getUnidades_pedidas() {
        return unidades_pedidas;
    }

    public void setUnidades_pedidas(int unidades_pedidas) {
        this.unidades_pedidas = unidades_pedidas;
    }

    public int getUnidades_recibidas() {
        return unidades_recibidas;
    }

    public void setUnidades_recibidas(int unidades_recibidas) {
        this.unidades_recibidas = unidades_recibidas;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(Date fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public int getCod_medicamento() {
        return cod_medicamento;
    }

    public void setCod_medicamento(int cod_medicamento) {
        this.cod_medicamento = cod_medicamento;
    }

    @Override
    public String toString() {
        return "RecepcionMedicacion{" + "nombre=" + nombre + ", fecha_llegada=" + fecha_llegada + ", cod_medicamento=" + cod_medicamento + ", unidades_pedidas=" + unidades_pedidas + ", unidades_recibidas=" + unidades_recibidas + '}';
    }

 
    
}
