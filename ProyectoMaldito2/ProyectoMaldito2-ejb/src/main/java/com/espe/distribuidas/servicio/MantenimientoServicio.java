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

import com.espe.distribuidas.dao.MantenimientoDAO;
import com.espe.distribuidas.model.CitaMantenimiento;
import com.espe.distribuidas.model.Mantenimiento;
import com.espe.distribuidas.model.MantenimientoPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de mantenimiento, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class MantenimientoServicio {

    /**
     * variable que conecta con el DAO de cliente
     */
    @EJB
    private MantenimientoDAO mantenimientoDAO;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla mantenimiento).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<Mantenimiento> obtenerTodasMantenimiento() {
        return this.mantenimientoDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un cliente por ID de
     * cliente.
     *
     * @param codigoMantenimiento   parametro tipo MantenimientoPL que define el Codigo de
     * mantenimiento buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public Mantenimiento obtenerMantenimientoPorID(MantenimientoPK codigoMantenimiento) {
        return this.mantenimientoDAO.findById(codigoMantenimiento, false);
    }

    /**
     * Permite ingresar nuevos mantenimiento a la base de datos.
     *
     * @param mantenimiento  recibe un objeto mantenimiento a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarMantenimiento(Mantenimiento mantenimiento) throws ValidacionException {
            this.mantenimientoDAO.insert(mantenimiento);
    }

    /**
     * Permite actualizar mantenimientos de la base de datos.
     *
     * @param mantenimiento  recibe un objeto  mantenimiento a actualizar.
     */
    public void actulizarMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimientoDAO.update(mantenimiento);
    }

    /**
     * Permite eliminar mantenimientos de la base de datos.
     *
     * @param idmantenimiento   recibe un objeto de mantenimiento a eliminar.
     */
    public void eliminarMantenimiento(Mantenimiento idmantenimiento) {

        try {
            Mantenimiento mantenimientotmp = this.obtenerMantenimientoPorID(idmantenimiento.getPrimaryKey());
            this.mantenimientoDAO.remove(mantenimientotmp);
        } catch (Exception e) {
            throw new ValidacionException("El mantenimiento " + idmantenimiento + " esta asociada");
        }
    }
}
