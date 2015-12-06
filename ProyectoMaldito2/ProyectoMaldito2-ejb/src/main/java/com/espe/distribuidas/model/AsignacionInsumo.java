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
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Andres Vr
 */
@Entity
@Table(name = "ASIGNACION_INSUMO")
@IdClass(AsignacionInsumoPK.class)

public class AsignacionInsumo implements Serializable {

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Id
    @Column(name = "ID_TECNICO", nullable = false)
    private String idTecnico;

    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ASIGNACION", nullable = false)
    private Date fechaAsignacion;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public AsignacionInsumo() {
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
