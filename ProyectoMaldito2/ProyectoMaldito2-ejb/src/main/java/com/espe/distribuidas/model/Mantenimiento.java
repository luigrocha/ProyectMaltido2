/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "MANTENIMIENTO")
@IdClass(MantenimientoPK.class)
public class Mantenimiento implements Serializable{
    @Id
    @Column(name = "ID_TECNICO", nullable = false)
    private String idTecnico;
    
    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_INICIO", nullable = false)
    private Date fechaInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_FIN", nullable = false)
    private Date fechaFin;
    
    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    public Mantenimiento() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idTecnico);
        hash = 79 * hash + Objects.hashCode(this.idCita);
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
        if (!Objects.equals(this.idTecnico, other.idTecnico)) {
            return false;
        }
        if (!Objects.equals(this.idCita, other.idCita)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mantenimiento{" + "idTecnico=" + idTecnico + ", idCita=" + idCita + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", precio=" + precio + '}';
    }
    
    

}
