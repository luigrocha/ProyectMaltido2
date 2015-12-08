/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Andres Vr
 */
@Entity
@Table(name = "CITA_MANTENIMIENTO")
public class CitaMantenimiento implements Serializable {

    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID_CLIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente clienteCita;

    @Column(name = "ID_CLIENTE", nullable = false)
    private String idCliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @Column(name = "TIPO_MANTENIMIENTO", nullable = false)
    private String tipoMantenimiento;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "citaMantenimiento")
    List<Mantenimiento> mantimientoCita;

    public CitaMantenimiento() {
    }

    public Cliente getClienteCita() {
        return clienteCita;
    }

    public void setClienteCita(Cliente clienteCita) {
        this.clienteCita = clienteCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.idCita);
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
        final CitaMantenimiento other = (CitaMantenimiento) obj;
        return Objects.equals(this.idCita, other.idCita);
    }

    @Override
    public String toString() {
        return "CitaMantenimiento{" + "idCita=" + idCita + ", idCliente=" + idCliente + ", fecha=" + fecha + ", tipoMantenimiento=" + tipoMantenimiento + ", descripcion=" + descripcion + ", estado=" + estado + '}';
    }

}
