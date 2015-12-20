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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.persistence.*;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author R&R S.A.
 */
public class QueryBuilder<T> {
    
    private static final String SELECT = "SELECT obj FROM ";
    private static final String SELECT_COUNT = "SELECT COUNT(obj) FROM ";
    private static final String FROM = " obj ";
    private static final String WHERE = "WHERE ";
    private static final String OBJ = "obj.";
    private static final String EQ = " = ?";
    private static final String LIKE = " LIKE ?";
    private static final String AND = " AND ";
    private static final String ORDER = " ORDER BY ";
    private static final String LOWER = " LOWER(";
    private static final String ASC = " ASC, ";
    private static final String DESC = " DESC, ";
    
    /**
     * Objeto que maneja las operaciones de persistencia.
     */
    private EntityManager em;
    
    public QueryBuilder(EntityManager em) {
        this.em = em;
    }
    
    public Query buildQuery(Integer type, T example, String... orden) {
        StringBuilder qry;
        List<String> ids = new ArrayList<String>();
        if (type==1) {
            qry = new StringBuilder(SELECT_COUNT);
            qry.append(example.getClass().getSimpleName());
        } else {
            qry = new StringBuilder(SELECT);
            qry.append(example.getClass().getSimpleName());
            ids = this.obtenerId(example);
        }
        qry.append(FROM);
        qry.append(WHERE);
        List<Object> parametros = new ArrayList();
        Criterio criterio = new Criterio();
        Map<String, Object> propiedades = this.obtenerPropiedades(example);
        criterio.contruccion(propiedades);
        Set<String> igualdad = criterio.igualdad.keySet();
        Integer idx = 1;
        for (String key : igualdad) {
            if (idx>1) {
                qry.append(AND);
            }
            qry.append(OBJ);
            qry.append(key);
            qry.append(EQ);
            qry.append(idx);
            parametros.add(criterio.igualdad.get(key));
            idx++;
        }
        
        Set<String> likeKeys = criterio.like.keySet();
        for (String key : likeKeys) {
            if (idx>1) {
                qry.append(AND);
            }
            Object valor = criterio.like.get(key);
            if (valor instanceof String) {
                qry.append(LOWER);
                qry.append(OBJ);
                qry.append(key);
                qry.append(")");
                qry.append(LIKE);
                qry.append(idx);
                parametros.add(((String) valor).toLowerCase());
            } else {
                qry.append(OBJ);
                qry.append(key);
                qry.append(LIKE);
                qry.append(idx);
                parametros.add(valor);
            }
            idx++;
        }
        
        Set<String> compositeKeys = criterio.compuesto.keySet();
        for (String key : compositeKeys) {
            Object value = criterio.compuesto.get(key);
            try {
                if (value.toString().startsWith("class java.util")) {
                    continue;
                } else if (StringUtils.containsIgnoreCase(key, "pk")) {
                    Map<String, Object> propsComposites = this
                                    .obtenerPropiedades(value, key);
                    Criterio criterioCompuesto = new Criterio();
                    criterioCompuesto.contruccion(propsComposites);
                    if (!criterioCompuesto.igualdad.isEmpty()) {
                        Set<String> eqKeysPK = criterioCompuesto.igualdad.keySet();
                        for (String keyPK : eqKeysPK) {
                            if (idx>1) {
                                qry.append(AND);
                            }
                            qry.append(OBJ);
                            qry.append(keyPK);
                            qry.append(EQ);
                            qry.append(idx);
                            parametros.add(criterioCompuesto.igualdad.get(keyPK));
                            idx++;
                        }
                    }
                    if (!criterioCompuesto.like.isEmpty()) {
                        Set<String> likeKeysPK = criterioCompuesto.like.keySet();
                        for (String keyPK : likeKeysPK) {
                            if (idx>1) {
                                qry.append(AND);
                            }
                            Object valor = criterioCompuesto.like.get(keyPK);
                            if (valor instanceof String) {
                                qry.append(LOWER);
                                qry.append(OBJ);
                                qry.append(keyPK);
                                qry.append(")");
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(((String) valor).toLowerCase());
                            } else {
                                qry.append(OBJ);
                                qry.append(keyPK);
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(valor);
                            }
                            idx++;
                        }
                    }
                } else {
                    Map<String, Object> propsComposites = this
                                    .obtenerPropiedades(value);
                    Criterio criterioCompuesto = new Criterio();
                    criterioCompuesto.contruccion(propsComposites);
                    if(!criterioCompuesto.igualdad.isEmpty()){
                        Set<String> eqKeysPK = criterioCompuesto.igualdad.keySet();
                        for (String keyPK : eqKeysPK) {
                            if (idx>1) {
                                qry.append(AND);
                            }
                            qry.append(OBJ);
                            qry.append(key);
                            qry.append(".");
                            qry.append(keyPK);
                            qry.append(EQ);
                            qry.append(idx);
                            parametros.add(criterioCompuesto.igualdad.get(keyPK));
                            idx++;
                        }
                    }
                    if (!criterioCompuesto.like.isEmpty()) {
                        Set<String> likeKeysPK = criterioCompuesto.like.keySet();
                        for (String keyPK : likeKeysPK) {
                            System.out.println("Compuesto LIKE: "+keyPK+", "+criterioCompuesto.igualdad.get(keyPK));
                            if (idx>1) {
                                qry.append(AND);
                            }
                            Object valor = criterioCompuesto.like.get(keyPK);
                            if (valor instanceof String) {
                                qry.append(LOWER);
                                qry.append(OBJ);
                                qry.append(key);
                                qry.append(".");
                                qry.append(keyPK);
                                qry.append(")");
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(((String) valor).toLowerCase());
                            } else {
                                qry.append(OBJ);
                                qry.append(key);
                                qry.append(".");
                                qry.append(keyPK);
                                qry.append(LIKE);
                                qry.append(idx);
                                parametros.add(valor);
                            }
                            idx++;
                        }
                    }
                    		
                }
            } catch (RuntimeException e) {
                    continue;
            }
        }

        if (idx==1) {
            qry.append(" 1=1");
        }
        if (!ids.isEmpty()) {
            qry.append(ORDER);
        } else {
            qry.append("  ");
        }
        if (orden.length>0) {
            for (String ord:orden) {
                qry.append(OBJ);
                qry.append(ord.substring(2));
                if (ord.startsWith("A,")) {
                    qry.append(ASC);
                } else if (ord.startsWith("D,")) {
                    qry.append(DESC);
                }
            }
        } else {
            for (String id : ids) {
                if (!id.contains("_persistence")) {
                    qry.append(OBJ);
                    qry.append(id);
                    qry.append(ASC);
                }
            }
        }
        
        System.out.println(qry.substring(0, qry.length()-2));
        Query query = this.em.createQuery(qry.substring(0, qry.length()-2));
        if (!parametros.isEmpty()) {
            int i=1;
            for (Object obj : parametros) {
                query.setParameter(i, obj);
                i++;
            }
        }
        return query;
    }
    

    /**
     * Obtiene las propiedades de la entidad a la cual pertenece el DAO.
     *
     * @param object entidad a la que pertenece el DAO.
     * @param clave la entidad embebida que contiene la clave primaria
     *
     * @return Mapa con las propiedades y los valores, el cual será utilizado
     * para la construcción de los criterios en la ejecución de la búsqueda.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> obtenerPropiedades(Object object, String... clave) {
        Class<T> classe = (Class<T>) object.getClass();
        Field[] fields = classe.getDeclaredFields();
        Map<String, Object> mapa = new HashMap<String, Object>(fields.length);
        for (Field field : fields) {
            Boolean isClob = Boolean.FALSE;
            Annotation[] anotaciones = field.getAnnotations();
            for (Annotation annotation : anotaciones) {
                if ("@javax.persistence.Lob()".equals(annotation.toString())
                        || "@javax.persistence.Transient()".equals(annotation.toString())) {
                    isClob = Boolean.TRUE;
                    break;
                }
            }
            if (!isClob) {
                String fieldName = field.getName();
                String methodName = "get"
                        + Character.toUpperCase(fieldName.charAt(0))
                        + fieldName.substring(1);
                try {
                    Method method = classe.getMethod(methodName, new Class[]{});
                    Object result = method.invoke(object, new Object[]{});
                    
                    if (result != null) {
                        if (clave != null && clave.length == 1) {
                            mapa.put(clave[0] + "." + field.getName(), result);
                        } else {
                            mapa.put(field.getName(), result);
                        }
                    }
                } catch (RuntimeException e) {
                    continue;
                } catch (NoSuchMethodException e) {
                    continue;
                } catch (IllegalAccessException e) {
                    continue;
                } catch (InvocationTargetException e) {
                    continue;
                }
            }
        }
        return mapa;
    }

    /**
     * Obtiene las columnas de la clave primaria de la entidad.
     *
     * @param object Entidad de donde se obtendrán las columnas clave
     * @return Retorna una lista con las columnas de la clave primaria de la
     * entidad
     */
    @SuppressWarnings("unchecked")
    protected List<String> obtenerId(Object object) {
        Class<T> classe = (Class<T>) object.getClass();
        Field[] fields = classe.getDeclaredFields();
        List<String> id = new ArrayList<String>();
        for (Field field : fields) {
            Annotation[] anotaciones = field.getAnnotations();
            for (Annotation annotation : anotaciones) {
                if ("@javax.persistence.Id()".equals(annotation.toString())) {
                    id.add(field.getName());
                    break;
                } else if ("@javax.persistence.EmbeddedId()".equals(annotation.toString())) {
                    String fieldName = field.getName();
                    String methodName = "get"
                            + Character.toUpperCase(fieldName.charAt(0))
                            + fieldName.substring(1);
                    try {
                        Method method = classe.getMethod(methodName, new Class[]{});
                        Object result = method.invoke(object, new Object[]{});
                        Class<T> classe1 = (Class<T>) result.getClass();
                        Field[] fields1 = classe1.getDeclaredFields();
                        for (Field fieldPK : fields1) {
                            if (!"serialVersionUID".equals(fieldPK.getName())) {
                                id.add(fieldName + "." + fieldPK.getName());
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    } catch (NoSuchMethodException e) {
                        continue;
                    } catch (IllegalAccessException e) {
                        continue;
                    } catch (InvocationTargetException e) {
                        continue;
                    }
                }
            }
        }
        return id;
    }

    /**
     * Clase Interna que es utilizada como estructura de datos y ayuda en la
     * construcción de las condiciones para la búsqueda.
     */
    private class Criterio {

        /**
         * Especifica las condiciones de igualdad.
         */
        private final Map<String, Object> igualdad = new HashMap<String, Object>();
        /**
         * Especifica las condiciones like.
         */
        private final Map<String, Object> like = new HashMap<String, Object>();
        /**
         * Especifica las condiciones con entidades compuestas.
         */
        private final Map<String, Object> compuesto = new HashMap<String, Object>();

        /**
         * Método que inspecciona las propiedades de una entidad y los valores
         * asignados a cada propiedad para la construcción de los criterios a
         * ser aplicados al momento de ejecutar una búsqueda.
         *
         * @param propiedades Mapa de propiedades de una entidad con los valores
         * que serán utilizados como criterios para la ejecución de una
         * búsqueda.
         */
        public void contruccion(Map<String, Object> propiedades) {
            Set<String> keys = propiedades.keySet();
            for (String key : keys) {
                Object value = propiedades.get(key);
                if (value.getClass().toString().startsWith("class java.sql")) {
                    continue;
                } else if (value.getClass().toString().startsWith("class java.lang")) {
                    if (value.toString().indexOf('%') >= 0) {
                        like.put(key, value);
                    } else if (value.toString().length() > 0) {
                        igualdad.put(key, value);
                    }
                } else if (value.getClass().toString().startsWith("class java.util.Date")
                        && value instanceof Date) {
                    // Fechas solo puede realizar igualdad
                    igualdad.put(key, value);
                } else {
                    compuesto.put(key, value);
                }
            }
        }
    }
      
}
