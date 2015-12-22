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
 * Clase que representa a la clave primaria de la entidad ASIGNACION_INSUMO
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
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
