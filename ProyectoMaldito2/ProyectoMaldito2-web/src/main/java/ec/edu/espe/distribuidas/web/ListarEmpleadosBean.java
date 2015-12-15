/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;

import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Andres Vr
 */
@ViewScoped
@ManagedBean
public class ListarEmpleadosBean {

    @EJB
    private EmpleadoServicio empleado;
    private List<Empleado> listaEmpleados;

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    @PostConstruct
    public void inicializar() {
        listaEmpleados = empleado.obtenerTodosEmpleados();
    }

}
