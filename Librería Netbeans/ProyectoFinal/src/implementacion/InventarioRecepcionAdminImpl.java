/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementacion;

import dao.InventarioAdministrativo;
import dao.InventarioRecepcionAdminDAO;
import dao.RecepcionMedicacion;
import dao.RecepcionOficina;
import factory.SqlDbDAOFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Denitsa
 */
public class InventarioRecepcionAdminImpl implements InventarioRecepcionAdminDAO{

     
     Connection conexion;

    public InventarioRecepcionAdminImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
    
    @Override
    public ArrayList listadoInventario() {
        ArrayList<InventarioAdministrativo> lista = new ArrayList();
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from material_oficina");
             while(rs.next()){
                 InventarioAdministrativo i = new InventarioAdministrativo(rs.getString(1),
                 rs.getInt(2), rs.getDate(3));
                 lista.add(i);
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return lista;
    }

    @Override
    public InventarioAdministrativo consultaMaterial(String nombre) {
        InventarioAdministrativo i = null;
         Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from material_oficina where nombre = '" + nombre + "'");
             while(rs.next()){
                 i = new InventarioAdministrativo(rs.getString(1),
                 rs.getInt(2), rs.getDate(3));
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return i;
    }

   /**
    * Sirve para registrar en el inventario el material que se ha pedido y ha llegado al centro.
    * Se comprueba si lo que nos ha llegado existe en pedidos. (No podemos meter algo en el inventario que no 
    * hemos pedido. Si existe en la lista de pedidos comprueba si ya está en el inventario. Para eso utiliza
    * el método existeMaterial(). Si existe actualiza el stock y la fecha de llegada. De lo contrario, si no 
    * existe en el inventario significa que es la primera vez que se pide, por lo tanto se hace un insert.
    * Para actualiza e insertar usamos los métodos actualizaInventario e insertaInventario.
    * 
    * @param r
    * @return  int ok
    * -->Si ok está a 0 es que se ha hecho todo correctamente
    * -->Si ok está a 1 es que lo que se intenta recepcionar no ha sido pedido.
    * -->Si ok está a -1 ha habido cualquier otro problema
    */
    @Override
    public int recepcionaOficina(RecepcionOficina r) {
        int ok = -1;
            if (existePedido(r.getNombre())) { //Comprueba Si el material que vamos a recepcionar existe en pedidos
                if(existeMaterial(r.getNombre())){
                    if(actualizaInventario(r)){
                        ok = 0;
                    }
                }else{
                    if(insertaInventario(r)){
                        ok = 0;
                    }
                }
            }else{
                ok = 1; //ERROR. no existe un pedido de ese material
            }

        return ok;
    }
    
      /**
     * Método usado en recepcionaOficina. Si dicho material no existe (es decir, primera vez que
     * se pide) se inserta en el inventario.
     * @param r
     * @return boolean recepcionado
     */
    public boolean insertaInventario(RecepcionOficina r){
        boolean recepcionado = false;
         String sql = String.format("INSERT INTO MATERIAL_OFICINA VALUES('%s',%d,'%s');",
                        r.getNombre(), r.getUnidades(), r.getFecha_llegada());

                try {
                    Statement sentencia = conexion.createStatement();
                    int filas = sentencia.executeUpdate(sql);
                    if (filas >= 1) {
                        recepcionado = true;
                    }
                    if (recepcionado) {
                        sentencia.executeUpdate("delete from pedidos_oficina where nombre = '" + r.getNombre() + "'");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
        return recepcionado;
    }

    /**
     * Método usado en recepcionaOficina. Si dicho material existe (por ejemplo, tenemos en el inventario
     * archivadores con código 20 y nos llega al centro archivadores con código 20) se actualiza el stock
     * y la fecha de llegada.
     * @param r
     * @return boolean recepcionado
     */
    public boolean actualizaInventario(RecepcionOficina r){
        boolean recepcionado = false;
           try {
                    Statement sentencia = conexion.createStatement();

                    String sql = String.format("Select unidades from material_oficina where nombre = '%s' ",
                           r.getNombre());

                    ResultSet rs = sentencia.executeQuery(sql);
                    int unidades = 0;
                    while (rs.next()) {
                        unidades = rs.getInt(1);
                    }

                    unidades = unidades + r.getUnidades();

                    String update = String.format("UPDATE MATERIAL_OFICINA SET UNIDADES = %d, fecha_llegada = '%s'"
                            + " where nombre = '%s' ", unidades, r.getFecha_llegada(), r.getNombre());

                    int filas = sentencia.executeUpdate(update);
                    if (filas >= 1) {
                        recepcionado = true;
                    }
                    if (recepcionado) {
                        sentencia.executeUpdate("delete from pedidos_oficina where nombre = '" + r.getNombre() + "'");

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
        return recepcionado;
    }
    
    
    /**
     * Comprueba si el material que se va a recepcionar existe en los pedidos.
     * (No se puede recepcionar algo que no se ha pedido)
     * @param nombre
     * @return boolean existe
     */
    
    public boolean existePedido(String nombre){
        boolean existe = false;
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from pedidos_oficina where nombre =  '" + nombre + "'");
             while(rs.next()){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
    
    /**
     * Inserta el medicamento que haya llegado en la tabla de recepcion_medicacion
     * De esta manera, cuando el farmacéutico inicie sesión y la consulte podrá ver cuáles han llegado
     * para así incluirlos en el inventario de farmacia.
     * 
     * Comprueba que no haya otro medicamento igual en la tabla para recepcionar
     * Comprueba que lo que vamos a recepcionar existe en la tabla pedidos. (No podemos recepcionar algo
     * que no hemos pedido)
     * 
     * Una vez hechas estas comprobaciones, podemos recepcionar medicamentos sólo en dos situaciones:
     * --> Si es la 1ª vez que se pide el medicamento. (Cod y nombre no coinciden AMBOS con ninguno del inventario)
     * --> Si ya se ha pedido anteriormente (Cod y nombre coinciden AMBOS con uno del inventario)
     * 
     * De lo contrario, si coincide el cod pero no el nombre (y viceversa) con alguno del inventario seguramente
     * sea error de la persona introduciendo datos. Saltaría mensaje. Para eso utilizo el método
     * compruebaMedicamento
     * 
     * Si todo está ok devuelve un int = 0
     * --> Si ok = 0 la operación es correcta. (Al insertarlo en la tabla recepcion_medicacion)
     * -->Si devuelve 1 ya existe dicho medicamento en la tabla de recepcion
     * -->Si devuelve 2 lo que vamos a recepcionar no existe en la tabla pedidos. (Por lo tanto nunca
     * ha sido pedido)
     * -->Si devuelve 3 el nombre introducido es incorrecto
     * -->Si devuelve 4 el código es incorrrecto
     * @param medicamento
     * @return int recepcionado
     */
    
    @Override
    public int recepcionaFarmacia(RecepcionMedicacion medicamento) {
        int recepcionado = -1;
        if (!existeRecepcionMedicamento(medicamento.getCod_medicamento())) { //Para evitar medicamentos a recepcionar duplicados
            if (existeMedicamentoPedido(medicamento.getNombre())) { //Evita recepcionar medicamentos que no se han pedido
                //Si no existe en el inventario es la 1ª vez que se pide, se inserta en la tabla de recepción
                if(!existeMedicamentoCod(medicamento.getCod_medicamento()) && !existeMedicamentoNombre(medicamento.getNombre())){
                    if(insertaMedicamentoRecepcion(medicamento)){recepcionado = 0;}
                }else{ //Si existe en el inventario antes se comprueba que los datos introducidos tengan integridad
                  
                    //En resultado guardamos el resultado de la operación
                    int resultado = compruebaMedicamento(medicamento.getCod_medicamento(),medicamento.getNombre());
                   
                   //Si resultado = 0 significa que el código y el nombre introducidos coinciden con los registrados
                   //en el inventario. Se inserta en la tabla de recepción
                   if(resultado == 0){
                        if(insertaMedicamentoRecepcion(medicamento)){recepcionado = 0;}
                   }
                   
                   //Si resultado == 1 nombre incorrecto.
                   //Es decir, tenemos cod 12 ibuprofeno y la persona ha introducido cod 12 naproxeno
                   if(resultado == 1){
                       recepcionado = 3; //nombre incorrecto
                   }
                   
                   //Si resultado == 2 codigo incorrecto
                   //Es decir, tenemos cod 12 ibuprofeno y la persona ha introducido cod 15 ibuprofeno
                   if(resultado == 2){
                       recepcionado = 4; //codigo incorrecto
                   }
                } 
           
            }else{
                recepcionado = 2;
            }
        }else{
            recepcionado = 1;
        }

        return recepcionado;
    }
    
    /**
     * Método que inserta un medicamento que ha llegado al centro en la tabla de recepción.
     * @param medicamento
     * @return boolean insertado.
     */
    public boolean insertaMedicamentoRecepcion(RecepcionMedicacion medicamento){
        boolean insertado = false;
        String sql = String.format("INSERT INTO RECEPCION_MEDICACION VALUES(%d, '%s','%s',%d,%d);", medicamento.getCod_medicamento(),
                        medicamento.getNombre(), medicamento.getFecha_llegada(), medicamento.getUnidades_pedidas(),medicamento.getUnidades_recibidas());

                try {
                    Statement sentencia = conexion.createStatement();
                    int filas = sentencia.executeUpdate(sql);
                    if (filas >= 1) {
                        insertado = true;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(InventarioFarmaciaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
        return insertado;
    }
  
    
    /**
     * Comprueba si un medicamento está en el inventario buscando por código.
     * @param cod
     * @return boolean existe
     */
    
    public boolean existeMedicamentoCod(int cod){
       boolean existe = false;
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from inventario_farmacia where cod_medicamento =  " + cod);
             while(rs.next()){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
    
    /**
     * Comprueba si existe un medicamento buscando por su nombre en el inventario de la farmacia
     * @param nombre
     * @return boolean existe
     */
    public boolean existeMedicamentoNombre(String nombre){
          boolean existe = false;
        Statement sentencia;
         try {
             sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from inventario_farmacia where nombre =  '" + nombre + "'");
             while(rs.next()){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
    
    /**
     Si coinciden el cod y el nombre que se pasan al método con los registrados en el inventario devuelve 0
     * Es decir, si en el inventario tenemos registrado con código 12 el medicamento ibuprofeno y los valores
     * pasados no coinciden devolverá el dato que se ha introducido erróneamente.
     * 
     * @param codMedicamento
     * @param nombreMedicamento
     * @return int resultado
     * -->Resultado = 0 datos ok
     * -->Resultado = 1 nombre incorrecto
     * -->Resultado = 2 codigo incorrecto.
     */
    public int compruebaMedicamento(int codMedicamento, String nombreMedicamento){
        int resultado = -1;
        
         try {
             Statement sentencia = conexion.createStatement();
             //Primero compruebo si coincide el nombre que se nos pasa con el nombre que hay registrado 
             //en el inventario
             ResultSet rs = sentencia.executeQuery("select nombre from inventario_farmacia"
                     + " where cod_medicamento = " + codMedicamento);
             
             //Si nombreCorrecto se pone a 0 es que el que se ha introducido por teclado está bien.
             //De lo contrario se pone a 1
             int nombreCorrecto = -1;
             while(rs.next()){
                 //Paso ambos nombres a minúsculas y los comparo, para que no haya fallos en caso de que
                 //sean los mismos nombres pero uno en mayus y otro en minus
                 if(nombreMedicamento.toLowerCase().equals(rs.getString(1).toLowerCase())){
                     nombreCorrecto = 0;
                 }else{
                     resultado = 1; //nombre introducido incorrecto
                 }
             }
             
             //Ahora compruebo si coincide el cod que se nos pasa con el registrado en el inventario
             rs = sentencia.executeQuery("select cod_medicamento from inventario_farmacia"
                     + " where nombre = '" + nombreMedicamento + "'");
             
             //Si codCorrecto se pone a 0 es que el que se ha introducido por teclado está bien.
             //De lo contrario se pone a 2
             int codCorrecto = -1;
             while(rs.next()){
                  if(codMedicamento == rs.getInt(1)){
                     codCorrecto = 0;
                 }else{
                     resultado = 2; //codigo introducido incorrecto
                 }
             }
             
             //Si ambas variables están a 0 significa que los datos introducidos son correctos, por lo tanto
             //el resultado = 0, indicando que todo está bien.
             if(nombreCorrecto == 0 && codCorrecto == 0){
                 resultado = 0;
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return resultado;
    }
   
    
    
    /**
     * Comprueba si existe cierto material de oficina buscando por el nombre
     * @param nombre
     * @return boolean existe
     */
    public boolean existeMaterial(String nombre){
        boolean existe = false;
         try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from material_oficina where nombre = '" + nombre + "'");
             while(rs.next()){
                 existe = true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
        return existe;
    }
   
    /**
     * Comprueba si el medicamento que vamos a recepcionar existe en la tabla pedidos
     * @param nombre
     * @return boolean existe
     */
    
    public boolean existeMedicamentoPedido(String nombre){
        
        boolean existe = false;
        try {
             Statement sentencia = conexion.createStatement();
             ResultSet rs = sentencia.executeQuery("select * from pedido_farmacia where nombre = '" + nombre + "'");
             while(rs.next()){
                 existe = true;
             }
         
         } catch (SQLException ex) {
             Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return existe;
    }
 
  
    /**
     * Comprueba si ya existe un registro (es decir un medicamento con el código que se pasa) en la tabla
     * para recepcionar
     */
    
    public boolean existeRecepcionMedicamento(int cod) {
        boolean existe = false;
        try {
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("select * from recepcion_medicacion where cod_medicamento = " + cod);
            while (rs.next()) {
                existe = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventarioRecepcionAdminImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
}
