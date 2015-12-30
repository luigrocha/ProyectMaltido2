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

import com.espe.distribuidas.dao.CitaMantenimientoDAO;
import com.espe.distribuidas.model.CitaMantenimiento;
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

public class CitaMantenimientoServicio {

    /**
     * variable que conecta con el DAO de cliente
     */
    @EJB
    private CitaMantenimientoDAO citaMantenimiento;

    /**
     * Permite obtener todos los mantenimientos de la base de datos (referente
     * unicamente a la tabla mantenimiento).
     *
     * @return parametro tipo lista de citas de mantenimiento.
     */
    public List<CitaMantenimiento> obtenerTodasCitas() {
        return this.citaMantenimiento.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un cliente por ID de
     * cliente.
     *
     * @param codigoCita  parametro tipo String que define el Codigo de
     * cliente buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public CitaMantenimiento obtenerCitaPorID(Integer codigoCita) {
        return this.citaMantenimiento.findById(codigoCita, false);
    }

    /**
     * Permite ingresar nuevas citas de mantenimiento a la base de datos.
     *
     * @param citaMantenimiento recibe un objeto cliente a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarCitaMantenimiento(CitaMantenimiento citaMantenimiento) throws ValidacionException {
            this.citaMantenimiento.insert(citaMantenimiento);
    }

    /**
     * Permite actualizar clientes de la base de datos.
     *
     * @param citamantenimiento  recibe un objeto cita mantenimiento a actualizar.
     */
    public void actulizarCita(CitaMantenimiento citamantenimiento) {
        this.citaMantenimiento.update(citamantenimiento);
    }

    /**
     * Permite eliminar citas de la base de datos.
     *
     * @param idCita  recibe un objeto de cliente a eliminar.
     */
    public void eliminarCita(Integer idCita) {

        try {
            CitaMantenimiento citatmp = this.obtenerCitaPorID(idCita);
            this.citaMantenimiento.remove(citatmp);
        } catch (Exception e) {
            throw new ValidacionException("La cita: " + idCita + " esta asociada");
        }
    }
}
