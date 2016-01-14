/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.web;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
/**
 *
 * @author Luig Rocha
 */

@ManagedBean
@ViewScoped
public class ReportFacturaBean  extends AbstractReportBean implements Serializable {


    public void generateReport() {
        //generar reporte
        super.pdf("factura", "factura");
        try {
            this.testMultiPartEmail();
        } catch (UnsupportedEncodingException | EmailException | MalformedURLException ex) {
            Logger.getLogger(ReportFacturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void testMultiPartEmail() throws UnsupportedEncodingException, EmailException, MalformedURLException {

        EmailAttachment att2 = new EmailAttachment();
        att2.setPath("C:\\Users\\Andres Vr\\Documents\\Git\\ProyectoMaldito7.0\\ProyectMaltido2\\ProyectoMaldito2\\ProyectoMaldito2-web\\src\\main\\webapp\\pdf\\factura.pdf");
        att2.setDisposition(EmailAttachment.ATTACHMENT);
        att2.setDescription("Envio Factura Mantenimiento SpotLinght&Wires");
        

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setSSLOnConnect(true);
        email.setAuthentication("spotwires@gmail.com", "084383260a");
    

        email.addTo("avrz237@gmail.com");
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

    
}
