/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import java.io.Serializable;

/**
 *
 * @author Andres Vr
 */
public class BaseBean  implements Serializable{
    
    private boolean enNuevo;
    private boolean enModificar;
    
    public boolean isEnNuevo() {
        return enNuevo;
    }

    public void setEnNuevo(boolean enNuevo) {
        this.enNuevo = enNuevo;
    }

    public boolean isEnModificar() {
        return enModificar;
    }

    public void setEnModificar(boolean enModificar) {
        this.enModificar = enModificar;
    }
    
    public void nuevo() {
        this.enNuevo = true;
    }
    
    public void modificar() {
        this.enModificar = true;
    }
    
    public void cancelar() {
        this.reset();
    }
    
    public void reset() {
        this.enModificar = false;
        this.enNuevo = false;
    }
}
