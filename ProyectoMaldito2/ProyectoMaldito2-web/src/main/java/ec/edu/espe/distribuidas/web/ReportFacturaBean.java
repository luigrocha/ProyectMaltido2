/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author Luig Rocha
 */

@ManagedBean
@ViewScoped
public class ReportFacturaBean  extends AbstractReportBean implements Serializable {


    public void generateReport() {
        //generar reporte
        super.generateReport("factura", "factura");
    }
    
}
