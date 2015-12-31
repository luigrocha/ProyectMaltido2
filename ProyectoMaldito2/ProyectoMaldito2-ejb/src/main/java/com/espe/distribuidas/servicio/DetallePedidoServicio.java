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

import com.espe.distribuidas.dao.DetallePedidoDAO;
import com.espe.distribuidas.model.DetallePedido;
import com.espe.distribuidas.model.DetallePedidoPK;
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

public class DetallePedidoServicio {

    /**
     * variable que conecta con el DAO de pedido detalle
     */
    @EJB
    private DetallePedidoDAO detallePedido;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla pedido).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<DetallePedido> obtenerTodosDetallesPedidos() {
        return this.detallePedido.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un detalle pedido por ID de
     * detalle pedido.
     *
     * @param codigoDetallePedido  parametro tipo String que define el Codigo de
     * proveedor buscar.
     * @return retorna el objeto proveedor de la base de datos.
     */
    public DetallePedido obtenerPedidoesPorID(DetallePedidoPK codigoDetallePedido) {
        return this.detallePedido.findById(codigoDetallePedido, true);
    }

    /**
     * Permite ingresar nuevos pedidos a la base de datos.
     *
     * @param detallePedido recibe un objeto detalle pedido a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el detalle ya existe.
     */
    public void ingresarDetallePedido(DetallePedido detallePedido) throws ValidacionException {
            this.detallePedido.insert(detallePedido);
            
    }

    /**
     * Permite actualizar pedidos  de la base de datos.
     *
     * @param detallePedido  recibe un objeto pedido a actualizar.
     */
    public void actualizarDetallePedido(DetallePedido detallePedido) {
        this.detallePedido.update(detallePedido);
    }

    /**
     * Permite eliminar pedidos de la base de datos.
     *
     * @param codigoDetallePedido  recibe un objeto de Pedido a eliminar.
     */
    public void eliminarDetallePedido(DetallePedidoPK codigoDetallePedido) {

        try {
            DetallePedido pedidotmp = this.obtenerPedidoesPorID(codigoDetallePedido);
            this.detallePedido.remove(pedidotmp);
        } catch (Exception e) {
            throw new ValidacionException("El Pedido " + codigoDetallePedido + " esta asociado");
        }
    }
}
