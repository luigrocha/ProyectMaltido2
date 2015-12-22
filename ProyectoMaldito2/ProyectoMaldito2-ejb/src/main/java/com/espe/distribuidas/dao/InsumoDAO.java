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

import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.commons.dao.DefaultGenericDAOImple;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Clase DAO de la entidad INSUMO.
 * @author R&R S.A.
 */
@LocalBean
@Stateless 
public class InsumoDAO  extends DefaultGenericDAOImple<Insumos, String> {
     public InsumoDAO() {
        super(Insumos.class);
    }
    
}
