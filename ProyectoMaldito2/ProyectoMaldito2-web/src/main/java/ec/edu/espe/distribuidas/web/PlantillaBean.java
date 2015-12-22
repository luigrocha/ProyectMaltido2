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
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Clase PlantillaBean que maneja la variable de sesion de la aplicación.
 *
 * @author R&R S.A.
 */
@SessionScoped
@ManagedBean
public class PlantillaBean implements Serializable {

    /**
     * metodo que permite la redirección al index de la aplicación.
     * @return retorna un string con la dirección del index de la aplicación.
     */
    public String redireccionar() {
        return "/index.xhtml";
    }

    /**
     * metodo que verifica el inicio de sesión antes del acceso a las ventanas internas.
     * de la plicación, de no haber iniciado sesión redirecciona a la pagina de permisos.xhtml.
     */
    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Empleado empleado = (Empleado) context.getExternalContext().getSessionMap().get("usuario");
            if (empleado == null) {
                context.getExternalContext().redirect("./../permisos.xhtml");
            }
        } catch (Exception e) {
        }
    }

    /**
     * metodo que desacopla el faces context y la instancia de la variable 
     * de sesión para finalizarla.
     */
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
