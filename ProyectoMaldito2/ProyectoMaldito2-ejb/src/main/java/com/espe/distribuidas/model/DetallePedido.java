/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.model;

import java.io.Serializable;
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
@Table(name = "DETALLE_PEDIDO")
@IdClass(DetallePedidoPK.class)
public class DetallePedido implements Serializable {

    @Id
    @Column(name = "ID_PEDIDO", nullable = false)
    private Integer idPedido;

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    public DetallePedido() {
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
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
