/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.DetalleDevolucion;
import com.espe.distribuidas.model.Devoluciones;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.Proveedor;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.DetalleDevolucionServicio;
import com.espe.distribuidas.servicio.DevolucionesServicio;
import com.espe.distribuidas.servicio.InsumoServicio;
import com.espe.distribuidas.servicio.ProveedorServicio;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Luig Rocha
 */
@ViewScoped
@ManagedBean

public class DevolucionBean extends BaseBean implements Serializable {

    @EJB
    private DevolucionesServicio devolucionServicio;

    @EJB
    private DetalleDevolucionServicio detalleDevolucionServicio;

    @EJB
    private ProveedorServicio proveedorServicio;

    @EJB
    private InsumoServicio insumoServicio;
    /**
     * variable que referencia una devolucion seleccionada
     */
    private Proveedor proveedorSelected;

    /**
     * variable de referencia al objeto proveedor
     */
    private Proveedor proveedor;
    /**
     * variable que referencia una insumo seleccionada
     */
    private Insumos insumoSelected;

    /**
     * variable de referencia al objeto insumo
     */
    private Insumos insumo;

    /**
     * variable de referencia al objeto insumodetalle
     */
    private Insumos insumodetalle;

    /**
     * lista de proveedors para el popup
     */
    private List<Proveedor> proveedores;
    /**
     * lista de Insumos para el popup
     */
    private List<Insumos> insumos;
    /**
     * lista de Insumos para el popup
     */
    private List<Insumos> insumoDetalle;
    /**
     * variable tipo lista de devolucions de mantenimiento para setar a una
     * tabla del formulario.
     */
    private List<Devoluciones> devoluciones;

    /**
     * variable tipo devolucion de mantenimiento para las operaciones del CRUD.
     */
    private Devoluciones devolucion;

    /**
     * variable tipo devolucion.
     */
    private List<Devoluciones> devolucionSelected;

    /**
     * variable tipo lista de detalles devoluciones para setear a una tabla del
     * formulario.
     */
    private List<DetalleDevolucion> detalleDevoluciones;

    /**
     * variable tipo devolucion de mantenimiento para las operaciones del CRUD.
     */
    private DetalleDevolucion detalledevolucion;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "Devoluciones";

    /**
     * variable que controla el estado del boton aceptar de la tabla de
     * seleccion de proveedor.
     */
    private boolean disableAceptar = true;

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {

        this.devoluciones = this.devolucionServicio.obtenerTodasDevoluciones();
        this.proveedores = this.proveedorServicio.obtenerTodosProveedores();
        this.insumos = this.insumoServicio.obtenerTodosInsumos();
        this.insumoDetalle = new ArrayList<>();
        this.detalleDevoluciones = new ArrayList<>();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.seleccionar();
//    super.nuevo();
        this.devolucion = new Devoluciones();
        this.setTitle("Ingresar Devolución ");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.devolucion = new Devoluciones();
        this.setTitle("Modificar Devoluciones");
        try {
            BeanUtils.copyProperties(this.devolucion, this.devolucionSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.devolucion = new Devoluciones();
        try {
            BeanUtils.copyProperties(this.devolucion, this.devolucionSelected);
            this.devolucionServicio.eliminarDevolucion(this.devolucion.getIdDevoluciones());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.devoluciones.remove(this.devolucion);
            this.setDevolucionSelected(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo sobreescrito de la clase base permite setear en blanco y por
     * defecto los valores del formulario.
     */
    @Override
    public void cancelar() {
        super.cancelar();
        this.setDevolucionSelected(null);

    }

    /**
     * metodo que recibe el evento de seleccion de una fila de la tabla de
     * proveedors.
     *
     * @param event evento de tipo seleccion activado al seleccionar un registro
     * de una tabla.
     */
    public void onRowSelect(SelectEvent event) {
        this.disabled = false;
    }

    public void onRowSelectPopUp(SelectEvent evt) {
        this.disableAceptar = false;
    }

    public void onRowSelectInsumo(SelectEvent evt) {
        this.insumodetalle = new Insumos();
        insumoDetalle.add(this.insumoSelected);

        this.detalledevolucion = new DetalleDevolucion();

        this.detalledevolucion.setIdInsumo(this.insumoSelected.getIdInsumo());
        this.detalledevolucion.setIdDevolucion(28);
        this.detalledevolucion.setCantidad(this.insumoSelected.getCantidad());
        detalleDevoluciones.add(this.detalledevolucion);
        this.setInsumo(null);
        super.quitarSeleccion();
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        Devoluciones devolucionestmp;
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            try {
                // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.devolucionServicio.ingresarDevolucion(this.devolucion);
               
                devolucionestmp = this.devolucionServicio.findLast();
                insertarIdDevolusion(devolucionestmp, detalleDevoluciones);

                for (DetalleDevolucion detalleDevolucione : detalleDevoluciones) {

                    this.detalleDevolucionServicio.ingresarDetalleDevolucion(detalleDevolucione);
                }

                //this.devoluciones.add(0, this.devolucion);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la devolucion: "
                        + this.devolucion.getIdDevoluciones() + " del proveedor: " + this.devolucion.getDevolucionProveedor().getIdProveedor(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.devolucionServicio.actualizarDevolucion(this.devolucion);
                BeanUtils.copyProperties(this.devolucionSelected, this.devolucion);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico la devolucion: " + this.devolucion.getIdDevoluciones()
                        + " del proveedor: " + this.devolucion.getDevolucionProveedor().getIdProveedor(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setDevolucionSelected(null);
    }

    /**
     *inserta en la lista de detalles de devolucion el is de la devo;lucion asociada.
     * @param devolucionesid recive el objeto del id de devolucion asociada.
     * @param lista recove la lista a insertar.
     */
    public void insertarIdDevolusion(Devoluciones devolucionesid,List<DetalleDevolucion> lista) {
        for(int i=0;i<lista.size();i++)
        {
        lista.get(i).setIdDevolucion(devolucionesid.getIdDevoluciones());
        }
    }

    public void habilitarSeleccionProveedor() {
        super.seleccionar();
    }

    /**
     * setea el id de la tabla en el parametro del formulario.
     */
    public void aceptarPopUp() {
        super.nuevo();
        this.proveedor = new Proveedor();
        try {

            BeanUtils.copyProperties(this.proveedor, this.proveedorSelected);
            this.devolucion.setIdProveedor(this.proveedor.getIdProveedor());

        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(DevolucionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setDevolucionSelected(null);
        this.setProveedor(null);
        super.quitarSeleccion();
    }

    /**
     * desactiva la tabla de seleccion
     */
    public void cancelarPopUp() {
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptarNuevo() {
        super.nuevo();
        this.devolucion = new Devoluciones();
    }

    /**
     * metodo get de titulo.
     *
     * @return retirna un string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * metodo set de titulo.
     *
     * @param title ingresa un string.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * metodo get de disabled.
     *
     * @return retorna un boolean.
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * metodo set de disabled.
     *
     * @param disabled ingresa un boolean.
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public DevolucionesServicio getDevolucionServicio() {
        return devolucionServicio;
    }

    public void setDevolucionServicio(DevolucionesServicio devolucionServicio) {
        this.devolucionServicio = devolucionServicio;
    }

    public ProveedorServicio getProveedorServicio() {
        return proveedorServicio;
    }

    public void setProveedorServicio(ProveedorServicio proveedorServicio) {
        this.proveedorServicio = proveedorServicio;
    }

    public Proveedor getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Proveedor proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Devoluciones> getDevoluciones() {
        return devoluciones;
    }

    public DetalleDevolucionServicio getDetalleDevolucionServicio() {
        return detalleDevolucionServicio;
    }

    public void setDetalleDevolucionServicio(DetalleDevolucionServicio detalleDevolucionServicio) {
        this.detalleDevolucionServicio = detalleDevolucionServicio;
    }

    public List<DetalleDevolucion> getDetalleDevoluciones() {
        return detalleDevoluciones;
    }

    public void setDetalleDevoluciones(List<DetalleDevolucion> detalleDevoluciones) {
        this.detalleDevoluciones = detalleDevoluciones;
    }

    public DetalleDevolucion getDetalledevolucion() {
        return detalledevolucion;
    }

    public void setDetalledevolucion(DetalleDevolucion detalledevolucion) {
        this.detalledevolucion = detalledevolucion;
    }

    public void setDevoluciones(List<Devoluciones> devoluciones) {
        this.devoluciones = devoluciones;
    }

    public Devoluciones getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devoluciones devolucion) {
        this.devolucion = devolucion;
    }

    public List<Devoluciones> getDevolucionSelected() {
        return devolucionSelected;
    }

    public void setDevolucionSelected(List<Devoluciones> devolucionSelected) {
        this.devolucionSelected = devolucionSelected;
    }

    public InsumoServicio getInsumoServicio() {
        return insumoServicio;
    }

    public void setInsumoServicio(InsumoServicio insumoServicio) {
        this.insumoServicio = insumoServicio;
    }

    public Insumos getInsumoSelected() {
        return insumoSelected;
    }

    public void setInsumoSelected(Insumos insumoSelected) {
        this.insumoSelected = insumoSelected;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }

    public Insumos getInsumodetalle() {
        return insumodetalle;
    }

    public void setInsumodetalle(Insumos insumodetalle) {
        this.insumodetalle = insumodetalle;
    }

    public List<Insumos> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Insumos> insumos) {
        this.insumos = insumos;
    }

    public List<Insumos> getInsumoDetalle() {
        return insumoDetalle;
    }

    public void setInsumoDetalle(List<Insumos> insumoDetalle) {
        this.insumoDetalle = insumoDetalle;
    }

    public boolean isDisableAceptar() {
        return disableAceptar;
    }

    public void setDisableAceptar(boolean disableAceptar) {
        this.disableAceptar = disableAceptar;
    }

}
