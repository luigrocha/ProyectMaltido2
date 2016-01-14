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

import com.espe.distribuidas.model.DetalleFactura;
import com.espe.distribuidas.model.Factura;
import com.espe.distribuidas.model.Mantenimiento;
import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import com.espe.distribuidas.servicio.DetalleFacturaServicio;
import com.espe.distribuidas.servicio.FacturaServicio;
import com.espe.distribuidas.servicio.MantenimientoServicio;
import com.espe.distribuidas.servicio.ClienteServicio;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Luig Rocha
 */
@ViewScoped
@ManagedBean

public class FacturaBean extends BaseBean implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private DetalleFacturaServicio detalleFacturaServicio;

    @EJB
    private ClienteServicio clienteServicio;

    @EJB
    private MantenimientoServicio mantenimientoServicio;
    protected StreamedContent file;
    /**
     * variable que referencia un cliente seleccionada
     */
    private Cliente clienteSelected;

    /**
     * variable de referencia al objeto cliente
     */
    private Cliente cliente;
    /**
     * variable que referencia una mantenimiento seleccionada
     */
    private Mantenimiento mantenimientoSelected;

    /**
     * variable de referencia al objeto mantenimiento
     */
    private Mantenimiento mantenimiento;

    /**
     * lista de clientes para el popup
     */
    private List<Cliente> clientes;
    /**
     * lista de Mantenimiento para el popup
     */
    private List<Mantenimiento> mantenimientos;

    /**
     * variable tipo lista de facturas de mantenimiento para setar a una tabla
     * del formulario.
     */
    private List<Factura> facturas;

    /**
     * variable tipo factura de mantenimiento para las operaciones del CRUD.
     */
    private Factura factura;

    /**
     * variable tipo factura.
     */
    private Factura facturaSelected;

    /**
     * variable tipo lista de detalles facturas para setear a una tabla del
     * formulario.
     */
    private List<DetalleFactura> detalleFacturas;

    /**
     * variable tipo factura de mantenimiento para las operaciones del CRUD.
     */
    private DetalleFactura detallefactura;

    /**
     * variable tipo boolean para estados del formulario.
     */
    private Boolean disabled = true;

    /**
     * variable tipo String para los titulos del formulario.
     */
    private String title = "Factura";

    /**
     * variable que controla el estado del boton aceptar de la tabla de
     * seleccion de cliente.
     */
    private boolean disableAceptar = true;
    
    private boolean desabilitarEnvio=true;

    /**
     * metodo que se inicializa despues de cargar el formulario contiene la
     * anotacion postconstructor.
     */
    @PostConstruct
    public void inicializar() {

        this.facturas = this.facturaServicio.obtenerTodasFacturas();
        this.clientes = this.clienteServicio.obtenerTodosClientes();

        // this.mantenimientos = new ArrayList<>();
        this.detalleFacturas = new ArrayList<>();
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion nuevo
     * habilita el formulario en la misma operacion.
     */
    @Override
    public void nuevo() {
        super.seleccionar();
//    super.nuevo();
        this.factura = new Factura();
        this.setTitle("Ingresar Factura ");
    }

    /**
     * metodo sobreescrito de la clase basebean que denota la operacion
     * modificar habilita el formulario en la misma operacion.
     */
    @Override
    public void modificar() {
        super.modificar();
        this.factura = new Factura();
        this.setTitle("Modificar Factura");
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);
        } catch (IllegalAccessException | InvocationTargetException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no controlado", e.getMessage()));
        }
    }

    /**
     * metodo eliminar, permite borrar un registro de la base de datos.
     */
    public void eliminar() {
        this.factura = new Factura();
        try {
            BeanUtils.copyProperties(this.factura, this.facturaSelected);
            this.facturaServicio.eliminarFactura(this.factura.getIdFactura());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro Eliminado Corectamente"));
            this.facturas.remove(this.factura);
            this.setFacturaSelected(null);
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
        this.setFacturaSelected(null);

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

    public void onRowSelectPopUp(SelectEvent evt) {
        this.disableAceptar = false;

    }

    public void onRowSelectMantenimiento(SelectEvent evt) {
        //mantenimiento = new Mantenimiento();

        //mantenimientos.add(this.mantenimientoSelected);
        this.detallefactura = new DetalleFactura();

        this.detallefactura.setIdCita(mantenimientoSelected.getPrimaryKey().getIdCita());
        this.detallefactura.setIdTecnico(mantenimientoSelected.getEmpleadoMantenimiento().getIdEmpleado());
        this.detallefactura.setCantidad(1);
        this.detallefactura.setValorUnitario(mantenimientoSelected.getPrecio());

        this.detalleFacturas.add(this.detallefactura);

        this.factura.setTotal(Total());
        this.mantenimientos.remove(this.mantenimientoSelected);
        this.setMantenimiento(null);
        super.quitarSeleccion();
    }

    /**
     * metodo que controla el boton aceptar del formulario. se comporta de 2
     * maneras, para la primera guarda un nuevo registro en la base de datos.
     * para la segunda actualiza un registro de la base de datos.
     */
    public void aceptar() {
        Factura facturastmp;
        FacesContext context = FacesContext.getCurrentInstance();
        if (super.isEnNuevo()) {
            try {
                // Usuario usuario = (Usuario)((HttpServletRequest)context.getExternalContext().getRequest()).getSession().getAttribute("usuario");
                this.facturaServicio.ingresarFactura(this.factura);

                facturastmp = this.facturaServicio.findLast();
                insertarIdFactura(facturastmp, detalleFacturas);

                for (DetalleFactura detalleFacturae : detalleFacturas) {

                    this.detalleFacturaServicio.ingresarDetalleFactura(detalleFacturae);
                }
                this.facturas = facturaServicio.obtenerTodasFacturas();

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se registro la factura: "
                        + " del cliente: " + this.factura.getClienteFactura().getIdCliente(), null));
                this.setDesabilitarEnvio(false);
            } catch (Exception e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        } else {
            try {
                //Llamar a modificar no a crear
                this.facturaServicio.actualizarFactura(this.factura);
                BeanUtils.copyProperties(this.facturaSelected, this.factura);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se modifico la factura: " + this.factura.getIdFactura()
                        + " del cliente: " + this.factura.getClienteFactura().getIdCliente(), null));
            } catch (ValidacionException | IllegalAccessException | InvocationTargetException e) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }
        }

//        this.reset();
        //      this.setFacturaSelected(null);
    }

    /**
     * inserta en la lista de detalles de factura el is de la devo;lucion
     * asociada.
     *
     * @param facturasid recive el objeto del id de factura asociada.
     * @param lista recove la lista a insertar.
     */
    public void insertarIdFactura(Factura facturasid, List<DetalleFactura> lista) {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setIdFactura(facturasid.getIdFactura());
        }
    }

    public void habilitarSeleccionCliente() {
        super.seleccionar();
    }

    /**
     * setea el id de la tabla en el parametro del formulario.
     */
    public void aceptarPopUp() {
        super.nuevo();
        this.cliente = new Cliente();
        this.factura = new Factura();
        try {

            BeanUtils.copyProperties(this.cliente, this.clienteSelected);
            this.factura.setIdCliente(this.cliente.getIdCliente());
            this.factura.setClienteFactura(this.cliente);
            this.mantenimientos = this.mantenimientoServicio.obtenerTodosMantenimientosCliente(this.cliente);
            this.factura.setFecha(new Date());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(FacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setFacturaSelected(null);
        this.setCliente(null);
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
        this.factura = new Factura();
    }

    /**
     * metodo que realiza el total del detalle factura
     *
     * @return BigDecimal total
     */
    public BigDecimal Total() {

        BigDecimal total;
        total = BigDecimal.valueOf(0);
        for (DetalleFactura i : detalleFacturas) {
            total = total.add(i.getValorUnitario());
        }
        return total;

    }

    public void generateReport() {
        //generar reporte
        super.pdf("factura", "factura");
        try {
            this.testMultiPartEmail(this.factura.getClienteFactura().getCorreoElectronico());
        } catch (UnsupportedEncodingException | EmailException | MalformedURLException ex) {
            Logger.getLogger(ReportFacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
                this.reset();
              this.setFacturaSelected(null);
        
    }

    public void testMultiPartEmail(String correo) throws UnsupportedEncodingException, EmailException, MalformedURLException {

        EmailAttachment att2 = new EmailAttachment();
        att2.setPath("C:\\Users\\Andres Vr\\Documents\\Git\\ProyectoMaldito7.0\\ProyectMaltido2\\ProyectoMaldito2\\ProyectoMaldito2-web\\src\\main\\webapp\\pdf\\factura.pdf");
        att2.setDisposition(EmailAttachment.ATTACHMENT);
        att2.setDescription("Envio Factura Mantenimiento SpotLinght&Wires");

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setSSLOnConnect(true);
        email.setAuthentication("spotwires@gmail.com", "084383260a");

        email.addTo(correo);
        email.setFrom("spotwires@gmail.com");
        email.setSubject("Factura");
        email.setMsg("Adjunto Factura");

        email.attach(att2);

        email.send();
//            SimpleEmail mail = new SimpleEmail();
// 
//        //Configuracion necesaria para GMAIL
//        mail.setHostName("smtp.gmail.com");
//        mail.setTLS(true);
//        mail.setSmtpPort(587);
//        mail.setSSL(true);
//        //En esta seccion colocar cuenta de usuario de Gmail y contraseña
//        mail.setAuthentication("spotwires@gmail.com", "084383260a");
// 
//        //Cuenta de Email Destino
//        mail.addTo("avrz237@gmail.com");
//        //Cuenta de Email Origen, la misma con la que nos autenticamos
//        mail.setFrom("spotwires@gmail.com");
//        //Titulo del Email
//        mail.setSubject("Email enviado usando Apache Commons Email");
//        //Contenido del Email
//        mail.setMsg("Mail enviado usando una cuenta de correo de GMAIL");
//        mail.send();
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

    public DetalleFacturaServicio getDetalleFacturaServicio() {
        return detalleFacturaServicio;
    }

    public void setDetalleFacturaServicio(DetalleFacturaServicio detalleFacturaServicio) {
        this.detalleFacturaServicio = detalleFacturaServicio;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mantenimiento getMantenimientoSelected() {
        return mantenimientoSelected;
    }

    public void setMantenimientoSelected(Mantenimiento mantenimientoSelected) {
        this.mantenimientoSelected = mantenimientoSelected;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Factura getFacturaSelected() {
        return facturaSelected;
    }

    public void setFacturaSelected(Factura facturaSelected) {
        this.facturaSelected = facturaSelected;
    }

    public List<DetalleFactura> getDetalleFacturas() {
        return detalleFacturas;
    }

    public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
        this.detalleFacturas = detalleFacturas;
    }

    public DetalleFactura getDetallefactura() {
        return detallefactura;
    }

    public void setDetallefactura(DetalleFactura detallefactura) {
        this.detallefactura = detallefactura;
    }

    public boolean isDisableAceptar() {
        return disableAceptar;
    }

    public void setDisableAceptar(boolean disableAceptar) {
        this.disableAceptar = disableAceptar;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public boolean isDesabilitarEnvio() {
        return desabilitarEnvio;
    }

    public void setDesabilitarEnvio(boolean desabilitarEnvio) {
        this.desabilitarEnvio = desabilitarEnvio;
    }

}
