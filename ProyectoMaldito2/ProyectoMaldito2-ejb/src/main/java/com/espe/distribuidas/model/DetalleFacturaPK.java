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
public class DetalleFacturaPK implements Serializable{
    private Integer idFactura;
    private String idTecnico;
    private Integer idCita;

    public DetalleFacturaPK() {
    }

    public Integer getIdFtactura() {
        return idFactura;
    }

    public void setIdFtactura(Integer idFtactura) {
        this.idFactura = idFtactura;
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
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.idFactura);
        hash = 47 * hash + Objects.hashCode(this.idTecnico);
        hash = 47 * hash + Objects.hashCode(this.idCita);
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
        final DetalleFacturaPK other = (DetalleFacturaPK) obj;
        if (!Objects.equals(this.idTecnico, other.idTecnico)) {
            return false;
        }
        if (!Objects.equals(this.idFactura, other.idFactura)) {
            return false;
        } else {
        }
        return Objects.equals(this.idCita, other.idCita);
    }
    
    
}
