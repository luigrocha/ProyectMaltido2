/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.Proveedor;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.ProveedorServicio;
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
public class ProveedorBean extends BaseBean implements Serializable {

    @EJB
    private ProveedorServicio proveedorServicio;

    private List<Proveedor> proveedor;

    private Proveedor proveedores;

    private Proveedor proveedorSelected;

    private Boolean disabled = true;

    private String title = "";

    @PostConstruct
    public void inicializar() {
        this.proveedor = this.proveedorServicio.obtenerTodosProveedores();
    }

    @Override
    public void cancelar() {
        super.cancelar(); 
        this.setProveedorSelected(null);
    }

    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

    @Override
    public void modificar() {
        super.modificar();
        this.proveedores = new Proveedor();
        this.setTitle("Modificar Proveedor");
        try {
            BeanUtils.copyProperties(this.proveedores, this.proveedorSelected);
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
                this.proveedorServicio.ingresarProveedor(this.proveedores);
                this.proveedor.add(0, this.proveedores);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro el proveedor: " + this.proveedores.getNombreEmpresa(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.proveedorServicio.actualizarProveedor(this.proveedores);
                BeanUtils.copyProperties(this.proveedorSelected, this.proveedores);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico el proveedor: " + this.proveedores.getNombreEmpresa(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setProveedorSelected(null);
    }

    @Override
    public void nuevo() {
        super.nuevo();
        this.proveedores = new Proveedor();
        this.setTitle("Ingresar Proveedor");
    }

    public void eliminar() {
        this.proveedores = new Proveedor();
        try {
            BeanUtils.copyProperties(this.proveedores, this.proveedorSelected);
            this.proveedorServicio.eliminarProveedor(this.proveedores.getIdProveedor());
            this.proveedor.remove(this.proveedores);
            this.setProveedorSelected(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));

        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    public List<Proveedor> getProveedor() {
        return proveedor;
    }

    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedor = proveedor;
    }

    public Proveedor getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedor proveedores) {
        this.proveedores = proveedores;
    }

    public Proveedor getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Proveedor proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
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
