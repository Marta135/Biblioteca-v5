package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public enum Curso {

	PRIMERO("1º ESO"), 
	SEGUNDO("2º ESO"), 
	TERCERO("3º ESO"), 
	CUARTO("4º ESO");
	
	/**
	 * Atributo:
	 */
	private final String cadenaAMostrar;
	
	/**
	 * Constructor:
	 * @param cadenaAMostrar: Texto a mostrar.
	 */
	private Curso (String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	/**
	 * Método toString:
	 * Devolverá el valor almacenado en la variable cadenaAMostrar. 
	 */
	@Override
	public String toString() {
		return cadenaAMostrar;
	}
	
}
