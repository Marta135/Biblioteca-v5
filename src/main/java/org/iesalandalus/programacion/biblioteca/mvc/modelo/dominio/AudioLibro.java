package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class AudioLibro extends Libro {

	/*********ATRIBUTOS*********/
	
	private static final int MINUTOS_PARA_RECOMPENSA = 15;
	private static final float PUNTOS_PREMIO = 0.25f;
	private int duracion;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor con parámetros.
	 * @param titulo
	 * @param autor
	 * @param duracion
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public AudioLibro (String titulo, String autor, int duracion) throws NullPointerException, IllegalArgumentException {
		super(titulo, autor);
		setDuracion(duracion);
	}
	
	/**
	 * Constructor copia. 
	 * @param copiaLibro
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public AudioLibro (AudioLibro copiaLibro) throws NullPointerException, IllegalArgumentException {
		super(copiaLibro);
		setDuracion(copiaLibro.getDuracion());
	}

	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve la duración del audiolibro.
	 * @return duracion
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Método que modifica la duración del audiolibro.
	 * @param duracion
	 */
	private void setDuracion(int duracion) {
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración debe ser mayor que cero.");
		}
		this.duracion = duracion;
	}
	
	/**
	 * Método que devuelve los puntos obtenidos.	
	 * @return puntos
	 */
	public float getPuntos() {
		return (duracion / MINUTOS_PARA_RECOMPENSA + 1) * PUNTOS_PREMIO;
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
		return String.format("título=%s, autor=%s, duración=%s", titulo, autor, duracion);
	}

}
