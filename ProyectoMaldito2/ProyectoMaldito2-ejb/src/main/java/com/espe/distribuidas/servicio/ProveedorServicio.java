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

import com.espe.distribuidas.dao.ProveedorDAO;
import com.espe.distribuidas.model.Proveedor;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase de servicio de Proveedores
 *
 * @author R&R S.A.
 */
@LocalBean
@Stateless

public class ProveedorServicio {

    
    @EJB
    private ProveedorDAO proveedorDAO;

    /**
     * Permite obtener todos los proveedores de la base de datos (referente
     * unicamente a la tabla proveedores).
     *
     * @return parametro tipo lista de proveedores.
     */
    public List<Proveedor> obtenerTodosProveedores() {
        return this.proveedorDAO.findAll();
    }

    /**
     * Permite realizar una busqueda para encontrar un proveedor por ID de proveedor.
     *
     * @param codigoProveedor  parametro tipo String que define el Codigo de proveedor a
     * buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public Proveedor obtenerProveedorPorID(String codigoProveedor) {
        return this.proveedorDAO.findById(codigoProveedor, false);
    }

    /**
     * Permite ingresar nuevos proveedor a la base de datos.
     *
     * @param proveedor  recibe un objeto proveedor a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el proveedor ya existe.
     */
    public void ingresarProveedor(Proveedor proveedor) throws ValidacionException {
        Proveedor proveedortmp = this.obtenerProveedorPorID(proveedor.getIdProveedor());
        if (proveedortmp == null) {
            this.proveedorDAO.insert(proveedor);
        } else {
            throw new ValidacionException("El codigo" + proveedor.getIdProveedor()+ "ya existe");
        }
    }

    /**
     * Permite actualizar proveedores de la base de datos.
     *
     * @param proveedor  recibe un objeto proveedor a actualizar.
     */
    public void actualizarProveedor(Proveedor proveedor) {
        this.proveedorDAO.update(proveedor);
    }

    /**
     * Permite eliminar proveedores de la base de datos.
     *
     * @param idproveedor  recibe un id de insumo a eliminar.
     */
    public void eliminarProveedor(String idproveedor) {

        try {
            Proveedor proveedortmp = this.obtenerProveedorPorID(idproveedor);
            this.proveedorDAO.remove(proveedortmp);
        } catch (Exception e) {
            throw new ValidacionException("El proveedor " + idproveedor + " esta asociada");
        }
    }

}
