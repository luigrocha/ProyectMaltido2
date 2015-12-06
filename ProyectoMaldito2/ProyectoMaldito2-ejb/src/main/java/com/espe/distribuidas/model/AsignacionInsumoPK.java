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
public class AsignacionInsumoPK implements Serializable{
    private String idInsumo;
    
    private String idTecnico;
   
    private Integer idCita;

    public AsignacionInsumoPK() {
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idInsumo);
        hash = 67 * hash + Objects.hashCode(this.idTecnico);
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
        final AsignacionInsumoPK other = (AsignacionInsumoPK) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idTecnico, other.idTecnico)) {
            return false;
        }
        return Objects.equals(this.idCita, other.idCita);
    }
    
    
}
