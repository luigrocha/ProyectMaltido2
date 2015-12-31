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

import com.espe.distribuidas.dao.DetalleFacturaDAO;
import com.espe.distribuidas.model.DetalleFactura;
import com.espe.distribuidas.model.DetalleFacturaPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de detalle Factura, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class DetalleFacturaServicio {

    /**
     * variable que conecta con el DAO de proveedor
     */
    @EJB
    private DetalleFacturaDAO detalleFactura;

    /**
     * Permite obtener todos los detalles Factura de la base de datos (referente
     * unicamente a la tabla detalle factura).
     *
     * @return parametro tipo lista de detalle Factura.
     */
    public List<DetalleFactura> obtenerTodosDetallesFacturas() {
        return this.detalleFactura.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un detalle por ID de
     * detalle Factura.
     *
     * @param codigoDetalleFactura  parametro tipo String que define el Codigo de
     * detalle Factura buscar.
     * @return retorna el objeto detallefactura de la base de datos.
     */
    public DetalleFactura obtenerFacturaesPorID(DetalleFacturaPK codigoDetalleFactura) {
        return this.detalleFactura.findById(codigoDetalleFactura, true);
    }

    /**
     * Permite ingresar nuevos detalle facturaes a la base de datos.
     *
     * @param detalleFactura recibe un objeto detalleFactura a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el detalle ya existe.
     */
    public void ingresarDetalleFactura(DetalleFactura detalleFactura) throws ValidacionException {
            this.detalleFactura.insert(detalleFactura);
            
    }

    /**
     * Permite actualizar detalle facturas  de la base de datos.
     *
     * @param detalleFactura  recibe un objeto detalle factura a actualizar.
     */
    public void actualizarDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura.update(detalleFactura);
    }

    /**
     * Permite eliminar detalle facturas de la base de datos.
     *
     * @param codigoDetalleFactura  recibe un objeto de Factura a eliminar.
     */
    public void eliminarDetalleFactura(DetalleFacturaPK codigoDetalleFactura) {

        try {
            DetalleFactura detallefacturatmp = this.obtenerFacturaesPorID(codigoDetalleFactura);
            this.detalleFactura.remove(detallefacturatmp);
        } catch (Exception e) {
            throw new ValidacionException("El detalle Factura " + codigoDetalleFactura + " esta asociado");
        }
    }
}
