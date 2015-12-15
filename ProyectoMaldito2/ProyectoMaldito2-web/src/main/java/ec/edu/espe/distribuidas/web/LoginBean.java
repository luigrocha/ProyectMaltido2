/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {

    @EJB
    private EmpleadoServicio empleadoServicio;
    private Empleado empleado;

    @PostConstruct
    public void inicializar() {
        empleado = new Empleado();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String iniciarSesion() {
        Empleado empleadotmp;
        String redireccion = null;
        try {
            empleadotmp = empleadoServicio.buscarPorUsuarioPassword(empleado.getUsuario(), empleado.getContrasena()).get(0);
            if (empleadotmp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Bienvenido"));
                redireccion = "/empleado/listarEmpleados?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Credenciales Incorrectas"));

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Credenciales Incorrectas"));

        }
        return redireccion;
    }

}
