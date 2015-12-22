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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa a la  entidad DEVOLUCIONES
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "DEVOLUCIONES_001")
public class Devoluciones implements Serializable {

    @Id
    @Column(name = "ID_DEVOLUCION", nullable = false)
    private Integer idDevoluciones;

    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID_PROVEEDOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proveedor devolucionProveedor;

    @Column(name = "ID_PROVEEDOR", nullable = false)
    private String idProveedor;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "devolucion")
    List<DetalleDevolucion> detalleDevolucion;

    public Devoluciones() {
    }

    public Proveedor getDevolucionProveedor() {
        return devolucionProveedor;
    }

    public void setDevolucionProveedor(Proveedor devolucionProveedor) {
        this.devolucionProveedor = devolucionProveedor;
    }

    public List<DetalleDevolucion> getDetalleDevolucion() {
        return detalleDevolucion;
    }

    public void setDetalleDevolucion(List<DetalleDevolucion> detalleDevolucion) {
        this.detalleDevolucion = detalleDevolucion;
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
