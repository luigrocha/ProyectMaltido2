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
 * Clase que representa a la  entidad DETALLE_PEDIDO
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "DETALLE_PEDIDO_001")
@IdClass(DetallePedidoPK.class)
public class DetallePedido implements Serializable {

    @Id
    @Column(name = "ID_PEDIDO", nullable = false)
    private Integer idPedido;

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID_PEDIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;

    @JoinColumn(name = "ID_INSUMO", referencedColumnName = "ID_INSUMO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Insumos insumoPedido;

    @Column(name = "CANTIDAD", nullable = false)
    private BigDecimal cantidad;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    public DetallePedido() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Insumos getInsumoPedido() {
        return insumoPedido;
    }

    public void setInsumoPedido(Insumos insumoPedido) {
        this.insumoPedido = insumoPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
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
        hash = 97 * hash + Objects.hashCode(this.idPedido);
        hash = 97 * hash + Objects.hashCode(this.idInsumo);
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
        final DetallePedido other = (DetallePedido) obj;
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idPedido, other.idPedido)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "idPedido=" + idPedido + ", idInsumo=" + idInsumo + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }

}
