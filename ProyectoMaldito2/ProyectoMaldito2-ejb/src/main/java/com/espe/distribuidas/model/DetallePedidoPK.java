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
public class DetallePedidoPK implements Serializable{
    private Integer idPedido;
    
    private String idInsumo;

    public DetallePedidoPK() {
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idPedido);
        hash = 43 * hash + Objects.hashCode(this.idInsumo);
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
        final DetallePedidoPK other = (DetallePedidoPK) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        return Objects.equals(this.idPedido, other.idPedido);
    }
    
    
}
