package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public abstract class Libro implements Serializable {
	
	protected String titulo;
	protected String autor;
	
	public Libro(String titulo, String autor) {
		setTitulo(titulo);
		setAutor(autor);
	}
	
	public Libro(Libro copiaLibro) {
		if (copiaLibro==null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setTitulo(copiaLibro.titulo);
		setAutor(copiaLibro.autor);
	}
	
	public static Libro getLibroFicticio(String titulo, String autor) {
		return new LibroEscrito(titulo, autor, 10);
	}

	public abstract float getPuntos();
	
	public abstract String getNombreClase();
	
	public String getTitulo() {
		return titulo;
	}

	protected void setTitulo(String titulo) {
		if (titulo==null) {
			throw new NullPointerException("ERROR: El título no puede ser nulo.");
		}
		if (titulo.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El título no puede estar vacío.");
		}
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	protected void setAutor(String autor) {
		if (autor==null) {
			throw new NullPointerException("ERROR: El autor no puede ser nulo.");
		}
		if (autor.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El autor no puede estar vacío.");
		}
		this.autor = autor;
	}

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
