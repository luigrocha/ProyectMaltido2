/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.dao;

import com.espe.distribuidas.model.DetalleDevolucion;
import com.espe.distribuidas.model.DetalleFactura;
import com.espe.distribuidas.model.DetalleFacturaPK;
import ec.edu.espe.distribuidas.commons.dao.DefaultGenericDAOImple;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless 
public class DetalleFacturaDAO extends DefaultGenericDAOImple<DetalleFactura, DetalleFacturaPK> {
     public DetalleFacturaDAO() {
        super(DetalleFactura.class);
    }
    
}
