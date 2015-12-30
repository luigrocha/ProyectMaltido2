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
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase que representa a la  entidad DETALLE_DEVOLUCION
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "DETALLE_DEVOLUCION_001")
@IdClass(DetalleDevolucionPK.class)
public class DetalleDevolucion implements Serializable {

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Id
    @Column(name = "ID_DEVOLUCION", nullable = false)
    private Integer idDevolucion;

    @Column(name = "CANTIDAD", nullable = false)
    private BigDecimal cantidad;

    @JoinColumn(name = "ID_DEVOLUCION", referencedColumnName = "ID_DEVOLUCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Devoluciones devolucion;

    @JoinColumn(name = "ID_INSUMO", referencedColumnName = "ID_INSUMO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumos devolucionInsumo;

    public DetalleDevolucion() {
    }

    public Devoluciones getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devoluciones devolucion) {
        this.devolucion = devolucion;
    }

    public Insumos getDevolucionInsumo() {
        return devolucionInsumo;
    }

    public void setDevolucionInsumo(Insumos devolucionInsumo) {
        this.devolucionInsumo = devolucionInsumo;
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idInsumo);
        hash = 67 * hash + Objects.hashCode(this.idDevolucion);
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
        final DetalleDevolucion other = (DetalleDevolucion) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        return Objects.equals(this.idDevolucion, other.idDevolucion);
    }

}
