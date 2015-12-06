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
import javax.persistence.Table;

/**
 *
 * @author Andres Vr
 */
@Entity
@Table(name = "INSUMOS")
public class Insumos implements Serializable {

    @Id
    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "PRECIO_COMPRA", nullable = false)
    private BigDecimal precioCompra;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "TIPO_INSUMO", nullable = false)
    private String tipoInsumo;

    public Insumos() {
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idInsumo);
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
        final Insumos other = (Insumos) obj;
        return Objects.equals(this.idInsumo, other.idInsumo);
    }

    @Override
    public String toString() {
        return "Insumos{" + "idInsumo=" + idInsumo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precioCompra=" + precioCompra + ", cantidad=" + cantidad + ", tipoInsumo=" + tipoInsumo + '}';
    }

}
