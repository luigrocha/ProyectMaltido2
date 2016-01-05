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

import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.servicio.ClienteServicio;
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
 * Clase ClienteBean que maneja listClientes.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class ClienteBean extends BaseBean implements Serializable {

    @EJB
    private ClienteServicio clienteServicio;

    /**
     * lista de clientes para setear en una tabla.
     */
    private List<Cliente> clientes;

    /**
     * instancia de la clase cliente que permite realizar las operaciones del
     * CRUD.
     */
    private Cliente cliente;

    /**
     * instancia de la clase cliente que permite setear los atributos de un
     * cliente seleccionado de la tabla de clientes general.
     */
    private Cliente clienteSelected;

    /**
     * variable de estado para habital o desabilitar opciones de la interfaz.
     */
    private Boolean disabled = true;

    /**
     * variable tipo string para cambiar el titulo del formulario de registo o
     * modificacion.
     */
    private String title = "";
    
    /**
     *varianble para desabilitar opciones en la modificacion.
     */
    private Boolean disabledModificar = true;

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        this.clientes = this.clienteServicio.obtenerTodosClientes();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.nuevo();
        this.cliente = new Cliente();
        this.setTitle("Ingresar Cliente");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.cliente = new Cliente();
        this.setTitle("Modificar Cliente");
        try {
            BeanUtils.copyProperties(this.cliente, this.clienteSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.cliente = new Cliente();
        try {
            BeanUtils.copyProperties(this.cliente, this.clienteSelected);
            this.clienteServicio.eliminarCliente(this.cliente.getIdCliente());
            this.clientes.remove(this.cliente);
            this.setClienteSelected(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));

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
        this.setClienteSelected(null);
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
        this.disabledModificar=false;
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
                this.clienteServicio.ingresarCliente(this.cliente);
                this.clientes.add(0, this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el cliente: " + this.cliente.getNombre() + " " + this.cliente.getApellido(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.clienteServicio.actulizarCliente(this.cliente);
                BeanUtils.copyProperties(this.clienteSelected, this.cliente);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el cliente: " + this.cliente.getNombre() + " " + this.cliente.getApellido(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setClienteSelected(null);
    }

    /**
     * metodo aceptar nuevo habilita opciones de la interfaz.
     */
    public void aceptarNuevo() {
        super.nuevo();
        this.cliente = new Cliente();
    }

    /**
     * permite ingresar un cliente en la BDD.
     */
    public void registrarCliente() {
        try {
            clienteServicio.ingresarCliente(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error en el Registro"));
        }
    }

    /**
     * metodo get de la lista de clientes.
     *
     * @return devuelve una lista de clientes.
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * metodo set de la lista de clientes.
     *
     * @param clientes permite ingresar una lista de clientes.
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * metodo ger de cliente.
     *
     * @return retorna un objeto de tipo cliente.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * metodo set del cliente.
     *
     * @param cliente recibe un tipo cliente.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * metodo get de cliente selected.
     *
     * @return retorna un cliente.
     */
    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    /**
     * metodo set de cliente seleccionado.
     *
     * @param clienteSelected recibe un objeto de tipo cliente.
     */
    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    /**
     * metodo get de la variable titulo.
     *
     * @return retorna el valor del titulo.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de la variable titulo.
     *
     * @param title cadena que representa al titulo.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * metodo get de la variable disable.
     *
     * @return retorna el valor de disable.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de la variable disabled.
     *
     * @param disabled boolean que representa al estado de la variable.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getDisabledModificar() {
        return disabledModificar;
    }

    public void setDisabledModificar(Boolean disabledModificar) {
        this.disabledModificar = disabledModificar;
    }

}
