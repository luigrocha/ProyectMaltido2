/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.dao;

import com.espe.distribuidas.model.Pedido;
import ec.edu.espe.distribuidas.commons.dao.DefaultGenericDAOImple;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class PedidoDAO extends DefaultGenericDAOImple<Pedido, String> {
     public PedidoDAO() {
        super(Pedido.class);
    }
    
}
