/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.InsumoServicio;
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
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class InsumoBean extends BaseBean implements Serializable {

    @EJB
    private InsumoServicio insumoServicio;

    private List<Insumos> insumo;

    private Insumos insumos;

    private Insumos insumoSelected;

    private Boolean disabled = true;

    private String title = "";

    @PostConstruct
    public void inicializar() {
        this.insumo = this.insumoServicio.obtenerTodosInsumos();
    }

    @Override
    public void cancelar() {
        super.cancelar();
    }

    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

    @Override
    public void modificar() {
        super.modificar();
        this.insumos = new Insumos();
        this.setTitle("Modificar Insumo");
        try {
            BeanUtils.copyProperties(this.insumos, this.insumoSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void eliminar() {
        this.insumos = new Insumos();
        try {
            BeanUtils.copyProperties(this.insumos, this.insumoSelected);
            this.insumoServicio.eliminarInsumo(this.insumos.getIdInsumo());
            this.insumo.remove(this.insumos);
            this.setInsumoSelected(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));

        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public void aceptar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            try {
                // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.insumoServicio.ingresarInsumo(this.insumos);
                this.insumo.add(0, this.insumos);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el insumo: " + this.insumos.getNombre(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.insumoServicio.actualizarInsumo(this.insumos);
                BeanUtils.copyProperties(this.insumoSelected, this.insumos);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el cliente: " + this.insumos.getNombre(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setInsumoSelected(null);
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.insumos = new Insumos();
        this.setTitle("Ingresar Insumos");
    }

    public List<Insumos> getInsumo() {
        return insumo;
    }

    public void setInsumo(List<Insumos> insumo) {
        this.insumo = insumo;
    }

    public Insumos getInsumos() {
        return insumos;
    }

    public void setInsumos(Insumos insumos) {
        this.insumos = insumos;
    }

    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
