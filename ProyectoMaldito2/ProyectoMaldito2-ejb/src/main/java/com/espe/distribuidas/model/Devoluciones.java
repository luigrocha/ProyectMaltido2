/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Andres Vr
 */
@Entity
@Table(name = "DEVOLUCIONES")
public class Devoluciones implements Serializable {

    @Id
    @Column(name = "ID_DEVOLUCION", nullable = false)
    private Integer idDevoluciones;

    @Column(name = "ID_PROVEEDOR", nullable = false)
    private String idProveedor;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    public Devoluciones() {
    }

    public Integer getIdDevoluciones() {
        return idDevoluciones;
    }

    public void setIdDevoluciones(Integer idDevoluciones) {
        this.idDevoluciones = idDevoluciones;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.idDevoluciones);
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
        final Devoluciones other = (Devoluciones) obj;
        return Objects.equals(this.idDevoluciones, other.idDevoluciones);
    }

    @Override
    public String toString() {
        return "Devoluciones{" + "idDevoluciones=" + idDevoluciones + ", idProveedor=" + idProveedor + ", fecha=" + fecha + '}';
    }

}
