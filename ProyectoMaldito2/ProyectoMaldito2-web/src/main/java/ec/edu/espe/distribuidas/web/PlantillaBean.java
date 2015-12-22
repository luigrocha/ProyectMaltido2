/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Empleado;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;



/**
 *
 * @author Luig Rocha
 */
@SessionScoped
@ManagedBean
public class PlantillaBean implements Serializable {

    public String redireccionar() {
        return "/index.xhtml";
    }

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
    public void cerrarSesion(){
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
