package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;


public class Alumno implements Serializable {

	
	/*********ATRIBUTOS*********/
	
	private static final String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	
	private String nombre;
	private String correo;
	private Curso curso;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor con parámetros:
	 * @param nombre: Nombre y apellido del alumno.
	 * @param correo: Correo del alumno.
	 * @param curso: Curso al que pertenece el alumno. 
	 */
	public Alumno (String nombre, String correo, Curso curso) throws NullPointerException, IllegalArgumentException {
		setNombre(nombre);
		setCorreo (correo);
		setCurso (curso);
	}
	
	/**
	 * Constructor copia:
	 * @param copiaAlumno: Copia del objeto Alumno. 
	 */
	public Alumno (Alumno copiaAlumno) throws NullPointerException, IllegalArgumentException {
		if(copiaAlumno == null)
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		setNombre(copiaAlumno.getNombre());
		setCorreo(copiaAlumno.getCorreo());
		setCurso(copiaAlumno.getCurso());
	}

	/**
	 * Método que devolverá un alumno ficticio para usarlo en búsquedas y borrados.
	 * @param correo
	 * @return nombre, correo y curso
	 */
	public static Alumno getAlumnoFicticio (String correo) throws NullPointerException, IllegalArgumentException {
		return new Alumno("Marta García", correo, Curso.CUARTO);
	}
	
	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve el nombre del alumno.
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que modifica el nombre del alumno.
	 * @param nombre
	 */
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if(!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	
	/**
	 * Método que formatea el nombre del alumno.
	 * Convierte la primera letra de cada palabra en mayúscula y elimina espacios
	 * en blanco sobrantes.
	 * @param nombre
	 * @return nombreFormateado
	 */
	private String formateaNombre(String nombre) {
		String[] palabras = nombre.trim().split("\\s+");
		StringBuilder nombreFormateado = new StringBuilder();
		for (String palabra : palabras) {
			nombreFormateado.append(palabra.substring(0, 1).toUpperCase())
			.append(palabra.substring(1).toLowerCase())
			.append(" ");
		}
		return nombreFormateado.toString().trim();
	}
	
	
	/**
	 * Método que devuelve el correo del alumno.
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Método que modifica el correo del alumno.
	 * @param correo
	 */
	private void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if(!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	
	/**
	 * Método que devuelve las iniciales del nombre del alumno.
	 * @return iniciales
	 */
	private String getIniciales() {
		String[] palabras = nombre.trim().split("\\s+");
		StringBuilder iniciales = new StringBuilder("");
		for (String palabra : palabras) {
			iniciales.append(palabra.charAt(0));
		}
		return iniciales.toString().toUpperCase();
	}
	
	
	/**
	 * Método que devuelve el curso del alumno.
	 * @return curso
	 */
	public Curso getCurso() {
		return curso;
	}

	/**
	 * Método que modifica el curso del alumno.
	 * @param curso
	 */
	public void setCurso(Curso curso) {
		if(curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		this.curso = curso;
	}

	
	/********OTROS MÉTODOS********/
	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	/**
	 * Método para comparar dos alumnos.
	 * Dos alumnos son iguales si tienen el mismo correo.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Alumno)) {
			return false;
		}
		Alumno other = (Alumno) obj;
		return Objects.equals(correo, other.correo);
	}
	
	/**
	 * Método que muestra la información del alumno:
	 * nombre, iniciales, correo y curso.
	 */
	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", nombre, getIniciales(), correo, curso);
	}

	
}
