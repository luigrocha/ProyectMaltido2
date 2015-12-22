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
 * Clase ProveedorBean que maneja el listarProveedores.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class ProveedorBean extends BaseBean implements Serializable {

    @EJB
    private ProveedorServicio proveedorServicio;

    /**
     * lista de proveedores.
     */
    private List<Proveedor> proveedor;

    /**
     * objeto de tipo proveedor para cruds.
     */
    private Proveedor proveedores;

    /**
     * objeto de tipo proveedor que controla la selección de la tabla de
     * proveedores.
     */
    private Proveedor proveedorSelected;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "";

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {
        this.proveedor = this.proveedorServicio.obtenerTodosProveedores();
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setProveedorSelected(null);
    }

    /**
     * metodo que recibe el evento de seleccion de una fila de la tabla de
     * clientes.
     *
     * @param event evento de tipo seleccion activado al seleccionar un registro
     * de una tabla.
     */
    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
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

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
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

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.nuevo();
        this.proveedores = new Proveedor();
        this.setTitle("Ingresar Proveedor");
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
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

    /**
     * metodo get de la lista de proveedores.
     *
     * @return retorna una lista de proveedores.
     */
    public List<Proveedor> getProveedor() {
        return proveedor;
    }

    /**
     * metodo set de la lista de proveedores.
     *
     * @param proveedor ingresa una lista de proveedores.
     */
    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * metodo get del objeto proveedor.
     * @return retorna un objeto tipo proveedor.
     */
    public Proveedor getProveedores() {
        return proveedores;
    }

    /**
     * metodo set del objeto proveedores.
     * @param proveedores admite una variable de tipo proveedor.
     */
    public void setProveedores(Proveedor proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * metodo get del objeto proveedorSelected.
     * @return retorna un objeto de tipo proveedor.
     */
    public Proveedor getProveedorSelected() {
        return proveedorSelected;
    }

    /**
     *metodo set del objeto proveedorselected.
     * @param proveedorSelected admite un objeto de tipo proveedor.
     */
    public void setProveedorSelected(Proveedor proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }

    /**
     * metodo get de disabled.
     * @return retirna un boolean.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de disabled.
     * @param disabled ingresa un boolean.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * metodo get de titulo.
     * @return retirna un string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de titulo.
     * @param title ingresa un string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
