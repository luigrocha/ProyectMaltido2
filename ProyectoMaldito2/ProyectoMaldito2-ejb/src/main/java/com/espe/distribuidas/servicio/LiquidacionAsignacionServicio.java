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

import com.espe.distribuidas.dao.LiquidacionAsignacionDAO;
import com.espe.distribuidas.model.LiquidacionAsignacion;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de Liquidacion Asignacion.
 *
 * @author R&R S.A.
 */
@LocalBean
@Stateless
public class LiquidacionAsignacionServicio {

    @EJB
    private LiquidacionAsignacionDAO liquidacionAsignacionDAO;

    /**
     * Permite obtener todos las liquidaciones de la base de datos (referente
     * unicamente a la tabla liquidacionAsignacion).
     *
     * @return parametro tipo lista de liquidacionAsignacion.
     */
    public List<LiquidacionAsignacion> obtenerTodos() {
        return this.liquidacionAsignacionDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un insumo por ID de insumo.
     *
     * @param codigoLiquidacion parametro tipo integer que define el Codigo de
     * asignacion buscar.
     * @return retorna el objeto asignacion de la base de datos.
     */
    public LiquidacionAsignacion obtenerPorID(Integer codigoLiquidacion) {
        return this.liquidacionAsignacionDAO.findById(codigoLiquidacion, true);
    }

    /**
     * Permite ingresar nuevos liquidaciones a la base de datos.
     *
     * @param liquidacion recibe un objeto empleado a insertar.
     *
     */
    public void ingresarInsumo(LiquidacionAsignacion liquidacion) {

        this.liquidacionAsignacionDAO.insert(liquidacion);

    }

}
