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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
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

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        super.nuevo();
        this.empleados = this.empleadoServicio.buscasPorTecnico();

    }

    @Override
    public void seleccionar() {
        super.quitarNuevo();
        try {
            super.seleccionar();
            this.empleado = new Empleado();
            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
            this.mantenimientos = this.mantenimientoServicio.obtenerMantenimientoPorEmpleado(this.empleado);
            this.setearShedule(this.mantenimientos);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(CalendarioOcupacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setearShedule(List<Mantenimiento> listMantenimiento) {
        eventModel = new DefaultScheduleModel();
        for (int i = 0; i < listMantenimiento.size(); i++) {
            eventModel.addEvent(new DefaultScheduleEvent(i + "-" + listMantenimiento.get(i).getCitaMantenimiento().getDescripcion(), this.getDate(listMantenimiento.get(i).getFechaInicio()), this.getDate(listMantenimiento.get(i).getFechaFin())));

        }
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setEmpleadoSelected(null);
        this.empleados = empleadoServicio.buscasPorTecnico();

    }

    public void regresar() {
        super.reset();
        super.nuevo();
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

    public Date getDate(Date date) {
        Date aux = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = formato.format(date);
            aux = formato.parse(fecha);

        } catch (ParseException ex) {
            Logger.getLogger(CalendarioOcupacionesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        String[] index = event.getTitle().split("-");
        this.mantenimiento = this.mantenimientos.get(Integer.valueOf(index[0]));
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

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

}
