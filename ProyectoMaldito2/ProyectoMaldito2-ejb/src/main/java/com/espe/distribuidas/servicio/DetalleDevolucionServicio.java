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

import com.espe.distribuidas.dao.DetalleDevolucionDAO;
import com.espe.distribuidas.model.DetalleDevolucion;
import com.espe.distribuidas.model.DetalleDevolucionPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de cita mantenimiento, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class DetalleDevolucionServicio {

    /**
     * variable que conecta con el DAO de proveedor
     */
    @EJB
    private DetalleDevolucionDAO detalleDevolucion;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla devolucion).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<DetalleDevolucion> obtenerTodosDetallesDevoluciones() {
        return this.detalleDevolucion.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un proveedor por ID de
     * proveedor.
     *
     * @param codigoDetalleDevolucion  parametro tipo String que define el Codigo de
     * proveedor buscar.
     * @return retorna el objeto proveedor de la base de datos.
     */
    public DetalleDevolucion obtenerDevolucionesPorID(DetalleDevolucionPK codigoDetalleDevolucion) {
        return this.detalleDevolucion.findById(codigoDetalleDevolucion, true);
    }

    /**
     * Permite ingresar nuevas devoluciones a la base de datos.
     *
     * @param detalleDevolucion recibe un objeto cliente a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ValidacionException {
            this.detalleDevolucion.insert(detalleDevolucion);
            
    }

    /**
     * Permite actualizar devoluciones  de la base de datos.
     *
     * @param detalleDevolucion  recibe un objeto devolucion a actualizar.
     */
    public void actualizarDetalleDevolucion(DetalleDevolucion detalleDevolucion) {
        this.detalleDevolucion.update(detalleDevolucion);
    }

    /**
     * Permite eliminar devoluciones de la base de datos.
     *
     * @param codigoDetalleDevolucion  recibe un objeto de Devolucion a eliminar.
     */
    public void eliminarDetalleDevolucion(DetalleDevolucionPK codigoDetalleDevolucion) {

        try {
            DetalleDevolucion devoluciontmp = this.obtenerDevolucionesPorID(codigoDetalleDevolucion);
            this.detalleDevolucion.remove(devoluciontmp);
        } catch (Exception e) {
            throw new ValidacionException("La Devolucion " + codigoDetalleDevolucion + " esta asociada");
        }
    }
}
