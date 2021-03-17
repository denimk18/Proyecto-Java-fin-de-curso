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
public class PedidosAdministrativo {
    
    String nombre;
    int unidades;
    Date fecha_pedido;

    public PedidosAdministrativo(String nombre, int unidades, Date fecha_pedido) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.fecha_pedido = fecha_pedido;
    }

    public PedidosAdministrativo() {
        super();
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

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    @Override
    public String toString() {
        return "PedidosAdministrativo{" + "nombre=" + nombre + ", unidades=" + unidades + ", fecha_pedido=" + fecha_pedido + '}';
    }
    
    
    
}
