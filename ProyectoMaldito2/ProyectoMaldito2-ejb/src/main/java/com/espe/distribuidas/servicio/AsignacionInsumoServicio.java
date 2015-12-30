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

import com.espe.distribuidas.dao.AsignacionInsumoDAO;
import com.espe.distribuidas.model.AsignacionInsumo;
import com.espe.distribuidas.model.AsignacionInsumoPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de asignacion servicio, define todas las operaciones del
 * CRUD y busqueda.
 *
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class AsignacionInsumoServicio {

    /**
     * variable que conecta con el DAO de asignacion insumo
     */
    @EJB
    private AsignacionInsumoDAO asignacionInsumoDAO;

    /**
     * Permite obtener todos los insumos asignados de la base de datos
     * (referente unicamente a la tabla mantenimiento).
     *
     * @return parametro tipo lista de insumos asignados.
     */
    public List<AsignacionInsumo> obtenerTodasInsumosAsignados() {
        return this.asignacionInsumoDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un insumo asignado por ID de
     * cliente.
     *
     * @param codigoAsignacionInsumo parametro tipo AsignacionInsumoPK que
     * define el Codigo de insumo asignado buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public AsignacionInsumo obtenerInsumoAsignadoPorID(AsignacionInsumoPK codigoAsignacionInsumo) {
        return this.asignacionInsumoDAO.findById(codigoAsignacionInsumo, false);
    }

    /**
     * Permite ingresar nuevos asignaciones de insumo a la base de datos.
     *
     * @param asignacionInsumo recibe un objeto AsignacionInsumo a insertar.
     * @throws ValidacionException retorna una exepcion predefinida.
     */
    public void ingresarInsumoAsignado(AsignacionInsumo asignacionInsumo) throws ValidacionException {
        this.asignacionInsumoDAO.insert(asignacionInsumo);
    }

    /**
     * Permite ingresar una lista de nuevos asignaciones de insumo a la base de datos.
     *
     * @param asignacionInsumo recibe una lista de asignacion Insumo a insertar.
     * @throws ValidacionException retorna una exepcion predefinida.
     */
    public void ingresarInsumoAsignado(List<AsignacionInsumo> asignacionInsumo) throws ValidacionException {
        for (int i = 0; i < asignacionInsumo.size(); i++) {
            this.asignacionInsumoDAO.insert(asignacionInsumo.get(i));
        }
    }

    /**
     * Permite actualizar la asignacion de insumos de la base de datos.
     *
     * @param asignacionInsumo recibe un objeto asignacion insumo a actualizar.
     */
    public void actulizarInsumoAsignado(AsignacionInsumo asignacionInsumo) {
        this.asignacionInsumoDAO.update(asignacionInsumo);
    }

    /**
     * Permite eliminar insumos asignados de la base de datos.
     *
     * @param idAsignacionInsumo recibe un objeto de mantenimiento a eliminar.
     */
    public void eliminarInsumosAsignados(AsignacionInsumo idAsignacionInsumo) {

        try {
            AsignacionInsumo insumoAsignadotmp = this.obtenerInsumoAsignadoPorID(idAsignacionInsumo.getPrimaryKey());
            this.asignacionInsumoDAO.remove(insumoAsignadotmp);
        } catch (Exception e) {
            throw new ValidacionException("Error no controlado de eliminacion por asociación");
        }
    }
}
