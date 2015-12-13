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

import com.espe.distribuidas.dao.ClienteDAO;
import com.espe.distribuidas.model.Cliente;
import ec.edu.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de clientes, define todas las operaciones del CRUD y busqueda. 
 * @author R&R S.A.
 *
 */
@LocalBean
@Stateless

public class ClienteServicio {

    /**
     * variable que conecta con el DAO de cliente
     */
    @EJB
    private ClienteDAO clienteDAO;

    /**
     * Permite obtener todos los clientes de la base de datos (referente
     * unicamente a la tabla clientes).
     *
     * @return parametro tipo lista de clientes.
     */
    public List<Cliente> obtenerTodosClientes() {
        return this.clienteDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un cliente por ID de
     * cliente.
     *
     * @param codigoCliente parametro tipo String que define el Codigo de
     * cliente buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public Cliente obtenerClientePorID(String codigoCliente) {
        return this.clienteDAO.findById(codigoCliente, false);
    }

    public List<Cliente> obtenerClientePorParametro(String nombre, String... apellido) {
        return null;
    }

    /**
     * Permite ingresar nuevos clientes a la base de datos.
     *
     * @param cliente recibe un objeto cliente a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el cliente ya existe.
     */
    public void ingresarCliente(Cliente cliente) throws ValidacionException {
        Cliente clientetmp = this.obtenerClientePorID(cliente.getIdCliente());
        if (clientetmp == null) {
            this.clienteDAO.insert(cliente);
        } else {
            throw new ValidacionException("El codigo" + cliente.getIdCliente() + "ya existe");
        }
    }

    /**
     * Permite actualizar clientes de la base de datos.
     *
     * @param cliente recibe un objeto cliente a actualizar.
     */
    public void actulizarCliente(Cliente cliente) {
        this.clienteDAO.update(cliente);
    }

    /**
     *Permite eliminar clientes de la base de datos.
     * @param cliente recibe un objeto de cliente a eliminar.
     */
    public void eliminarCliente(Cliente cliente) {
        this.clienteDAO.update(cliente);
    }
}
