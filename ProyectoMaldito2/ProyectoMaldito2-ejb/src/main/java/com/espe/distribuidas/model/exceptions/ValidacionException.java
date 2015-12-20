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
package com.espe.distribuidas.model.exceptions;

/**
 * Clase especial para validar exepciones de tipo RunTimeException.
 * @author R&R S.A.
 */
public class ValidacionException extends RuntimeException{
    /**
     * Constructor que despliega la exepcion predefinida.
     * @param message recibe el mensaje a ejecutar.
     */
  public ValidacionException(String message){
  super(message);
  }  
}
