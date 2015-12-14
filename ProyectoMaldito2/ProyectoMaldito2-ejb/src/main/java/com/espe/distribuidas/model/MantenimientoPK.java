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
public class MantenimientoPK implements Serializable{
    private String idEmpleado;
    
    private Integer idCita;

    public MantenimientoPK() {
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }



    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idEmpleado);
        hash = 67 * hash + Objects.hashCode(this.idCita);
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
        final MantenimientoPK other = (MantenimientoPK) obj;
        if (!Objects.equals(this.idEmpleado, other.idEmpleado)) {
            return false;
        }
        return Objects.equals(this.idCita, other.idCita);
    }
    
    
}
