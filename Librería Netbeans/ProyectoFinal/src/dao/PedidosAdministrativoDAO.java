/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author Denitsa
 */
public interface PedidosAdministrativoDAO {
    
    public boolean insertaPedido(PedidosAdministrativo p);
    public ArrayList listadoPedidos();
    public PedidosAdministrativo consultaPedido(String nombre);
    public boolean borraPedido(String nombre);
}
