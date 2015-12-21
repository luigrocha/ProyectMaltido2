/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.servicio;

import com.espe.distribuidas.dao.InsumoDAO;
import com.espe.distribuidas.model.Empleado;
import com.espe.distribuidas.model.Insumos;
import com.espe.distribuidas.model.exceptions.ValidacionException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andres Vr
 */
@LocalBean
@Stateless
public class InsumoServicio {
    @EJB
    private InsumoDAO insumoDAO;

    /**
     * Permite obtener todos los insumos de la base de datos (referente
     * unicamente a la tabla insumos).
     *
     * @return parametro tipo lista de insumos.
     */
    public List<Insumos> obtenerTodosInsumos() {
        return this.insumoDAO.findAll();
    }
    

    /**
     * Permite realizar una busqueda para encontrar un insumo por ID de
     * insumo.
     *
     * @param codigoInsumo  parametro tipo String que define el Codigo de
     * insumo buscar.
     * @return retorna el objeto cliente de la base de datos.
     */
    public Insumos obtenerInsumoPorID(String codigoInsumo) {
        return this.insumoDAO.findById(codigoInsumo, false);
    }

    /**
     * Permite ingresar nuevos insumos a la base de datos.
     *
     * @param insumo  recibe un objeto empleado a insertar.
     * @throws ValidacionException retorna una exepcion predefinida, indicando
     * que el insumo ya existe.
     */
    public void ingresarInsumo(Insumos insumo) throws ValidacionException {
        Insumos insumotmp = this.obtenerInsumoPorID(insumo.getIdInsumo());
        if (insumotmp == null) {
            this.insumoDAO.insert(insumo);
        } else {
            throw new ValidacionException("El codigo" + insumo.getIdInsumo()+ "ya existe");
        }
    }

    /**
     * Permite actualizar insumos de la base de datos.
     *
     * @param insumo  recibe un objeto insumo a actualizar.
     */
    public void actualizarInsumo(Insumos insumo) {
        this.insumoDAO.update(insumo);
    }

    /**
     * Permite eliminar insumos de la base de datos.
     *
     * @param idinsumo  recibe un id de insumo a eliminar.
     */
    public void eliminarInsumo(String idinsumo) {

        try {
            Insumos insumostmp = this.obtenerInsumoPorID(idinsumo);
            this.insumoDAO.remove(insumostmp);
        } catch (Exception e) {
            throw new ValidacionException("El insumo " + idinsumo + " esta asociada");
        }
    }

}
