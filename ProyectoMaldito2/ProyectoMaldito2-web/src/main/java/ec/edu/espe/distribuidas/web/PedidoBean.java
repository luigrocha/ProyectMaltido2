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

import com.espe.distribuidas.model.DetallePedido;
import com.espe.distribuidas.model.Pedido;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.Proveedor;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.DetallePedidoServicio;
import com.espe.distribuidas.servicio.PedidoServicio;
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

public class PedidoBean extends BaseBean implements Serializable {

    @EJB
    private PedidoServicio pedidoServicio;

    @EJB
    private DetallePedidoServicio detallePedidoServicio;

    @EJB
    private ProveedorServicio proveedorServicio;

    @EJB
    private InsumoServicio insumoServicio;
    /**
     * variable que referencia una pedido seleccionada
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
     * variable tipo lista de pedidos de mantenimiento para setar a una
     * tabla del formulario.
     */
    private List<Pedido> pedidos;

    /**
     * variable tipo pedido de mantenimiento para las operaciones del CRUD.
     */
    private Pedido pedido;

    /**
     * variable tipo pedido.
     */
    private Pedido pedidoSelected;

    /**
     * variable tipo lista de detalles pedidos para setear a una tabla del
     * formulario.
     */
    private List<DetallePedido> detallePedidos;

    /**
     * variable tipo pedido de mantenimiento para las operaciones del CRUD.
     */
    private DetallePedido detallepedido;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "Pedidos";

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

        this.pedidos = this.pedidoServicio.obtenerTodosPedidos();
        this.proveedores = this.proveedorServicio.obtenerTodosProveedores();
        this.insumos = this.insumoServicio.obtenerTodosInsumos();
        this.insumoDetalle = new ArrayList<>();
        this.detallePedidos = new ArrayList<>();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.seleccionar();
//    super.nuevo();
        this.pedido = new Pedido();
        this.setTitle("Ingresar Pedido ");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.pedido = new Pedido();
        this.setTitle("Modificar Pedidos");
        try {
            BeanUtils.copyProperties(this.pedido, this.pedidoSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.pedido = new Pedido();
        try {
            BeanUtils.copyProperties(this.pedido, this.pedidoSelected);
            this.pedidoServicio.eliminarPedido(this.pedido.getIdPedido());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.pedidos.remove(this.pedido);
            this.setPedidoSelected(null);
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
        this.setPedidoSelected(null);

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

        this.detallepedido = new DetallePedido();

        detallePedidos.add(this.detallepedido);
        this.setInsumo(null);
        super.quitarSeleccion();
    }
 

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        Pedido pedidostmp;
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            try {
                // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.pedidoServicio.ingresarPedido(this.pedido);
               
                pedidostmp = this.pedidoServicio.findLast();
                insertarIdDevolusion(pedidostmp, detallePedidos);

                for (DetallePedido detallePedidoe : detallePedidos) {

                    this.detallePedidoServicio.ingresarDetallePedido(detallePedidoe);
                }
                this.pedidos=pedidoServicio.obtenerTodosPedidos();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la pedido: "
                        + this.pedido.getIdPedido() + " del proveedor: " + this.pedido.getProveedorPedido().getIdProveedor(), null));
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.pedidoServicio.actualizarPedido(this.pedido);
                BeanUtils.copyProperties(this.pedidoSelected, this.pedido);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico la pedido: " + this.pedido.getIdPedido()
                        + " del proveedor: " + this.pedido.getProveedorPedido().getIdProveedor(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

        this.reset();
        this.setPedidoSelected(null);
    }

    /**
     *inserta en la lista de detalles de pedido el is de la devo;lucion asociada.
     * @param pedidosid recive el objeto del id de pedido asociada.
     * @param lista recove la lista a insertar.
     */
    public void insertarIdDevolusion(Pedido pedidosid,List<DetallePedido> lista) {
        for(int i=0;i<lista.size();i++)
        {
        lista.get(i).setIdPedido(pedidosid.getIdPedido());
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
            this.pedido.setIdProveedor(this.proveedor.getIdProveedor());

        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(PedidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setPedidoSelected(null);
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
        this.pedido = new Pedido();
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

    public PedidoServicio getPedidoServicio() {
        return pedidoServicio;
    }

    public void setPedidoServicio(PedidoServicio pedidoServicio) {
        this.pedidoServicio = pedidoServicio;
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public DetallePedidoServicio getDetallePedidoServicio() {
        return detallePedidoServicio;
    }

    public void setDetallePedidoServicio(DetallePedidoServicio detallePedidoServicio) {
        this.detallePedidoServicio = detallePedidoServicio;
    }

    public List<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    public DetallePedido getDetallepedido() {
        return detallepedido;
    }

    public void setDetallepedido(DetallePedido detallepedido) {
        this.detallepedido = detallepedido;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido getPedidoSelected() {
        return pedidoSelected;
    }

    public void setPedidoSelected(Pedido pedidoSelected) {
        this.pedidoSelected = pedidoSelected;
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
