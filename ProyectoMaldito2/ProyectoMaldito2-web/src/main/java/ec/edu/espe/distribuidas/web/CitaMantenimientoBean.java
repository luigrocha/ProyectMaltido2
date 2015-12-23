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

import com.espe.distribuidas.model.Cliente;
import com.espe.distribuidas.servicio.ClienteServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andres Vr
 */
 @ViewScoped
@ManagedBean

public class CitaMantenimientoBean implements Serializable {
    
    @EJB
    private ClienteServicio clienteServicio;
    
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("idCliente", "nombre", "apellido", "telefono");
     
    private String columnTemplate = "nombre apellido";
     
    private List<ColumnModel> columns;
     
    private List<Cliente> clientelista;
     
    private List<Cliente> filteredCliente;
     

    @PostConstruct
    public void init() {
         clientelista=clienteServicio.obtenerTodosClientes();
        createDynamicColumns();
    }

    public ClienteServicio getClienteServicio() {
        return clienteServicio;
    }

    public void setClienteServicio(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }

    public List<Cliente> getClientelista() {
        return clientelista;
    }

    public void setClientelista(List<Cliente> clientelista) {
        this.clientelista = clientelista;
    }


    public List<Cliente> getFilteredCliente() {
        return filteredCliente;
    }

    public void setFilteredCliente(List<Cliente> filteredCliente) {
        this.filteredCliente = filteredCliente;
    }
     

 
    public String getColumnTemplate() {
        return columnTemplate;
    }
 
    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }
 
    public List<ColumnModel> getColumns() {
        return columns;
    }
 
    private void createDynamicColumns() {
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<>();   
         
        for(String columnKey : columnKeys) {
            String key = columnKey.trim();
             
            if(VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }
     
    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cliente");
        table.setValueExpression("sortBy", null);
         
        //update columns
        createDynamicColumns();
    }
     
   public class ColumnModel implements Serializable {
 
        private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }
}
