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
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.model.Mantenimiento;
import com.espe.distribuidas.model.MantenimientoPK;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.CitaMantenimientoServicio;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import com.espe.distribuidas.servicio.MantenimientoServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
 * Clase de bean de mantenimiento, define todas las operaciones del CRUD y
 * busqueda.
 *
 * @author R&R S.A.
 *
 */
@ViewScoped
@ManagedBean
public class MantenimientosBean extends BaseBean implements Serializable {

    @EJB
    private CitaMantenimientoServicio citaServicio;

    @EJB
    private EmpleadoServicio empleadoServicio;
    
    @EJB
    private MantenimientoServicio mantenimientoServicio;
   
        
    /**
     * variable que referencia a un empleado seleccionado.
     */
    private Empleado empleadoSelected;

    /**
     * variable de referencia al objeto empleado.
     */
    private Empleado empleado;
    /**
     * lista de empleados.
     */
    private List<Empleado> empleados;
    
    /**
     * variable tipo lista de citas de mantenimiento para setar a una tabla del
     * formulario.
     */
    private List<CitaMantenimiento> citas;

    /**
     * variable tipo cita de mantenimiento.
     */
    private CitaMantenimiento cita;

    /**
     * variable tipo cita para seleccion.
     */
    private CitaMantenimiento citaSelected;

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
    private MantenimientoPK primaryKey;
    
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
        this.citas = this.citaServicio.obtenerTodasCitas();
        this.empleados = this.empleadoServicio.buscasPorTecnico();
        this.mantenimientos=this.mantenimientoServicio.obtenerTodosMantenimiento();
        this.primaryKey=new MantenimientoPK();
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
        this.cita = new CitaMantenimiento();
        this.mantenimiento=new Mantenimiento();
        try {
            
            BeanUtils.copyProperties(this.mantenimiento, this.mantenimientoSelected);
            this.mantenimientoServicio.eliminarMantenimiento(this.mantenimiento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.setCitaSelected(null);
            this.mantenimientos=mantenimientoServicio.obtenerTodosMantenimiento();
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
        this.setCitaSelected(null);

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

    public void onRowSelectEmpleado(SelectEvent event) {
            // this.diableAceptar = false;
//            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
            this.primaryKey.setIdEmpleado(this.empleadoSelected.getIdEmpleado());
            this.mantenimiento.setPrimaryKey(primaryKey);
            this.mantenimiento.setEmpleadoMantenimiento(this.empleadoSelected);
    }
    public void onRowSelectCita(SelectEvent event) {
      
            // this.diableAceptar = false;
            //BeanUtils.copyProperties(this.cita, this.citaSelected);
            this.primaryKey.setIdCita(this.citaSelected.getIdCita());
            this.mantenimiento.setPrimaryKey(primaryKey);
            this.mantenimiento.setCitaMantenimiento(this.citaSelected);
         
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
                this.mantenimientoServicio.ingresarMantenimiento(this.mantenimiento);
                // this.citas.add(0, this.cita);
                this.mantenimientos.clear();
                this.mantenimientos = this.mantenimientoServicio.obtenerTodosMantenimiento();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el mantenimiento: " + this.mantenimiento.getPrimaryKey().getIdEmpleado()+" "+this.mantenimiento.getPrimaryKey().getIdCita(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.mantenimientoServicio.actulizarMantenimiento(this.mantenimiento);
                BeanUtils.copyProperties(this.mantenimientoSelected, this.mantenimiento);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el mantenimiento del empleado: " + this.mantenimiento.getPrimaryKey().getIdEmpleado()+ " de la cita: " + this.mantenimiento.getPrimaryKey().getIdCita(), null));
                this.mantenimientos=this.mantenimientoServicio.obtenerTodosMantenimiento();
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setCitaSelected(null);
    }

    public void habilitarSeleccionCliente() {
        super.seleccionar();
    }

    
    /**
     * setea el id de la tabla en el parametro del formulario.
     */
    /*
    public void aceptarPopUp() {
        super.nuevo();
        this.cliente = new Cliente();
        try {

            BeanUtils.copyProperties(this.cliente, this.clienteSelected);
            this.cita.setIdCliente(this.cliente.getIdCliente());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(CitaMantenimientoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setCitaSelected(null);
        this.setCliente(null);
        super.quitarSeleccion();

    }
    */
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

    public List<CitaMantenimiento> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaMantenimiento> citas) {
        this.citas = citas;
    }

    public CitaMantenimiento getCita() {
        return cita;
    }

    public void setCita(CitaMantenimiento cita) {
        this.cita = cita;
    }

    public CitaMantenimiento getCitaSelected() {
        return citaSelected;
    }

    public void setCitaSelected(CitaMantenimiento citaSelected) {
        this.citaSelected = citaSelected;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
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

}

