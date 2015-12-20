/*
 * R&R S.A.
 * Sistema: Spotlights&Wires
 * Creado: 05-Dec-2015 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de R&R S.A.
 *  
 *  
 * Copyright 2015 R&R S.A. Todos los derechos reservados.
 */
package com.espe.distribuidas.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase utilitaria que permite sincronizar el estado de un listado de objetos.
 * La sincronizaci�n utiliza dos listados. Uno que representa a los objetos que
 * se encuentran en la BD y otro que representa a los objetos de sincronizaci�n.
 *
 * @param <T> Clase correspondiente al modelo (Clases Hibernate) que especifica
 * el tipo de objeto con el cual se van a realizar las operaciones de acceso a
 * datos.
 * @param <ID> Tipo de la Clave Primaria de la clase modelo, esta clase debe
 * extender de java.io.Serializable
 *
 * @author R&R S.A.
 * @version $Revision: 6014 $
 */
public class ListOperationsDB<T, ID extends Serializable> {

    /**
     * DAO que ejecutar� las operaciones en la BD.
     */
    private GenericDAO<T, ID> dao;

    /**
     * Constructor por defecto.
     *
     * @param dao DAO que referencia a la entidad con la cual se va a realizar
     * las operaciones.
     * @param dsa Objeto que contiene la informaci�n de auditor�a de usuario.
     */
    public ListOperationsDB(GenericDAO<T, ID> dao) {
        this.dao = dao;
    }

    /**
     * TODO: GLOZADA se debe verificar el m�todo Procesa las operaciones de
     * inserci�n, actualizaci�n y eliminaci�n.
     *
     * @param actualList Lista de objetos que se encuentran en la BD.
     * @param newList Lista de objetos modificada en memoria.
     * @return n�mero de registros que se creo, actualizo y elimino
     */
    @SuppressWarnings("unchecked")
    public int process(List<T> actualList, List<T> newList) {
        int res = 0;
        Collection<T> toUpdateOriginal = CollectionUtils.intersection(actualList, newList);
        Collection<T> toUpdateNew = CollectionUtils.intersection(newList, actualList);
        Collection<T> delete = CollectionUtils.subtract(actualList, newList);
        for (T entity : delete) {
            dao.makeTransient(entity);
            res++;
        }
        Collection<T> insert = CollectionUtils.subtract(newList, actualList);
        for (T entity : insert) {
            dao.insert(entity);
            res++;
        }
        T[] originalL = (T[]) toUpdateOriginal.toArray();
        T[] newL = (T[]) toUpdateNew.toArray();
        for (int i = 0; i < originalL.length; i++) {
            boolean actualizar = false;
            try {
                Map<String, String> propertiesOriginal = BeanUtils.describe(originalL[i]);
                Map<String, String> propertiesNueva = BeanUtils.describe(newL[i]);
                Set<String> propiedades = propertiesOriginal.keySet();
                for (String string : propiedades) {
                    if (!(propertiesOriginal.get(string) == null && propertiesNueva.get(string) == null)
                            && !propertiesOriginal.get(string).equals(propertiesNueva.get(string))) {
                        actualizar = true;
                        break;
                    }
                }
            } catch (Exception e) {
                actualizar = true;
            }
            if (actualizar) {
                // se realiza un refresh para sincronizar la versi�n
                T object = this.dao.update(newL[i]);
                this.dao.flush();
                try {
                    BeanUtils.copyProperties(object, newL[i]);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ListOperationsDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(ListOperationsDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                res++;
            }
        }
        return res;
    }
}
