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
 * Clase que representa a la  entidad PEDIDO
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "PEDIDOS_001")
public class Pedido implements Serializable {

    @Id
    @Column(name = "ID_PEDIDO", nullable = false)
    private Integer idPedido;

    @JoinColumn(name = "ID_PROVEEDOR", referencedColumnName = "ID_PROVEEDOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proveedor proveedorPedido;

    @Column(name = "ID_PROVEEDOR", nullable = false)
    private String idProveedor;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @Column(name = "TOTAL_PEDIDO", nullable = false)
    private BigDecimal totalPedido;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    List<DetallePedido> detallePedido;

    public Pedido() {
    }

    public Proveedor getProveedorPedido() {
        return proveedorPedido;
    }

    public void setProveedorPedido(Proveedor proveedorPedido) {
        this.proveedorPedido = proveedorPedido;
    }


    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
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

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idPedido);
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
        final Pedido other = (Pedido) obj;
        return Objects.equals(this.idPedido, other.idPedido);
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", idProveedor=" + idProveedor + ", fecha=" + fecha + ", totalPedido=" + totalPedido + '}';
    }

    public void setTotalPedido(Integer total) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
