/*
 * Gestorinc S.A.
 * Sistema: Gestor G5
 * Creado: 21-jul-2010 - 15:50:45
 * 
 * Los contenidos de este archivo son propiedad intelectual de Gestorinc S.A.
 * y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5".
 * 
 * Usted puede encontrar una copia de esta licencia en: 
 *   http://www.gestorinc.com
 * 
 * Copyright 2008-2010 Gestorinc S.A. Todos los derechos reservados.
 */
package com.persist.common.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;


/**
 * La Clase BaseDAO implementa las operaciones básicas de acceso a datos DAO
 * utilizando usado por las clases DAO del módulo de ejecución de transacciones.
 *
 * @author Gestorinc S.A.
 * @version $Rev $
 */
public class BaseDAO {

    /**
     * Constante que representa el character '%'.
     */
    public static final String SYMBOLO_LIKE = "%";
    
    /**
     * Constante que representa la cadena "'".
     */
    public static final String SYMBOLO_APOSTROFE = "'";
    
    /**
     * Creación del log de auditoría.
     */
    protected static final Logger LOGGER = Logger.getLogger(BaseDAO.class.getName());
    
    /**
     * Objeto que maneja las operaciones de persistencia.
     */
    @PersistenceContext(name = "punit")
    private EntityManager em;

    /**
     * Constructor por defecto.
     */
    public BaseDAO() {
    }

    /**
     * Retorna una referencia al objeto que maneja las operaciones de
     * persistencia definidas por JPA.
     *
     * @return Referencia al objeto que maneja las operaciones de persistencia.
     * En caso de que el objeto no este inicializado lanza la excepción
     * @see java.lang.IllegalStateException
     */
    protected EntityManager getEntityManager() {
        if (em == null) {
            throw new IllegalStateException(
                    "EntityManager no ha sido asignado a DAO antes del uso.");
        } else {
            return em;
        }
    }

    /**
     * Ejecuta una sentencia SQL obteniendo una conexión a la BD, referenciado
     * por la unidad de persistencia: <b>punit</b>.<br/>
     * No utilizar este método para ejecutar sentencias SELECT.
     * 
     * @param sentencia Sentencia SQL que será ejecutada.
     */
    public void ejecutarNativo(String sentencia) {
        try {
            java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
            PreparedStatement ps = connection.prepareStatement(sentencia);
            ps.execute();
            ps.close();
        } catch (PersistenceException e) {
            LOGGER.info("Error al ejecutar sentencia"+ e.getMessage());
        } catch (SQLException e) {
            LOGGER.info("Error al ejecutar sentencia"+ e.getMessage());
        }
    }

    /**
     * Pone apóstrofes a una cadena de caracteres.
     *
     * @param cadena la cadena
     * @return la cadena con apóstrofes
     */
    protected String comillar(String cadena) {
        return SYMBOLO_APOSTROFE + cadena + SYMBOLO_APOSTROFE;
    }
}
