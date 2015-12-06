/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.model;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author Andres Vr
 */
@Entity
@Table(name = "DETALLE_FACTURA")
@IdClass(DetalleFacturaPK.class)
public class DetalleFactura {

    @Id
    @Column(name = "ID_FACTURA", nullable = false)
    private Integer idFactura;

    @Id
    @Column(name = "ID_TECNICO", nullable = false)
    private String idTecnico;

    @Id
    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "VALOR_UNITARIO", nullable = false)
    private BigDecimal valorUnitario;

    public DetalleFactura() {
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
