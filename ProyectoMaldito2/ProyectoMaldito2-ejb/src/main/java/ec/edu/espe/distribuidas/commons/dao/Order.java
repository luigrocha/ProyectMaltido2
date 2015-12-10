/*
 * Gestorinc S.A.
 * Sistema: Gestor G5
 * Creado: 27-may-2009 - 14:14:39
 * 
 * Los contenidos de este archivo son propiedad intelectual de Gestorinc S.A.
 * y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5".
 * 
 * Usted puede encontrar una copia de esta licencia en: 
 *   http://www.gestorinc.com
 * 
 * Copyright 2008-2010 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.espe.distribuidas.commons.dao;

/**
 * Clase de utilidad que define el ordenamiento de consultas.
 * 
 * @author Gestorinc S.A.
 * @version $Revision:$
 * 
 */
public final class Order {

	/**
	 * Constante utilizada para definir ordenamiento ascendente.
	 */
	public static final String ASC = "A,";
	/**
	 * Constante utilizada para definir ordenamiento descendente.
	 */
	public static final String DESC = "D,";
	
	/**
	 * Constructor por defecto.
	 */
	private Order() {
		
	}
	
	/**
	 * Retorna un objeto que especifica que la propiedad recibida como 
	 * par�metro ser� tomada para ordenamiento ascendente.
	 * 
	 * @param propiedad Propiedad de la entidad que ser� utilizada para el ordenamiento.
	 * @return Cadena que especifica el ordenamiento.
	 */
	public static String ascendente(String propiedad) {
		return ASC+propiedad;
	}
	
	/**
	 * Retorna un objeto que especifica que la propiedad recibida como 
	 * par�metro ser� tomada para ordenamiento descendente.
	 * 
	 * @param propiedad Propiedad de la entidad que ser� utilizada para el ordenamiento.
	 * @return Cadena que especifica el ordenamiento.
	 */
	public static String descendente(String propiedad) {
		return DESC+propiedad;
	}
}
