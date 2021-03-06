package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public abstract class Libro implements Serializable {

	/*********ATRIBUTOS*********/
	
	protected String titulo;
	protected String autor;
	
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor con parámetros.
	 * @param titulo: Título del libro.
	 * @param autor: Autor del libro.
	 */
	public Libro (String titulo, String autor) throws NullPointerException, IllegalArgumentException {
		setTitulo(titulo);
		setAutor(autor);
	}
	
	/**
	 * Constructor copia. 
	 * @param copiaLibro: Copia del objeto Libro.
	 */
	public Libro (Libro copiaLibro) {
		if (copiaLibro == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		titulo = copiaLibro.getTitulo();
		autor = copiaLibro.getAutor();
	}
	
	/**
	 * Método que devolverá un libro ficticio para usarlo en búsquedas y borrados.
	 * @param titulo
	 * @param autor
	 * @return título, autor y número de páginas. 
	 */
	public static Libro getLibroFicticio(String titulo, String autor) throws NullPointerException, IllegalArgumentException {
		if (titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		return new LibroEscrito(titulo, autor, 10);
	}
	
	/**
	 * Método que devuelve los puntos obtenidos.	
	 * @return puntos
	 */
	public abstract float getPuntos();
	
	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve el título del libro.
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Método que modifica el título del libro.
	 * @param titulo
	 */
	protected void setTitulo(String titulo) {
		if(titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}
	
	/**
	 * Método que devuelve el autor del libro.
	 * @return autor
	 */
	public String getAutor() {
		return autor;
	}
	
	/**
	 * Método que modifica el autor del libro.
	 * @param autor
	 */
	protected void setAutor(String autor) {
		if(autor == null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		this.autor = autor;
	}
	
	
	
	/********OTROS MÉTODOS********/
	
	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(autor, titulo);
	}

	/**
	 * Método para comparar dos libros.
	 * Dos libros son el mismo si tienen el mismo título y autor.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Libro)) {
			return false;
		}
		Libro other = (Libro) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(titulo, other.titulo);
	}

	/**
	 * Método que muestra la información del libro:
	 * título, autor y número de páginas.
	 */
	@Override
	public String toString() {
		return String.format("título=%s, autor=%s", titulo, autor);
	}

}
