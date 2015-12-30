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
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa a la entidad ASIGNACION_INSUMO
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "ASIGNACION_INSUMO_001")

public class AsignacionInsumo implements Serializable {
    @EmbeddedId
    AsignacionInsumoPK primaryKey;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ASIGNACION", nullable = false)
    private Date fechaAsignacion;

    @Column(name = "CANTIDAD", nullable = false)
    private BigDecimal cantidad;
    
    @Column(name="UNIDAD_MEDIDA",nullable = false)
    private String unidadMedida;

    @JoinColumns({
        @JoinColumn(name = "ID_CITA", referencedColumnName = "ID_CITA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false)
    })
    @ManyToOne(optional = false)
    private Mantenimiento mantenimientoAsignacionInsumo;

    @JoinColumn(name = "ID_INSUMO", referencedColumnName = "ID_INSUMO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumos insumo;

    public AsignacionInsumo() {
    }

    public Mantenimiento getMantenimientoAsignacionInsumo() {
        return mantenimientoAsignacionInsumo;
    }

    public void setMantenimientoAsignacionInsumo(Mantenimiento mantenimientoAsignacionInsumo) {
        this.mantenimientoAsignacionInsumo = mantenimientoAsignacionInsumo;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

    public AsignacionInsumoPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AsignacionInsumoPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.primaryKey);
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
        final AsignacionInsumo other = (AsignacionInsumo) obj;
        return Objects.equals(this.primaryKey, other.primaryKey);
    }

    @Override
    public String toString() {
        return "AsignacionInsumo{" + "primaryKey=" + primaryKey + ", fechaAsignacion=" + fechaAsignacion + ", cantidad=" + cantidad + ", unidadMedida=" + unidadMedida + ", mantenimientoAsignacionInsumo=" + mantenimientoAsignacionInsumo + ", insumo=" + insumo + '}';
    }

    
 

}
