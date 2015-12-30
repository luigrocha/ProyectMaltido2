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

import com.espe.distribuidas.dao.EmpleadoDAO;
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de empleados
 *
 * @author R&R S.A.
 */
@LocalBean
@Stateless
public class EmpleadoServicio {

    @EJB
    private EmpleadoDAO empleadoDAO;

    /**
     * Permite obtener todos los empleados de la base de datos (referente
     * unicamente a la tabla empleados).
     *
     * @return parametro tipo lista de empleados.
     */
    public List<Empleado> obtenerTodosEmpleados() {
        return this.empleadoDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un empleado por ID de
     * empleado.
     *
     * @param codigoEmpleado parametro tipo String que define el Codigo de
     * empleado buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public Empleado obtenerEmpleadoPorID(String codigoEmpleado) {
        return this.empleadoDAO.findById(codigoEmpleado, false);
    }

    /**
     * Permite ingresar nuevos empleados a la base de datos.
     *
     * @param empleado recibe un objeto empleado a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarEmpleado(Empleado empleado) throws ValidacionException {
        Empleado empleadotmp = this.obtenerEmpleadoPorID(empleado.getIdEmpleado());
        if (empleadotmp == null) {
            this.empleadoDAO.insert(empleado);
        } else {
            throw new ValidacionException("El codigo" + empleado.getIdEmpleado() + "ya existe");
        }
    }

    /**
     * Permite actualizar empleados de la base de datos.
     *
     * @param empleado recibe un objeto empleaso a actualizar.
     */
    public void actualizarEmpleado(Empleado empleado) {
        this.empleadoDAO.update(empleado);
    }

    /**
     * Permite eliminar empleados de la base de datos.
     *
     * @param idempleado recibe un id de empleado a eliminar.
     */
    public void eliminarEmpleado(String idempleado) {

        try {
            Empleado empleadotmp = this.obtenerEmpleadoPorID(idempleado);
            this.empleadoDAO.remove(empleadotmp);
        } catch (Exception e) {
            throw new ValidacionException("El empleado " + idempleado + " esta asociadao");
        }
    }

    /**
     * Permite consultar si un usurio y una contraseña son validos para la
     * autenticación dentro de la aplicacion.
     *
     * @param usuario cadena de texto que identifica al nombre de usuario.
     * @param password cadena de texro que identifica la contraseña.
     * @return retorna una lista de empleados que coincidan con el usuario y la
     * contraseña en este caso la coincidencia de la lista arrojaria una unica
     * coincidencia.
     */
    public List<Empleado> buscarPorUsuarioPassword(String usuario, String password) {
        Empleado empleado = new Empleado();
        empleado.setUsuario(usuario);
        empleado.setContrasena(password);
        return this.empleadoDAO.find(empleado);
    }

    /**
     * Permite consultar si un usurio es valido para ser registrado.
     *
     * @param usuario string del usuario.
     * @return
     */
    public List<Empleado> buscarPorUsuario(String usuario) {
        Empleado empleado = new Empleado();
        empleado.setUsuario(usuario);
        return this.empleadoDAO.find(empleado);
    }

    /**
     * Permite consultar si la lista de tecnicos de la empresa.
     *
     * @return retorna la lista tipo empleado.
     */
    public List<Empleado> buscasPorTecnico() {
        Empleado empleado = new Empleado();
        empleado.setTipoEmpleado("Tecnico");
        return this.empleadoDAO.find(empleado);
    }

}
