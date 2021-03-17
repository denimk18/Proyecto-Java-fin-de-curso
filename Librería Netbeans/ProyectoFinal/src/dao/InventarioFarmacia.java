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
public class InventarioFarmacia {
    
    int cod_medicamento;
    String nombre;
    int unidades;
    Date fecha_llegada;

    public InventarioFarmacia(int cod_medicamento, String nombre, int unidades, Date fecha_llegada) {
        this.cod_medicamento = cod_medicamento;
        this.nombre = nombre;
        this.unidades = unidades;
        this.fecha_llegada = fecha_llegada;
    }

    public int getCod_medicamento() {
        return cod_medicamento;
    }

    public void setCod_medicamento(int cod_medicamento) {
        this.cod_medicamento = cod_medicamento;
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
        return "InventarioFarmacia{" + "cod_medicamento=" + cod_medicamento + ", nombre=" + nombre + ", unidades=" + unidades + ", fecha_llegada=" + fecha_llegada + '}';
    }
    
    
}
