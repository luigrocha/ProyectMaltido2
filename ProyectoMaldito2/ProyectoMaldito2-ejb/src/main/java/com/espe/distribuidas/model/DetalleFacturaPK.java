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
 * Clase que representa a la clave primaria de la  entidad DETALLE_FACTURA
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
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
