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
public class RecepcionOficina {
    
    String nombre;
    int unidades;
    Date fecha_llegada;

    public RecepcionOficina(String nombre, int unidades, Date fecha_llegada) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.fecha_llegada = fecha_llegada;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public Date getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(Date fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    @Override
    public String toString() {
        return "RecepcionOficina{" + "nombre=" + nombre + ", unidades=" + unidades + ", fecha_llegada=" + fecha_llegada + '}';
    }

  
    
}
