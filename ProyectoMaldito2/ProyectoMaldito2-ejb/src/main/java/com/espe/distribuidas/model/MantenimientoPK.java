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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase que representa a la clave primaria de la entidad MANTENIMIENTO contiene
 * todos los datos asociados a la entidad.
 *
 * @author R&R S.A.
 */
@Embeddable
public class MantenimientoPK implements Serializable {

    @Column(name = "ID_EMPLEADO", nullable = false)
    private String idEmpleado;

    @Column(name = "ID_CITA", nullable = false)
    private Integer idCita;

    public MantenimientoPK() {
    }

    public MantenimientoPK(String idEmpleado, Integer idCita) {
        this.idEmpleado = idEmpleado;
        this.idCita = idCita;
    }

    public MantenimientoPK(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public MantenimientoPK(Integer idCita) {
        this.idCita = idCita;
    }
    
    
    
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idEmpleado);
        hash = 67 * hash + Objects.hashCode(this.idCita);
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
        final MantenimientoPK other = (MantenimientoPK) obj;
        if (!Objects.equals(this.idEmpleado, other.idEmpleado)) {
            return false;
        }
        return Objects.equals(this.idCita, other.idCita);
    }

}
