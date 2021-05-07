package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public enum Curso {

	PRIMERO("1º ESO"), 
	SEGUNDO("2º ESO"), 
	TERCERO("3º ESO"), 
	CUARTO("4º ESO");
	
	private final String cadenaAMostrar;
	
	private Curso (String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	@Override
	public String toString() {
		return cadenaAMostrar;
	}
	
}
