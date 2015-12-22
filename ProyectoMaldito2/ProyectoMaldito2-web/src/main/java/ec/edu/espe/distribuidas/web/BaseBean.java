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
package ec.edu.espe.distribuidas.web;

import java.io.Serializable;

/**
 * Clase BASEBEAN para la implementacion del CRUD.
 *
 * @author R&R S.A.
 */
public class BaseBean implements Serializable {

    /**
     * variable que indica el estado del formulario para un nuevo registro.
     */
    private boolean enNuevo;

    /**
     * variable que indica el estado del formulario para modificar un registro.
     */
    private boolean enModificar;

    /**
     * metodo get de enNuevo.
     *
     * @return retirna el valor de ennuevo.
     */
    public boolean isEnNuevo() {
        return enNuevo;
    }

    /**
     * metodo set de ennuevo.
     *
     * @param enNuevo recibe un booleano para setear el valor de ennuevo.
     */
    public void setEnNuevo(boolean enNuevo) {
        this.enNuevo = enNuevo;
    }

    /**
     * metodo get de en modificar.
     *
     * @return retirna el valor de enmodificar.
     */
    public boolean isEnModificar() {
        return enModificar;
    }

    /**
     * metodo set de enmodificar.
     *
     * @param enModificar recibe un booleano para setear el valor de
     * enmodificar.
     */
    public void setEnModificar(boolean enModificar) {
        this.enModificar = enModificar;
    }

    /**
     * metodo generico que denotara la operacion Nuevo del CRUD.
     */
    public void nuevo() {
        this.enNuevo = true;
    }

    /**
     * metodo generico que denotara la operacion Modificar del CRUD.
     */
    public void modificar() {
        this.enModificar = true;
    }

    /**
     * metodo generico que denotara la operacion Modificar del CRUD
     */
    public void cancelar() {
        this.reset();

    }

    /**
     * metodo generico que denotara el reseteo de las variables ennuevo y enmodificar.
     */    
    public void reset() {
        this.enModificar = false;
        this.enNuevo = false;
    }
}
