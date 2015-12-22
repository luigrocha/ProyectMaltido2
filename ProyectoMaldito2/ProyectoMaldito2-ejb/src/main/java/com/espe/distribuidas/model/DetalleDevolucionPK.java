/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *   
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package com.espe.distribuidas.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa a la clave primaria de la  entidad DETALLE_DEVOLUCION
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
public class DetalleDevolucionPK implements Serializable{
    private String idInsumo;
    private Integer idDevolucion;

    public DetalleDevolucionPK(String idInsumo, Integer idDevolucion) {
        this.idInsumo = idInsumo;
        this.idDevolucion = idDevolucion;
    }

    public DetalleDevolucionPK() {
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.idInsumo);
        hash = 19 * hash + Objects.hashCode(this.idDevolucion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DetalleDevolucionPK other = (DetalleDevolucionPK) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        return Objects.equals(this.idDevolucion, other.idDevolucion);
    }
    
    
}
