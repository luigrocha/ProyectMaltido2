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

import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import com.espe.distribuidas.model.exceptions.ValidacionException;
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
 * Clase EmpleadoBean que maneja listEmpleados.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class EmpleadoBean extends BaseBean implements Serializable {

    @EJB
    private EmpleadoServicio empleadoServicio;

    /**
     * variable tipo lista de empleados para setar a una tabla del formulario.
     */
    private List<Empleado> empleados;

    /**
     * variable tipo empleado para las operaciones del CRUD.
     */
    private Empleado empleado;

    /**
     * variable tipo epleado.
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
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        this.empleados = this.empleadoServicio.obtenerTodosEmpleados();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.nuevo();
        this.empleado = new Empleado();
        this.setTitle("Ingresar Empleado");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.empleado = new Empleado();
        this.setTitle("Modificar Empleado");
        try {
            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.empleado = new Empleado();
        try {
            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
            this.empleadoServicio.eliminarEmpleado(this.empleado.getIdEmpleado());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.empleados.remove(this.empleado);
            this.setEmpleadoSelected(null);
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
                this.empleadoServicio.ingresarEmpleado(this.empleado);
                this.empleados.add(0, this.empleado);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el cliente: " + this.empleado.getNombre() + " " + this.empleado.getApellido(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.empleadoServicio.actualizarEmpleado(this.empleado);
                BeanUtils.copyProperties(this.empleadoSelected, this.empleado);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el cliente: " + this.empleado.getNombre() + " " + this.empleado.getApellido(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setEmpleadoSelected(null);
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptarNuevo() {
        super.nuevo();
        this.empleado = new Empleado();
    }

     /**
     *permite ingresar un empleado en la BDD.
     */
    public void registrarEmpleado() {
        try {
            empleadoServicio.ingresarEmpleado(empleado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error en el Registro"));
        }
    }

    /**
     * metodo get de titulo.
     * @return retirna un string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de titulo.
     * @param title ingresa un string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * metodo get de disabled.
     * @return retirna un boolean.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de disabled.
     * @param disabled ingresa un boolean.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * metodo get de la lista de empleados.
     * @return retorna una lista de empleados.
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * metodo set de empleados.
     * @param empleados ingresa una lista de empleados.
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * metodo get de empleado.
     * @return retorna un tipo empleado.
     */
    public Empleado getEmpleado() {
        return empleado;
    }
     /**
      * metodo set de empleado.
      * @param empleado recibe un tipo empleado.
      */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    /**
     * metodo get del empleado seleccionado.
     * @return retorna un objeto tipo empleado.
     */
    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    /**
     * metodo set del empleado seleccionado.
     * @param empleadoSelected retorna un tipo empleado.
     */
    public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }

}
