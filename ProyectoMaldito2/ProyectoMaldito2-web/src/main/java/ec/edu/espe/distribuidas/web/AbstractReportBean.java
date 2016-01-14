/*
 * ROCHE ECUADOR
 * Sistema: SWP 1.0 - ROCHE
 * Creado: 14-feb-2013 - 00:10:46
 * 
 * Los contenidos de este archivo son propiedad intelectual de ROCHE ECUADOR
 * y se encuentran protegidos por leyes de propiedad intelectual.
 * 
 * No se puede hacer uso de los mismos sin el expreso consentimiento de ROCHE.
 * 
 * Copyright 2012-2013 Roche Ecuador Todos los derechos reservados.
 */
package ec.edu.espe.distribuidas.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Henry Coral
 */
public class AbstractReportBean {

    protected Connection conn;
    protected String formato;
    protected StreamedContent file;
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String cancelar() {
        return "inicio";
    }
    
    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
    
    public StreamedContent getFile() {
        return file;
    }

    protected void createConn() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PMALDITO2", "root");
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    protected void generateReportFactura(String name, String jasperName) {
        try {
            this.createConn();
            FacesContext ctx = FacesContext.getCurrentInstance();
            File reportFile = new File(ctx.getExternalContext().getRealPath("reports/" + jasperName + ".jasper"));
            JasperReport jr = (JasperReport) JRLoader.loadObject(reportFile);
                byte[] data = JasperRunManager.runReportToPdf(jr, parameters, conn);
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(data), "application/pdf", name + ".pdf");
        } catch (JRException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar conex");
            }
        }
    }
     public void pdf(String name, String jasperName){
        JasperReport jasperReport;
        JasperPrint jasperPrint;                
        this.createConn();
        try
        {
          //se carga el reporte
                      FacesContext ctx = FacesContext.getCurrentInstance();
  
          String  in=ctx.getExternalContext().getRealPath("reports/" + jasperName + ".jasper");
          jasperReport=(JasperReport)JRLoader.loadObject(in);
          //se procesa el archivo jasper
          jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), this.conn);
          //se crea el archivo PDF
          JasperExportManager.exportReportToPdfFile( jasperPrint, "C:\\Users\\Andres Vr\\Documents\\Git\\ProyectoMaldito7.0\\ProyectMaltido2\\ProyectoMaldito2\\ProyectoMaldito2-web\\src\\main\\webapp\\pdf\\"+name+".pdf");
        }
        catch (JRException ex)
        {
          System.err.println( "Error iReport: " + ex.getMessage() );
        }
  }
    protected void generateReport(String name, String jasperName) {
        try {
            this.createConn();
            FacesContext ctx = FacesContext.getCurrentInstance();
            File reportFile = new File(ctx.getExternalContext().getRealPath("reports/" + jasperName + ".jasper"));
            JasperReport jr = (JasperReport) JRLoader.loadObject(reportFile);
            if ("PDF".equals(formato)) {
                byte[] data = JasperRunManager.runReportToPdf(jr, parameters, conn);
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(data), "application/pdf", name + ".pdf");
            } else if ("XLS".equals(formato)) {
                JasperPrint print = JasperFillManager.fillReport(jr, parameters, conn);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(output.toByteArray()), "application/vnd.ms-excel", name + ".xls");

            } else if ("TXT".equals(formato)) {
                JasperPrint print = JasperFillManager.fillReport(jr, parameters, conn);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(6.55));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11.9));
                exporter.exportReport();
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(output.toByteArray()), "text/plain", name + ".txt");
            }
        } catch (JRException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar conex");
            }
        }
    }
    
    protected void generateReport(String name, String jasperName, String sql) {
        try {
            this.createConn();
            Statement st = this.conn.createStatement();
            System.out.println("SQL:"+sql);
            ResultSet rs = st.executeQuery(sql);
            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
            FacesContext ctx = FacesContext.getCurrentInstance();
            File reportFile = new File(ctx.getExternalContext().getRealPath("reports/" + jasperName + ".jasper"));
            JasperReport jr = (JasperReport) JRLoader.loadObject(reportFile);
            if ("PDF".equals(formato)) {
                byte[] data = JasperRunManager.runReportToPdf(jr, parameters, resultSetDataSource);
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(data), "application/pdf", name + ".pdf");
            } else if ("XLS".equals(formato)) {
                JasperPrint print = JasperFillManager.fillReport(jr, parameters, resultSetDataSource);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(output.toByteArray()), "application/vnd.ms-excel", name + ".xls");

            } else if ("TXT".equals(formato)) {
                JasperPrint print = JasperFillManager.fillReport(jr, parameters, resultSetDataSource);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(6.55));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11.9));
                exporter.exportReport();
                this.file = new DefaultStreamedContent(new ByteArrayInputStream(output.toByteArray()), "text/plain", name + ".txt");
            }
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (SQLException sex) {
            sex.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conex");
            }
        }
    }
}
