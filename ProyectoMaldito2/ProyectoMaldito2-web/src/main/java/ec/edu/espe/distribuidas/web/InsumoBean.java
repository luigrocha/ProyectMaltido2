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
 * Clase InsumoBean que maneja listInsumos.xhtml.
 *
 * @author R&R S.A.
 */
@ViewScoped
@ManagedBean
public class InsumoBean extends BaseBean implements Serializable {

    @EJB
    private InsumoServicio insumoServicio;

    /**
     * lista de insumos para setear en la tabla insumos.
     */
    private List<Insumos> insumo;

    /**
     * objeto de tipo insumo.
     */
    private Insumos insumos;

    /**
     * objeto de tipo insumo que intactua con la selección de la tabla.
     */
    private Insumos insumoSelected;

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
        this.insumo = this.insumoServicio.obtenerTodosInsumos();
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setInsumoSelected(null);
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
        this.insumos = new Insumos();
        this.setTitle("Modificar Insumo");
        try {
            BeanUtils.copyProperties(this.insumos, this.insumoSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
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

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.nuevo();
        this.insumos = new Insumos();
        this.setTitle("Ingresar Insumos");
    }

    /**
     * metodo get del objeto insumo.
     * @return retorna una lista de insumos.
     */
    public List<Insumos> getInsumo() {
        return insumo;
    }

    /**
     * metodo set del objeto insumo.
     * @param insumo ingresa una variable de tipo lista de insumos.
     */
    public void setInsumo(List<Insumos> insumo) {
        this.insumo = insumo;
    }

    /**
     * metodo get del objeto insumos.
     * @return retorna un objeto de insumo.
     */
    public Insumos getInsumos() {
        return insumos;
    }

    /**
     * metodo set del objeto Insumo.
     * @param insumos recibe un objeto de tipo insumo.
     */
    public void setInsumos(Insumos insumos) {
        this.insumos = insumos;
    }

    /**
     * metodo de tipo get del objeto insumoSelected.
     * @return retorna un tipo insumo.
     */
    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    /**
     * metodo set de insumoSelected.
     * @param insumoSelected recibe un tipo insumo.
     */
    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
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
