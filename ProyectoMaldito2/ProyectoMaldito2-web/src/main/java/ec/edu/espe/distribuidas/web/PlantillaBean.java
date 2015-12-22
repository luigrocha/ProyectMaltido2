/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Empleado;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Luig Rocha
 */
@Named
@ViewScoped
public class PlantillaBean implements Serializable{
    
    public void verificarSesion(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Empleado empleado = (Empleado) context.getExternalContext().getSessionMap().get("usuario");
            if (empleado == null){
                context.getExternalContext().redirect("./../permisos.xhtml");
            }
        } catch (Exception e) {
        }
    }

}
