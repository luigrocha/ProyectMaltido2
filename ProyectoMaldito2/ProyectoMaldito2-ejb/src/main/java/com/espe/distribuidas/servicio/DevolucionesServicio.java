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

import com.espe.distribuidas.dao.DevolucionesDAO;
import com.espe.distribuidas.model.Devoluciones;
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

public class DevolucionesServicio {

    /**
     * variable que conecta con el DAO de proveedor
     */
    @EJB
    private DevolucionesDAO devolucion;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla devolucion).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<Devoluciones> obtenerTodasDevoluciones() {
        return this.devolucion.findAll();
    }
    
    public Devoluciones findLast(){
    
        return this.devolucion.findAll().get(this.devolucion.findAll().size()-1);
    }

    /**
     * Permite realizar una busqueda para encontrar un proveedor por ID de
     * proveedor.
     *
     * @param codigoDevolucion  parametro tipo String que define el Codigo de
     * proveedor buscar.
     * @return retorna el objeto proveedor de la base de datos.
     */
    public Devoluciones obtenerDevolucionesPorID(Integer codigoDevolucion) {
        return this.devolucion.findById(codigoDevolucion, true);
    }

    /**
     * Permite ingresar nuevas devoluciones a la base de datos.
     *
     * @param devolucion recibe un objeto cliente a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarDevolucion(Devoluciones devolucion) throws ValidacionException {
            this.devolucion.insert(devolucion);
            
    }

    /**
     * Permite actualizar devoluciones  de la base de datos.
     *
     * @param devolucion  recibe un objeto devolucion a actualizar.
     */
    public void actualizarDevolucion(Devoluciones devolucion) {
        this.devolucion.update(devolucion);
    }

    /**
     * Permite eliminar devoluciones de la base de datos.
     *
     * @param codigoDevolucion  recibe un objeto de Devolucion a eliminar.
     */
    public void eliminarDevolucion(Integer codigoDevolucion) {

        try {
            Devoluciones devoluciontmp = this.obtenerDevolucionesPorID(codigoDevolucion);
            this.devolucion.remove(devoluciontmp);
        } catch (Exception e) {
            throw new ValidacionException("La Devolucion " + codigoDevolucion + " esta asociada");
        }
    }
}
