package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public class LibroEscrito extends Libro {

	/*********ATRIBUTOS*********/
	
	private static final long serialVersionUID = 1L;
	private static final int PAGINAS_PARA_RECOMPENSA = 25;
	private static final float PUNTOS_PREMIO = 0.5f;
	private int numPaginas;
	

	/*******CONSTRUCTORES*******/
	
	public LibroEscrito (String titulo, String autor, int numPaginas) throws NullPointerException, IllegalArgumentException {
		super(titulo, autor);
		setNumPaginas(numPaginas);
	}
	
	public LibroEscrito (LibroEscrito copiaLibro) throws NullPointerException, IllegalArgumentException {
		super(copiaLibro);
		numPaginas = copiaLibro.getNumPaginas();
	}
	
	
	/*********GETTERS Y SETTERS**********/
	
	public int getNumPaginas() {
		return numPaginas;
	}
	
	private void setNumPaginas(int numPaginas) {
		if (numPaginas <= 0) {
			throw new IllegalArgumentException("ERROR: El número de páginas debe ser mayor que cero.");
		}
		this.numPaginas = numPaginas;
	}
	
	public float getPuntos() {
		return ((numPaginas / PAGINAS_PARA_RECOMPENSA) + 1) * PUNTOS_PREMIO;
	}
	
	
	/********OTROS MÉTODOS********/
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return String.format("%s, número de páginas=%s", super.toString(), numPaginas);
	}

	@Override
	public String getNombreClase() {
		return "Libro Escrito";
	}

}
