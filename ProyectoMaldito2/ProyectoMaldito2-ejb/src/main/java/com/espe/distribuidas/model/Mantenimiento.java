/*
 * R&R S.A.
 * Sistema: Spotlights&Wires.
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
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa a la  entidad MANTENIMIENTO
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "MANTENIMIENTO_001")
public class Mantenimiento implements Serializable {

    @EmbeddedId
    protected MantenimientoPK primaryKey;
    
    @JoinColumn(name = "ID_CITA", referencedColumnName = "ID_CITA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CitaMantenimiento citaMantenimiento;

    @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleadoMantenimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_FIN", nullable = false)
    private Date fechaFin;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "mantenimientoAsignacionInsumo")
    private List<AsignacionInsumo> insumosAsignados;

    public Mantenimiento() {
    }

    public List<AsignacionInsumo> getInsumosAsignados() {
        return insumosAsignados;
    }

    public void setInsumosAsignados(List<AsignacionInsumo> insumosAsignados) {
        this.insumosAsignados = insumosAsignados;
    }

    public Empleado getEmpleadoMantenimiento() {
        return empleadoMantenimiento;
    }

    public void setEmpleadoMantenimiento(Empleado empleadoMantenimiento) {
        this.empleadoMantenimiento = empleadoMantenimiento;
    }

    public CitaMantenimiento getCitaMantenimiento() {
        return citaMantenimiento;
    }

    public void setCitaMantenimiento(CitaMantenimiento citaMantenimiento) {
        this.citaMantenimiento = citaMantenimiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public MantenimientoPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(MantenimientoPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.primaryKey);
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
        final Mantenimiento other = (Mantenimiento) obj;
        return Objects.equals(this.primaryKey, other.primaryKey);
    }

    @Override
    public String toString() {
        return "Mantenimiento{" + "primaryKey=" + primaryKey + ", citaMantenimiento=" + citaMantenimiento + ", empleadoMantenimiento=" + empleadoMantenimiento + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", precio=" + precio + ", insumosAsignados=" + insumosAsignados + '}';
    }

    

}
