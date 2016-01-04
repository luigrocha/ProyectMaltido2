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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa a la entidad Liquidacion Asignacion contiene todos los
 * datos asociados a la entidad.
 *
 * @author R&R S.A.
 */
@Entity
@Table(name = "LIQUIDACION_ASIGNACION_001")
public class LiquidacionAsignacion implements Serializable {

    @Id
    @Column(name = "ID_LIQUIDACION", nullable = false)
    private Integer idLiquidacion;

    @Column(name = "ID_INSUMO", nullable = false)
    private String idInsumo;

    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    @Column(name = "ID_EMPLEADO", nullable = false)
    private String idEmpleado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @Column(name = "CANTIDAD", nullable = false)
    private BigDecimal cantidad;

    @Column(name = "UNIDAD_MEDIDA", nullable = false)
    private String unidadMedida;

    @Column(name = "TOTAL_LIQUIDACION", nullable = false)
    private BigDecimal totalLiquidacion;

    @JoinColumns({
        @JoinColumn(name = "ID_CITA", referencedColumnName = "ID_CITA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_EMPLEADO", referencedColumnName = "ID_EMPLEADO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_INSUMO", referencedColumnName = "ID_INSUMO", insertable = false, updatable = false)

    }
    )
    @ManyToOne(optional = false)
    AsignacionInsumo insumoliquidado;

    public LiquidacionAsignacion() {
    }

    public Integer getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(Integer idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public BigDecimal getTotalLiquidacion() {
        return totalLiquidacion;
    }

    public void setTotalLiquidacion(BigDecimal totalLiquidacion) {
        this.totalLiquidacion = totalLiquidacion;
    }

    public AsignacionInsumo getInsumoliquidado() {
        return insumoliquidado;
    }

    public void setInsumoliquidado(AsignacionInsumo insumoliquidado) {
        this.insumoliquidado = insumoliquidado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idLiquidacion);
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
        final LiquidacionAsignacion other = (LiquidacionAsignacion) obj;
        if (!Objects.equals(this.idLiquidacion, other.idLiquidacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LiquidacionAsignacion{" + "idLiquidacion=" + idLiquidacion + ", idInsumo=" + idInsumo + ", idCita=" + idCita + ", idEmpleado=" + idEmpleado + ", fecha=" + fecha + ", cantidad=" + cantidad + ", unidadMedida=" + unidadMedida + ", totalLiquidacion=" + totalLiquidacion + '}';
    }
    

}
