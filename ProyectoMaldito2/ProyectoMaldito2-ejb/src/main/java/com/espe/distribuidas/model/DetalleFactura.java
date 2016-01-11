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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase que representa a la  entidad DETALLE_FACTURA
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "DETALLE_FACTURA_001")
@IdClass(DetalleFacturaPK.class)
public class DetalleFactura implements Serializable{

    @Id
    @Column(name = "ID_FACTURA", nullable = false)
    private Integer idFactura;

    @Id
    @Column(name = "ID_EMPLEADO", nullable = false)
    private String idTecnico;

    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @JoinColumn(name = "ID_FACTURA", referencedColumnName = "ID_FACTURA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Factura factura;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "VALOR_UNITARIO", nullable = false)
    private BigDecimal valorUnitario;

    @JoinColumns({
        @JoinColumn(name = "ID_CITA", referencedColumnName = "ID_CITA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false)
    })
    @ManyToOne(optional = false)
    private Mantenimiento mantenimientoDetalleFactura;

    public DetalleFactura() {
    }

    public Mantenimiento getMantenimientoDetalleFactura() {
        return mantenimientoDetalleFactura;
    }

    public void setMantenimientoDetalleFactura(Mantenimiento mantenimientoDetalleFactura) {
        this.mantenimientoDetalleFactura = mantenimientoDetalleFactura;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Integer getIdFtactura() {
        return idFactura;
    }

    public void setIdFtactura(Integer idFtactura) {
        this.idFactura = idFtactura;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idFactura);
        hash = 29 * hash + Objects.hashCode(this.idTecnico);
        hash = 29 * hash + Objects.hashCode(this.idCita);
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
        final DetalleFactura other = (DetalleFactura) obj;
        if (!Objects.equals(this.idTecnico, other.idTecnico)) {
            return false;
        }
        if (!Objects.equals(this.idFactura, other.idFactura)) {
            return false;
        }
        return Objects.equals(this.idCita, other.idCita);
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "idFtactura=" + idFactura + ", idTecnico=" + idTecnico + ", idCita=" + idCita + ", cantidad=" + cantidad + ", valorUnitario=" + valorUnitario + '}';
    }

}
