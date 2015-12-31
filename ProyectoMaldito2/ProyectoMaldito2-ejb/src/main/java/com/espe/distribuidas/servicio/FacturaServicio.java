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

import com.espe.distribuidas.dao.FacturaDAO;
import com.espe.distribuidas.model.Factura;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de Facturacion, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class FacturaServicio {

    /**
     * variable que conecta con el DAO de Factura
     */
    @EJB
    private FacturaDAO factura;

    /**
     * Permite obtener todos las Facturas de la base de datos (referente
     * unicamente a la tabla factura).
     *
     * @return parametro tipo lista de facturas.
     */
    public List<Factura> obtenerTodasFacturas() {
        return this.factura.findAll();
    }
    
    public Factura findLast(){
    
        return this.factura.findAll().get(this.factura.findAll().size()-1);
    }

    /**
     * Permite realizar una busqueda para encontrar un factura por ID de
     * cliente.
     *
     * @param codigoFactura  parametro tipo Integer que define el Codigo de
     * factura buscar.
     * @return retorna el objeto factura de la base de datos.
     */
    public Factura obtenerFacturaPorID(Integer codigoFactura) {
        return this.factura.findById(codigoFactura, true);
    }

    /**
     * Permite ingresar nuevas facturaes a la base de datos.
     *
     * @param factura recibe un objeto factura a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que la factura ya existe.
     */
    public void ingresarFactura(Factura factura) throws ValidacionException {
            this.factura.insert(factura);
            
    }

    /**
     * Permite actualizar facturas  de la base de datos.
     *
     * @param factura  recibe un objeto factura a actualizar.
     */
    public void actualizarFactura(Factura factura) {
        this.factura.update(factura);
    }

    /**
     * Permite eliminar facturas de la base de datos.
     *
     * @param codigoFactura  recibe un objeto de Factura a eliminar.
     */
    public void eliminarFactura(Integer codigoFactura) {

        try {
            Factura facturatmp = this.obtenerFacturaPorID(codigoFactura);
            this.factura.remove(facturatmp);
        } catch (Exception e) {
            throw new ValidacionException("La Factura " + codigoFactura + " esta asociada");
        }
    }
}
