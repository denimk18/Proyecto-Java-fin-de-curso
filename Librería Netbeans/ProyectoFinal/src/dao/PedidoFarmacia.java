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
public class PedidoFarmacia {
    
    String nombre;
    int unidades_pedidas;
    Date fecha_pedido;

    public PedidoFarmacia(String nombre, int unidades_pedidas, Date fecha_pedido) {
        this.nombre = nombre;
        this.unidades_pedidas = unidades_pedidas;
        this.fecha_pedido = fecha_pedido;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUnidades_pedidas() {
        return unidades_pedidas;
    }

    public void setUnidades_pedidas(int unidades_pedidas) {
        this.unidades_pedidas = unidades_pedidas;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    @Override
    public String toString() {
        return "PedidoFarmacia{" + "nombre=" + nombre + ", unidades_pedidas=" + unidades_pedidas + ", fecha_pedido=" + fecha_pedido + '}';
    }

    
    
    
}
