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
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa a la  entidad INSUMOS
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "INSUMOS_001")
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
    private BigDecimal cantidad;

    @Column(name = "TIPO_INSUMO", nullable = false)
    private String tipoInsumo;
    
    @Column(name = "UNIDAD_MEDIDA", nullable = false)
    private String unidadMedida;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "insumo")
    List<AsignacionInsumo> insumoAsigndo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "devolucionInsumo")
    List<DetalleDevolucion> detalleDevolucionInsumo;    

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "insumoPedido")
    List<DetallePedido> detallePedido;    

    public Insumos() {
    }

    public Insumos(String idInsumo, String nombre, String descripcion, BigDecimal precioCompra, BigDecimal cantidad, String tipoInsumo, String unidadMedida) {
        this.idInsumo = idInsumo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCompra = precioCompra;
        this.cantidad = cantidad;
        this.tipoInsumo = tipoInsumo;
        this.unidadMedida = unidadMedida;
    }
    

    public Insumos(String idInsumo, BigDecimal cantidad) {
        this.idInsumo = idInsumo;
        this.cantidad = cantidad;
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public List<AsignacionInsumo> getInsumoAsigndo() {
        return insumoAsigndo;
    }

    public void setInsumoAsigndo(List<AsignacionInsumo> insumoAsigndo) {
        this.insumoAsigndo = insumoAsigndo;
    }

    public List<DetalleDevolucion> getDetalleDevolucionInsumo() {
        return detalleDevolucionInsumo;
    }

    public void setDetalleDevolucionInsumo(List<DetalleDevolucion> detalleDevolucionInsumo) {
        this.detalleDevolucionInsumo = detalleDevolucionInsumo;
    }

    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
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
