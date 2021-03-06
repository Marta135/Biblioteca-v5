package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class LibroEscrito extends Libro {

	/*********ATRIBUTOS*********/
	
	private static final int PAGINAS_PARA_RECOMPENSA = 25;
	private static final float PUNTOS_PREMIO = 0.5f;
	private int numPaginas;
	
	

	/*******CONSTRUCTORES*******/
	/**
	 * Constructor con parámetros.
	 * @param titulo: Título del libro.
	 * @param autor: Autor del libro.
	 * @param numPaginas: Número de páginas del libro. 
	 */
	public LibroEscrito (String titulo, String autor, int numPaginas) throws NullPointerException, IllegalArgumentException {
		super(titulo, autor);
		setNumPaginas(numPaginas);
	}
	
	/**
	 * Constructor copia. 
	 * @param copiaLibro: Copia del objeto Libro.
	 */
	public LibroEscrito (LibroEscrito copiaLibro) throws NullPointerException, IllegalArgumentException {
		super(copiaLibro);
		setNumPaginas(copiaLibro.getNumPaginas());
	}
	
	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve el número de páginas del libro.
	 * @return numPaginas
	 */
	public int getNumPaginas() {
		return numPaginas;
	}
	
	/**
	 * Método que modifica el número de páginas del libro.
	 * @param numPaginas
	 */
	private void setNumPaginas(int numPaginas) {
		if (numPaginas <= 0) {
			throw new IllegalArgumentException("ERROR: El número de páginas debe ser mayor que cero.");
		}
		this.numPaginas = numPaginas;
	}
	
	/**
	 * Método que devuelve los puntos obtenidos.	
	 * @return puntos
	 */
	public float getPuntos() {
		return (numPaginas / PAGINAS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO;
	}
	
	
	
	/********OTROS MÉTODOS********/
	
	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Método para comparar dos libros.
	 * Dos libros son el mismo si tienen el mismo título y autor.
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * Método que muestra la información del libro:
	 * título, autor y número de páginas.
	 */
	@Override
	public String toString() {
		return String.format("título=%s, autor=%s, número de páginas=%s", titulo, autor, numPaginas);
	}

}
