package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public abstract class Libro {

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
	public Libro (Libro copiaLibro) throws NullPointerException, IllegalArgumentException {
		if (copiaLibro == null) {
			throw new NullPointerException("ERROR: No es posible copiar un libro nulo.");
		}
		setTitulo(copiaLibro.getTitulo());
		setAutor(copiaLibro.getAutor());
	}
	
	/**
	 * Método que devolverá un libro ficticio para usarlo en búsquedas y borrados.
	 * @param titulo
	 * @param autor
	 * @return título, autor y número de páginas. 
	 */
	public static Libro getLibroFicticio(String titulo, String autor) throws NullPointerException, IllegalArgumentException {
		return new LibroEscrito(titulo, autor, 100);
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
		if (titulo.trim().equals("")) {
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
		if (autor.trim().equals("")) {
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	/**
	 * Método para comparar dos libros.
	 * Dos libros son el mismo si tienen el mismo título y autor.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
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
