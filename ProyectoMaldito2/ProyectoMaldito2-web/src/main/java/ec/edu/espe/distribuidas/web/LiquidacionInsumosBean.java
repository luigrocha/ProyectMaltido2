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

import com.espe.distribuidas.model.AsignacionInsumo;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.LiquidacionAsignacion;
import com.espe.distribuidas.servicio.AsignacionInsumoServicio;
import com.espe.distribuidas.servicio.InsumoServicio;
import com.espe.distribuidas.servicio.LiquidacionAsignacionServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
import org.primefaces.event.SelectEvent;

/**
 * Clase de bean de cita mantenimiento, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@ViewScoped
@ManagedBean

public class LiquidacionInsumosBean extends BaseBean implements Serializable {

    @EJB
    private LiquidacionAsignacionServicio liquidacionServicio;

    @EJB
    private AsignacionInsumoServicio asignacionInsumoServicio;
    
    @EJB
    private InsumoServicio insumoServicio;
    /**
     * variable que referencia un cliente seleccionado
     */
    private AsignacionInsumo asignacionInsumoSelected;

    /**
     * variable de referencia al objeto cliente
     */
    private AsignacionInsumo asignacionInsumo;
    /**
     * lista de clientes para el popup
     */
    private List<AsignacionInsumo> asignacionInsumos;
    /**
     * variable tipo lista de citas de mantenimiento para setar a una tabla del
     * formulario.
     */
    private List<LiquidacionAsignacion> liquidacionAsignaciones;

    /**
     * variable tipo cita de mantenimiento para las operaciones del CRUD.
     */
    private LiquidacionAsignacion liquidacionAsignacion;

    /**
     * variable tipo cita.
     */
    private LiquidacionAsignacion liquidacionAsignacionSelected;

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
        this.liquidacionAsignaciones = liquidacionServicio.obtenerTodos();
        this.asignacionInsumos=asignacionInsumoServicio.obtenerTodasInsumosAsignados();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {

        super.nuevo();
        this.liquidacionAsignacion = new LiquidacionAsignacion();
        this.setTitle("Ingresar Liquidacion de Asignacioón");
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setAsignacionInsumoSelected(null);

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

    public void onRowSelectPopUp(SelectEvent event) {
        this.diableAceptar = false;
    }

    public void aceptarPopUp() {
        super.seleccionar();
        this.asignacionInsumo = new AsignacionInsumo();
        try {

            BeanUtils.copyProperties(this.asignacionInsumo, this.asignacionInsumoSelected);
            this.liquidacionAsignacion.setIdCita(this.asignacionInsumo.getPrimaryKey().getIdCita());
            this.liquidacionAsignacion.setIdEmpleado(this.asignacionInsumo.getPrimaryKey().getIdEmpleado());
            this.liquidacionAsignacion.setIdInsumo(this.asignacionInsumo.getPrimaryKey().getIdInsumo());
            this.liquidacionAsignacion.setUnidadMedida(this.asignacionInsumo.getUnidadMedida());
            this.liquidacionAsignacion.setFecha(new Date());
            this.liquidacionAsignacion.setTotalLiquidacion(this.asignacionInsumo.getInsumo().getPrecioCompra());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(CitaMantenimientoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setAsignacionInsumoSelected(null);
        this.setLiquidacionAsignacionSelected(null);
        super.quitarNuevo();
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
            this.liquidacionAsignacion.setTotalLiquidacion(this.liquidacionAsignacion.getTotalLiquidacion().multiply(this.liquidacionAsignacion.getCantidad()));
            this.liquidacionServicio.ingresarInsumo(this.liquidacionAsignacion);
            // this.citas.add(0, this.cita);
            
            actualizar(this.asignacionInsumoSelected, this.liquidacionAsignacion);
            this.liquidacionAsignaciones = this.liquidacionServicio.obtenerTodos();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Liquidación completada Satisfactoriamente", null));
        } catch (Exception e) {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }

        this.reset();
        this.setDiableAceptar(true);
        this.setAsignacionInsumoSelected(null);
    }

    public void habilitarSeleccionCliente() {
        super.seleccionar();
    }

    public void actualizar(AsignacionInsumo asignacion,LiquidacionAsignacion liquidacion){
    Integer res;
    
    res=liquidacion.getCantidad().compareTo(asignacion.getCantidad());
        if(res==-1)
    {
    Insumos insumotmp=asignacion.getInsumo();
    insumotmp.setCantidad(insumotmp.getCantidad().add(asignacion.getCantidad().subtract(liquidacion.getCantidad())));
    this.insumoServicio.actualizarInsumo(insumotmp);

    
    }
        
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

    public AsignacionInsumo getAsignacionInsumoSelected() {
        return asignacionInsumoSelected;
    }

    public void setAsignacionInsumoSelected(AsignacionInsumo asignacionInsumoSelected) {
        this.asignacionInsumoSelected = asignacionInsumoSelected;
    }

    public AsignacionInsumo getAsignacionInsumo() {
        return asignacionInsumo;
    }

    public void setAsignacionInsumo(AsignacionInsumo asignacionInsumo) {
        this.asignacionInsumo = asignacionInsumo;
    }

    public List<AsignacionInsumo> getAsignacionInsumos() {
        return asignacionInsumos;
    }

    public void setAsignacionInsumos(List<AsignacionInsumo> asignacionInsumos) {
        this.asignacionInsumos = asignacionInsumos;
    }

    public LiquidacionAsignacion getLiquidacionAsignacion() {
        return liquidacionAsignacion;
    }

    public void setLiquidacionAsignacion(LiquidacionAsignacion liquidacionAsignacion) {
        this.liquidacionAsignacion = liquidacionAsignacion;
    }

    public boolean isDiableAceptar() {
        return diableAceptar;
    }

    public void setDiableAceptar(boolean diableAceptar) {
        this.diableAceptar = diableAceptar;
    }

    public List<LiquidacionAsignacion> getLiquidacionAsignaciones() {
        return liquidacionAsignaciones;
    }

    public void setLiquidacionAsignaciones(List<LiquidacionAsignacion> liquidacionAsignaciones) {
        this.liquidacionAsignaciones = liquidacionAsignaciones;
    }

    public LiquidacionAsignacion getLiquidacionAsignacionSelected() {
        return liquidacionAsignacionSelected;
    }

    public void setLiquidacionAsignacionSelected(LiquidacionAsignacion liquidacionAsignacionSelected) {
        this.liquidacionAsignacionSelected = liquidacionAsignacionSelected;
    }

}
