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
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que representa a la  entidad PROVEEDOR
 * contiene todos los datos asociados a la entidad.
 * @author R&R S.A.
 */
@Entity
@Table(name = "PROVEEDOR_001")
public class Proveedor implements Serializable {

    @Id
    @Column(name = "ID_PROVEEDOR", nullable = false)
    private String idProveedor;

    @Column(name = "NOMBRE_EMPRESA", nullable = false)
    private String nombreEmpresa;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "TELEFONO", nullable = false)
    private String telefono;

    @Column(name = "CORREO_ELECTRONICO", nullable = false)
    private String correoElectronico;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "devolucionProveedor")
    List<Devoluciones> devoluciondesProveedor;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proveedorPedido")
    List<Pedido> pedidoProveedor;
    

    public Proveedor() {
    }

    public List<Devoluciones> getDevoluciondesProveedor() {
        return devoluciondesProveedor;
    }

    public void setDevoluciondesProveedor(List<Devoluciones> devoluciondesProveedor) {
        this.devoluciondesProveedor = devoluciondesProveedor;
    }

    public List<Pedido> getPedidoProveedor() {
        return pedidoProveedor;
    }

    public void setPedidoProveedor(List<Pedido> pedidoProveedor) {
        this.pedidoProveedor = pedidoProveedor;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombre_empresa) {
        this.nombreEmpresa = nombre_empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idProveedor);
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
        final Proveedor other = (Proveedor) obj;
        return Objects.equals(this.idProveedor, other.idProveedor);
    }

    @Override
    public String toString() {
        return "Proveedor{" + "idProveedor=" + idProveedor + ", nombre_empresa=" + nombreEmpresa + ", direccion=" + direccion + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + ", estado=" + estado + '}';
    }

}
