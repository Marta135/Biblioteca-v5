package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public abstract class Libro implements Serializable {

	/*********ATRIBUTOS*********/
	
	private static final long serialVersionUID = 1L;
	protected String titulo;
	protected String autor;
	
	
	
	/*******CONSTRUCTORES*******/
	
	public Libro (String titulo, String autor) throws NullPointerException, IllegalArgumentException {
		setTitulo(titulo);
		setAutor(autor);
	}
	
	public Libro (Libro copiaLibro) {
		if (copiaLibro == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		titulo = copiaLibro.getTitulo();
		autor = copiaLibro.getAutor();
	}
	
	public static Libro getLibroFicticio(String titulo, String autor) throws NullPointerException, IllegalArgumentException {
		return new LibroEscrito(titulo, autor, 100);
	}
	
	public abstract float getPuntos();
	
	
	/*********GETTERS Y SETTERS**********/
	
	public String getTitulo() {
		return titulo;
	}
	
	protected void setTitulo(String titulo) {
		if(titulo == null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
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
	
	@Override
	public int hashCode() {
		return Objects.hash(autor, titulo);
	}

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

	@Override
	public String toString() {
		return String.format("título=%s, autor=%s", titulo, autor);
	}

}
