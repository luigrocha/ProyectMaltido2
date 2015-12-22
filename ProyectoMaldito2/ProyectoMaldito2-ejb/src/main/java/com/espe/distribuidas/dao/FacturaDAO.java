/*
 * R&R S.A.
 * Sistema: Spotlights&Wires.
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *   
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package com.espe.distribuidas.dao;

import com.espe.distribuidas.model.Factura;
import com.espe.distribuidas.commons.dao.DefaultGenericDAOImple;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase DAO de la entidad FACTURA.
 * @author R&R S.A.
 */
@LocalBean
@Stateless 
public class FacturaDAO extends DefaultGenericDAOImple<Factura, String> {
     public FacturaDAO() {
        super(Factura.class);
    }
    
}
