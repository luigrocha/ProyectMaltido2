/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.CitaMantenimiento;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.model.Mantenimiento;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.CitaMantenimientoServicio;
import com.espe.distribuidas.servicio.ClienteServicio;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import com.espe.distribuidas.servicio.MantenimientoServicio;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 * Clase de bean de insumo Asignado al mantenimiento, define todas las
 * operaciones del CRUD y busqueda.
 *
 * @author R&R S.A.
 *
 */
@ViewScoped
@ManagedBean
public class CalendarioOcupacionesBean extends BaseBean implements Serializable {

    @EJB
    private MantenimientoServicio mantenimientoServicio;

    @EJB
    private EmpleadoServicio empleadoServicio;
    /**
     * variable que referencia un cliente seleccionado
     */
    private Mantenimiento mantenimientoSelected;

    /**
     * variable de referencia al objeto cliente
     */
    private Mantenimiento mantenimiento;
    /**
     * lista de clientes para el popup
     */
    private List<Mantenimiento> mantenimientos;
    /**
     * variable tipo lista de citas de mantenimiento para setar a una tabla del
     * formulario.
     */
    private List<Empleado> empleados;

    /**
     * variable tipo cita de mantenimiento para las operaciones del CRUD.
     */
    private Empleado empleado;

    /**
     * variable tipo cita.
     */
    private Empleado empleadoSelected;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "";

    /**
     * variable que controla el estado del boton aceptar de la tabla de
     * seleccion de cliente.
     */
    private boolean diableAceptar = true;

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        this.empleados = this.empleadoServicio.buscasPorTecnico();
        
    }

  

    @Override
    public void seleccionar()
    {
        try {
            super.seleccionar();
            this.empleado=new Empleado();
            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
            this.mantenimientos=this.mantenimientoServicio.obtenerMantenimientoPorEmpleado(this.empleado);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(CalendarioOcupacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
/*        this.cita = new CitaMantenimiento();
        this.setTitle("Modificar Cita de Mantenimiento");
        try {
            BeanUtils.copyProperties(this.cita, this.citaSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }*/
    }


    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
       this.setEmpleadoSelected(null);
      
    }

    /**
     * metodo que recibe el evento de seleccion de una fila de la tabla de
     * clientes.
     *
     * @param event evento de tipo seleccion activado al seleccionar un registro
     * de una tabla.
     */
    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

    public void onRowSelectMantenimiento(SelectEvent event) {
        
    }

  

    public void habilitarSeleccionCliente() {
        super.seleccionar();
    }

  
    
    /**
     * metodo get de titulo.
     *
     * @return retirna un string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de titulo.
     *
     * @param title ingresa un string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * metodo get de disabled.
     *
     * @return retirna un boolean.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de disabled.
     *
     * @param disabled ingresa un boolean.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Mantenimiento getMantenimientoSelected() {
        return mantenimientoSelected;
    }

    public void setMantenimientoSelected(Mantenimiento mantenimientoSelected) {
        this.mantenimientoSelected = mantenimientoSelected;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

  
    public boolean isDiableAceptar() {
        return diableAceptar;
    }

    public void setDiableAceptar(boolean diableAceptar) {
        this.diableAceptar = diableAceptar;
    }

}
