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
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Clase LoginBean que maneja index.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class LoginBean implements Serializable {

    @EJB
    private EmpleadoServicio empleadoServicio;
    
    /**
     * variable de tipo empleado.
     */
    private Empleado empleado;

     /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        empleado = new Empleado();
    }
    
    /**
     * metodo que valida el inicio de sesion a la aplicacion.
     * @return retorna un string con el argumento de redirección a la pagina despues de 
     * iniciar sesion.
     */
    public String iniciarSesion() {
        Empleado empleadotmp;
        String redireccion = null;
        try {
            empleadotmp = empleadoServicio.buscarPorUsuarioPassword(empleado.getUsuario(), empleado.getContrasena()).get(0);
            if (empleadotmp != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Bienvenido"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", empleado);
                redireccion = "/views/dashboard?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Credenciales Incorrectas"));

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Credenciales Incorrectas"));

        }
        return redireccion;
    }

    /**
     * metodo get del objeto empleado.
     * @return retorna un tipo empleado.
     */
    public Empleado getEmpleado() {
        return empleado;
    }
    
    /**
     * metodo set de empleado.
     * @param empleado  acepta un objeto de tipo empleado.
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public void cerrarSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    

}
