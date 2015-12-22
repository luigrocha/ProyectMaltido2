/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.EmpleadoServicio;
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
public class EmpleadoBean extends BaseBean implements Serializable {

    @EJB
    private EmpleadoServicio empleadoServicio;

    private List<Empleado> empleados;

    private Empleado empleado;

    private Empleado empleadoSelected;

    private Boolean disabled = true;

    private String title = "";

    @PostConstruct
    public void inicializar() {
        this.empleados = this.empleadoServicio.obtenerTodosEmpleados();
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.empleado = new Empleado();
        this.setTitle("Ingresar Empleado");
    }

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

    public void eliminar() {
        this.empleado = new Empleado();
        try {
            BeanUtils.copyProperties(this.empleado, this.empleadoSelected);
            this.empleadoServicio.eliminarEmpleado(this.empleado.getIdEmpleado());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.setEmpleadoSelected(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Empleado Modificado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Override
    public void cancelar() {
            super.cancelar();
            this.setEmpleadoSelected(null);
         
    }

    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

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
        this.empleado = new Empleado();
    }

    public void registrarEmpleado() {
        try {
            empleadoServicio.ingresarEmpleado(empleado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Correcto"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error en el Registro"));
        }
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

}
