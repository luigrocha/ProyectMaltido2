/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Andres Vr
 */
public class DetalleDevolcuionPK implements Serializable{
    private String idInsumo;
    private Integer idDevolucion;

    public DetalleDevolcuionPK(String idInsumo, Integer idDevolucion) {
        this.idInsumo = idInsumo;
        this.idDevolucion = idDevolucion;
    }

    public DetalleDevolcuionPK() {
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
        final DetalleDevolcuionPK other = (DetalleDevolcuionPK) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        return Objects.equals(this.idDevolucion, other.idDevolucion);
    }
    
    
}
