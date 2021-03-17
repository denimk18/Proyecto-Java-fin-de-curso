/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 * Operaciones relacionadas con el pedido de la farmacia
 * @author Denitsa
 */
public interface PedidoFarmaciaDAO {
    
    public boolean insertarPedido(PedidoFarmacia p);
    public ArrayList todosPedidos();
    public ArrayList consultaPedido(String nombre);
    public ArrayList solicitudPedidos();
    public boolean borrarPedido(String nombre);
}
