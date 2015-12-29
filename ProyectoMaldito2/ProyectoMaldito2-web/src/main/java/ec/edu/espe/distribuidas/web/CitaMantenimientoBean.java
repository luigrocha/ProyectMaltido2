/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.CitaMantenimiento;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.CitaMantenimientoServicio;
import com.espe.distribuidas.servicio.ClienteServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean

public class CitaMantenimientoBean extends BaseBean implements Serializable {

    @EJB
    private CitaMantenimientoServicio citaServicio;

    @EJB
    private ClienteServicio clienteServicio;
    /**
     * variable que referencia un cliente seleccionado
     */
    private Cliente clienteSelected;

    /**
     * variable de referencia al objeto cliente
     */
    private Cliente cliente;
    /**
     * lista de clientes para el popup
     */
    private List<Cliente> clientes;
    /**
     * variable tipo lista de citas de mantenimiento para setar a una tabla del
     * formulario.
     */
    private List<CitaMantenimiento> citas;

    /**
     * variable tipo cita de mantenimiento para las operaciones del CRUD.
     */
    private CitaMantenimiento cita;

    /**
     * variable tipo cita.
     */
    private CitaMantenimiento citaSelected;

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
        this.clientes = this.clienteServicio.obtenerTodosClientes();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.seleccionar();
        // super.nuevo();
        this.cita = new CitaMantenimiento();
        this.setTitle("Ingresar Cita de Mantenimiento");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.cita = new CitaMantenimiento();
        this.setTitle("Modificar Cita de Mantenimiento");
        try {
            BeanUtils.copyProperties(this.cita, this.citaSelected);
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
        try {
            BeanUtils.copyProperties(this.cita, this.citaSelected);
            this.citaServicio.eliminarCita(this.cita.getIdCita());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.citas.remove(this.cita);
            this.setCitaSelected(null);
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

    public void onRowSelectPopUp(SelectEvent event) {
        this.diableAceptar = false;
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
                this.citaServicio.ingresarCitaMantenimiento(this.cita);
                // this.citas.add(0, this.cita);
                this.citas = this.citaServicio.obtenerTodasCitas();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la cita del cliente: " + this.cita.getIdCliente(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.citaServicio.actulizarCita(this.cita);
                BeanUtils.copyProperties(this.citaSelected, this.cita);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el cliente: " + this.cita.getIdCita() + " del cliente: " + this.cita.getClienteCita().getIdCliente(), null));
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

    /**
     * desactiva la tabla de seleccion
     */
    public void cancelarPopUp() {
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptarNuevo() {
        super.nuevo();
        this.cita = new CitaMantenimiento();
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

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isDiableAceptar() {
        return diableAceptar;
    }

    public void setDiableAceptar(boolean diableAceptar) {
        this.diableAceptar = diableAceptar;
    }

}
