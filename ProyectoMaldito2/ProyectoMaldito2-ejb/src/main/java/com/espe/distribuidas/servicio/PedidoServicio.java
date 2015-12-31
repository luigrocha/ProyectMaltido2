/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package com.espe.distribuidas.servicio;

import com.espe.distribuidas.dao.PedidoDAO;
import com.espe.distribuidas.model.Pedido;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de pedido, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class PedidoServicio {

    /**
     * variable que conecta con el DAO de proveedor
     */
    @EJB
    private PedidoDAO pedido;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla pedido).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<Pedido> obtenerTodosPedidos() {
        return this.pedido.findAll();
    }
    
    public Pedido findLast(){
    
        return this.pedido.findAll().get(this.pedido.findAll().size()-1);
    }

    /**
     * Permite realizar una busqueda para encontrar un proveedor por ID de
     * proveedor.
     *
     * @param codigoPedido  parametro tipo String que define el Codigo de
     * proveedor buscar.
     * @return retorna el objeto proveedor de la base de datos.
     */
    public Pedido obtenerPedidosPorID(Integer codigoPedido) {
        return this.pedido.findById(codigoPedido, true);
    }

    /**
     * Permite ingresar nuevos pedidos a la base de datos.
     *
     * @param pedido recibe un objeto pedido a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el proveedor ya existe.
     */
    public void ingresarPedido(Pedido pedido) throws ValidacionException {
            this.pedido.insert(pedido);
            
    }

    /**
     * Permite actualizar pedidos  de la base de datos.
     *
     * @param pedido  recibe un objeto pedido a actualizar.
     */
    public void actualizarPedido(Pedido pedido) {
        this.pedido.update(pedido);
    }

    /**
     * Permite eliminar pedidos de la base de datos.
     *
     * @param codigoPedido  recibe un objeto de Devolucion a eliminar.
     */
    public void eliminarPedido(Integer codigoPedido) {

        try {
            Pedido pedidotmp = this.obtenerPedidosPorID(codigoPedido);
            this.pedido.remove(pedidotmp);
        } catch (Exception e) {
            throw new ValidacionException("El Pedido " + codigoPedido + " esta asociado");
        }
    }
}
