/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.servicio.ClienteServicio;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class ClienteBean extends BaseBean implements Serializable {

    @EJB
    private ClienteServicio clienteServicio;

    private List<Cliente> clientes;

    private Cliente cliente;

    private Cliente clienteSelected;

    private Boolean disabled = true;

    private String title = "";

    @PostConstruct
    public void inicializar() {
        this.clientes = this.clienteServicio.obtenerTodosClientes();
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.cliente = new Cliente();
        this.setTitle("Ingresar Cliente");
    }

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

    @Override
    public void cancelar() {
        super.cancelar();
        this.setClienteSelected(null);
    }

    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void aceptarNuevo() {
        super.nuevo();
        this.cliente = new Cliente();
    }

    public void registrarCliente() {
        try {
            clienteServicio.ingresarCliente(cliente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error en el Registro"));
        }
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

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

}
