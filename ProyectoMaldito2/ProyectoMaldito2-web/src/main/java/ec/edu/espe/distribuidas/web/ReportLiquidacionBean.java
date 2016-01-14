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
public class ReportLiquidacionBean  extends AbstractReportBean implements Serializable {


    private static final String QRY = "SELECT " +
"     LIQUIDACION_ASIGNACION_001.\"ID_LIQUIDACION\" AS LIQUIDACION_ASIGNACION_001_ID_," +
"     LIQUIDACION_ASIGNACION_001.\"FECHA\" AS LIQUIDACION_ASIGNACION_001_FEC," +
"     LIQUIDACION_ASIGNACION_001.\"CANTIDAD\" AS LIQUIDACION_ASIGNACION_001_CAN," +
"     LIQUIDACION_ASIGNACION_001.\"UNIDAD_MEDIDA\" AS LIQUIDACION_ASIGNACION_001_UNI," +
"     LIQUIDACION_ASIGNACION_001.\"TOTAL_LIQUIDACION\" AS LIQUIDACION_ASIGNACION_001_TOT," +
"     EMPLEADO_001.\"NOMBRE\" AS EMPLEADO_001_NOMBRE," +
"     EMPLEADO_001.\"APELLIDO\" AS EMPLEADO_001_APELLIDO," +
"     INSUMOS_001.\"NOMBRE\" AS INSUMOS_001_NOMBRE," +
"     INSUMOS_001.\"CANTIDAD\" AS INSUMOS_001_CANTIDAD," +
"     INSUMOS_001.\"TIPO_INSUMO\" AS INSUMOS_001_TIPO_INSUMO," +
"     INSUMOS_001.\"UNIDAD_MEDIDA\" AS INSUMOS_001_UNIDAD_MEDIDA," +
"     MANTENIMIENTO_001.\"ID_CITA\" AS MANTENIMIENTO_001_ID_CITA," +
"     MANTENIMIENTO_001.\"ID_EMPLEADO\" AS MANTENIMIENTO_001_ID_EMPLEADO," +
"     MANTENIMIENTO_001.\"FECHA_INICIO\" AS MANTENIMIENTO_001_FECHA_INICIO," +
"     MANTENIMIENTO_001.\"FECHA_FIN\" AS MANTENIMIENTO_001_FECHA_FIN," +
"     MANTENIMIENTO_001.\"PRECIO\" AS MANTENIMIENTO_001_PRECIO" +
" FROM" +
"     \"PMALDITO2\".\"LIQUIDACION_ASIGNACION_001\" LIQUIDACION_ASIGNACION_001 INNER JOIN \"PMALDITO2\".\"INSUMOS_001\" INSUMOS_001 ON LIQUIDACION_ASIGNACION_001.\"ID_INSUMO\" = INSUMOS_001.\"ID_INSUMO\"" +
"     INNER JOIN \"PMALDITO2\".\"MANTENIMIENTO_001\" MANTENIMIENTO_001 ON LIQUIDACION_ASIGNACION_001.\"ID_CITA\" = MANTENIMIENTO_001.\"ID_CITA\"" +
"     AND LIQUIDACION_ASIGNACION_001.\"ID_EMPLEADO\" = MANTENIMIENTO_001.\"ID_EMPLEADO\"" +
"     INNER JOIN \"PMALDITO2\".\"EMPLEADO_001\" EMPLEADO_001 ON MANTENIMIENTO_001.\"ID_EMPLEADO\" = EMPLEADO_001.\"ID_EMPLEADO\"" +
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
