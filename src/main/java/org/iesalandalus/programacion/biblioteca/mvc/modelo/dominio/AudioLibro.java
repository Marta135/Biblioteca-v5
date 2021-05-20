package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

/**
 * 
 * @author Marta García
 * versión: 3v
 *
 */

public class AudioLibro extends Libro {

	
	/*********ATRIBUTOS*********/
	
	private static final long serialVersionUID = 1L;
	private static final int MINUTOS_PARA_RECOMPENSA = 15;
	private static final float PUNTOS_PREMIO = 0.25f;
	private int duracion;
	
	
	/*******CONSTRUCTORES*******/
	
	public AudioLibro (String titulo, String autor, int duracion) throws NullPointerException, IllegalArgumentException {
		super(titulo, autor);
		setDuracion(duracion);
	}
	
	public AudioLibro (AudioLibro copiaLibro) throws NullPointerException, IllegalArgumentException {
		super(copiaLibro);
		duracion = copiaLibro.getDuracion();
	}

	
	/*********GETTERS Y SETTERS**********/
	
	public int getDuracion() {
		return duracion;
	}

	private void setDuracion(int duracion) {
		if (duracion <= 0) {
			throw new IllegalArgumentException("ERROR: La duración debe ser mayor que cero.");
		}
		this.duracion = duracion;
	}
	
	public float getPuntos() {
		return ((duracion / MINUTOS_PARA_RECOMPENSA) + 1) * PUNTOS_PREMIO;
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
		return String.format("%s, duración=%s", super.toString(), duracion);
	}

	@Override
	public String getNombreClase() {
		return "AudioLibro";
	}

}
