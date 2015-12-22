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
import java.util.Objects;
import javax.persistence.Column;
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
@IdClass(AsignacionInsumoPK.class)

public class AsignacionInsumo implements Serializable {

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Id
    @Column(name = "ID_EMPLEADO", nullable = false)
    private String idTecnico;

    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ASIGNACION", nullable = false)
    private Date fechaAsignacion;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

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

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idInsumo);
        hash = 17 * hash + Objects.hashCode(this.idTecnico);
        hash = 17 * hash + Objects.hashCode(this.idCita);
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
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idTecnico, other.idTecnico)) {
            return false;
        }
        return Objects.equals(this.idCita, other.idCita);
    }

    @Override
    public String toString() {
        return "AsignacionInsumo{" + "idInsumo=" + idInsumo + ", idTecnico=" + idTecnico + ", idCita=" + idCita + ", fechaAsignacion=" + fechaAsignacion + ", cantidad=" + cantidad + '}';
    }

}
