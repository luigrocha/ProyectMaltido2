/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.servicio.EmpleadoServicio;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author Luig Rocha
 */

@ManagedBean
@ViewScoped
public class ReportCargasBean  extends AbstractReportBean implements Serializable {


    private static final String QRY = "SELECT" +
" EMPLEADO_001.\"APELLIDO\" AS EMPLEADO_001_APELLIDO," +
" EMPLEADO_001.\"NOMBRE\" AS EMPLEADO_001_NOMBRE," +
" EMPLEADO_001.\"ID_EMPLEADO\" AS EMPLEADO_001_ID_EMPLEADO," +
" MANTENIMIENTO_001.\"FECHA_INICIO\" AS MANTENIMIENTO_001_FECHA_INICIO," +
" MANTENIMIENTO_001.\"FECHA_FIN\" AS MANTENIMIENTO_001_FECHA_FIN," +
" MANTENIMIENTO_001.\"PRECIO\" AS MANTENIMIENTO_001_PRECIO," +
" CITA_MANTENIMIENTO_001.\"TIPO_MANTENIMIENTO\" AS CITA_MANTENIMIENTO_001_TIPO_MA," +
" CITA_MANTENIMIENTO_001.\"DESCRIPCION\" AS CITA_MANTENIMIENTO_001_DESCRIP," +
" CITA_MANTENIMIENTO_001.\"ID_CITA\" AS CITA_MANTENIMIENTO_001_ID_CITA," +
" CLIENTE_001.\"ID_CLIENTE\" AS CLIENTE_001_ID_CLIENTE," +
" CLIENTE_001.\"NOMBRE\" AS CLIENTE_001_NOMBRE," +
" CLIENTE_001.\"APELLIDO\" AS CLIENTE_001_APELLIDO," +
" CLIENTE_001.\"DIRECCION\" AS CLIENTE_001_DIRECCION," +
" CLIENTE_001.\"TELEFONO\" AS CLIENTE_001_TELEFONO " +
" FROM" +
"     \"PMALDITO2\".\"MANTENIMIENTO_001\" MANTENIMIENTO_001 INNER JOIN \"PMALDITO2\".\"EMPLEADO_001\" EMPLEADO_001 ON MANTENIMIENTO_001.\"ID_EMPLEADO\" = EMPLEADO_001.\"ID_EMPLEADO\"" +
"     INNER JOIN \"PMALDITO2\".\"CITA_MANTENIMIENTO_001\" CITA_MANTENIMIENTO_001 ON MANTENIMIENTO_001.\"ID_CITA\" = CITA_MANTENIMIENTO_001.\"ID_CITA\"" +
"     INNER JOIN \"PMALDITO2\".\"CLIENTE_001\" CLIENTE_001 ON CITA_MANTENIMIENTO_001.\"ID_CLIENTE\" = CLIENTE_001.\"ID_CLIENTE\"" +
" WHERE MANTENIMIENTO_001.\"FECHA_INICIO\" between to_date(";
    private static final String QRY1 = ",'yyyy/mm/dd') and to_date(";
    private static final String QRY2 = ",'yyyy/mm/dd') and MANTENIMIENTO_001.\"ID_EMPLEADO\" = ";
    
    private Date fechaInicio;
    private Date fechaFin;
    private String empleado;
    @EJB
    private EmpleadoServicio empleadoServicio;
    private List<Empleado> listadoEmpleados;

    @PostConstruct
    public void postConstructor() {
        this.listadoEmpleados = this.empleadoServicio.obtenerTodosEmpleados();
    }

    public void generateReport() {
        System.out.println("Fecha: " + fechaInicio + ", " + fechaFin + ", " + formato);
        String fecha1 = "'20160101'";
        String fecha2 = null;
        String emp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (fechaInicio != null) {
            fecha1 = "'" + sdf.format(this.fechaInicio) + "'";
        }
        if (fechaFin != null) {
            fecha2 = "'" + sdf.format(this.fechaFin) + "'";
        } else {
            fecha2 = "'" + sdf.format(new Date()) + "'";
        }
        if (empleado != null) {
            emp = "'" + this.empleado + "'";
        }
        

        String sql = QRY + fecha1 + QRY1 + fecha2 + QRY2 + emp;
        parameters.put("fecha", fecha1 + " fechaf " + fecha2 );
        super.generateReport("cDiarias", "cDiarias", sql);
            
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public EmpleadoServicio getEmpleadoServicio() {
        return empleadoServicio;
    }

    public void setEmpleadoServicio(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
    }

    public List<Empleado> getListadoEmpleados() {
        return listadoEmpleados;
    }

    public void setListadoEmpleados(List<Empleado> listadoEmpleados) {
        this.listadoEmpleados = listadoEmpleados;
    }
    
    
}
