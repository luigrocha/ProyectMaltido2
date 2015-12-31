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
import com.espe.distribuidas.model.AsignacionInsumoPK;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.Mantenimiento;
import com.espe.distribuidas.model.MantenimientoPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.AsignacionInsumoServicio;
import com.espe.distribuidas.servicio.InsumoServicio;
import com.espe.distribuidas.servicio.MantenimientoServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.SelectEvent;

/**
 * Clase de bean de insumo Asignado al mantenimiento, define todas las
 * operaciones del CRUD y busqueda.
 *
 * @author R&R S.A.
 *
 */
@ViewScoped
@ManagedBean
public class AsignacionInsumoBean extends BaseBean implements Serializable {

    @EJB
    private InsumoServicio insumoServicio;

    @EJB
    private AsignacionInsumoServicio asignacionInsumoServicio;

    @EJB
    private MantenimientoServicio mantenimientoServicio;

    /**
     * variable temporal para retener el id del mantenimiento en toda la lista.
     */
    /**
     * variable que referencia a un insumo seleccionado.
     */
    private Insumos insumoSelected;

    /**
     * cantidad de un insumo.
     */
    private Insumos cantidad;
    /**
     * variable de referencia al objeto insumo.
     */
    private Insumos insumo;
    /**
     * lista de insumos.
     */
    private List<Insumos> insumos;

    /**
     * variable tipo lista de AsignacionInsumo para setar a una tabla del
     * formulario.
     */
    private List<AsignacionInsumo> asignacionInsumos;

    /**
     * variable tipo AsignacionInsumos.
     */
    private AsignacionInsumo asignacionInsumo;

    /**
     * variable tipo AsignacionInsumo para seleccion.
     */
    private AsignacionInsumo asignacionInsumoSelected;

    /**
     * lista de insumos para guardar.
     */
    private List<AsignacionInsumo> guardarAsignacionInsumo;
    /**
     * variable tipo lista de mantenimiento para setar a una tabla del
     * formulario.
     */
    private List<Mantenimiento> mantenimientos;

    /**
     * variable tipo mantenimiento.
     */
    private Mantenimiento mantenimiento;

    /**
     * variable tipo mantenimiento para seleccion.
     */
    private Mantenimiento mantenimientoSelected;

    /**
     * clase clave primaria
     */
    private AsignacionInsumoPK primaryKey;

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
        this.asignacionInsumos = this.asignacionInsumoServicio.obtenerTodasInsumosAsignados();
        this.insumos = this.insumoServicio.obtenerTodosInsumos();
        this.mantenimientos = this.mantenimientoServicio.obtenerTodasMantenimiento();
        this.primaryKey = new AsignacionInsumoPK();
        this.guardarAsignacionInsumo = new ArrayList<>();
        this.cantidad = new Insumos();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        // super.seleccionar();
        super.nuevo();
        this.mantenimiento = new Mantenimiento();
        this.setTitle("Ingresar Cita de Mantenimiento");
    }

    public void ingresarInsumo() {
        this.asignacionInsumo = new AsignacionInsumo();

        this.primaryKey.setIdEmpleado(this.getMantenimientoSelected().getPrimaryKey().getIdEmpleado());
        this.primaryKey.setIdCita(this.getMantenimientoSelected().getPrimaryKey().getIdCita());
        this.primaryKey.setIdInsumo(this.insumoSelected.getIdInsumo());
        this.asignacionInsumo.setPrimaryKey(primaryKey);
        this.asignacionInsumo.setUnidadMedida(this.insumoSelected.getUnidadMedida());
        this.asignarCantidad(cantidad);
        this.asignacionInsumo.setMantenimientoAsignacionInsumo(this.mantenimientoSelected);
        this.asignacionInsumo.setInsumo(this.insumoSelected);
        this.asignacionInsumo.setFechaAsignacion(new Date());
        this.guardarAsignacionInsumo.add(this.asignacionInsumo);

    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.mantenimiento = new Mantenimiento();
        this.setTitle("Modificar Mantenimiento");
        try {
            BeanUtils.copyProperties(this.mantenimiento, this.mantenimientoSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.asignacionInsumo = new AsignacionInsumo();
        try {
            BeanUtils.copyProperties(this.asignacionInsumo, this.asignacionInsumoSelected);
            this.asignacionInsumoServicio.eliminarInsumosAsignados(this.asignacionInsumo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.asignacionInsumos.remove(this.asignacionInsumo);
            this.setAsignacionInsumoSelected(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
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

    public void onRowSelectMantenimiento(SelectEvent event) {
        // this.diableAceptar = false;
//            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);

//        this.primaryKey.setIdEmpleado(this.mantenimientoSelected.getPrimaryKey().getIdEmpleado());
    }

    public void onRowSelectInsumo(SelectEvent event) {
        this.setDiableAceptar(false);
    }

    public void asignarCantidad(Insumos cantidad) {
        this.asignacionInsumo.setCantidad(cantidad.getCantidad());
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            try {

                // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.asignacionInsumoServicio.ingresarInsumoAsignado(this.guardarAsignacionInsumo);
                listaActualizar();
                // this.citas.add(0, this.cita);
                this.asignacionInsumos = this.asignacionInsumoServicio.obtenerTodasInsumosAsignados();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "El o los registros se registraron corectamente", null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.asignacionInsumoServicio.actulizarInsumoAsignado(this.asignacionInsumo);
                BeanUtils.copyProperties(this.asignacionInsumoSelected, this.asignacionInsumo);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el insumo asignado del de: " + this.asignacionInsumo.getPrimaryKey().getIdEmpleado() + " de la cita: " + this.asignacionInsumo.getPrimaryKey().getIdCita(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setMantenimientoSelected(null);
    }

    public void habilitarSeleccionCliente() {
        super.seleccionar();
    }

    public void listaActualizar() {
        for (int i = 0; i < guardarAsignacionInsumo.size(); i++) {
            Insumos insumoTmp = guardarAsignacionInsumo.get(i).getInsumo();

            insumoTmp.setCantidad(insumoTmp.getCantidad().subtract(guardarAsignacionInsumo.get(i).getCantidad()));
            this.insumoServicio.actualizarInsumo(insumoTmp);

        }

    }

    /**
     * desactiva la tabla de seleccion
     */
    public void cancelarPopUp() {
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

    public MantenimientoServicio getMantenimientoServicio() {
        return mantenimientoServicio;
    }

    public void setMantenimientoServicio(MantenimientoServicio mantenimientoServicio) {
        this.mantenimientoServicio = mantenimientoServicio;
    }

    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

    public List<Insumos> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Insumos> insumos) {
        this.insumos = insumos;
    }

    public List<AsignacionInsumo> getAsignacionInsumos() {
        return asignacionInsumos;
    }

    public void setAsignacionInsumos(List<AsignacionInsumo> asignacionInsumos) {
        this.asignacionInsumos = asignacionInsumos;
    }

    public AsignacionInsumo getAsignacionInsumo() {
        return asignacionInsumo;
    }

    public void setAsignacionInsumo(AsignacionInsumo asignacionInsumo) {
        this.asignacionInsumo = asignacionInsumo;
    }

    public AsignacionInsumo getAsignacionInsumoSelected() {
        return asignacionInsumoSelected;
    }

    public void setAsignacionInsumoSelected(AsignacionInsumo asignacionInsumoSelected) {
        this.asignacionInsumoSelected = asignacionInsumoSelected;
    }

    public List<AsignacionInsumo> getGuardarAsignacionInsumo() {
        return guardarAsignacionInsumo;
    }

    public void setGuardarAsignacionInsumo(List<AsignacionInsumo> guardarAsignacionInsumo) {
        this.guardarAsignacionInsumo = guardarAsignacionInsumo;
    }

    public AsignacionInsumoPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(AsignacionInsumoPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public Mantenimiento getMantenimientoSelected() {
        return mantenimientoSelected;
    }

    public void setMantenimientoSelected(Mantenimiento mantenimientoSelected) {
        this.mantenimientoSelected = mantenimientoSelected;
    }

    public boolean isDiableAceptar() {
        return diableAceptar;
    }

    public void setDiableAceptar(boolean diableAceptar) {
        this.diableAceptar = diableAceptar;
    }

    public Insumos getCantidad() {
        return cantidad;
    }

    public void setCantidad(Insumos cantidad) {
        this.cantidad = cantidad;
    }

}
