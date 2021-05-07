package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author Marta García
 * versión: 3v
 *
 */

public class Alumno implements Serializable {

	
	/*********ATRIBUTOS*********/
	
	private static final long serialVersionUID = 1L;
	private static final String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	private String nombre;
	private String correo;
	private Curso curso;
	
	
	/*******CONSTRUCTORES*******/
	
	public Alumno (String nombre, String correo, Curso curso) throws NullPointerException, IllegalArgumentException {
		setNombre(nombre);
		setCorreo (correo);
		setCurso (curso);
	}
	
	public Alumno (Alumno copiaAlumno) throws NullPointerException, IllegalArgumentException {
		if(copiaAlumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(copiaAlumno.getNombre());
		setCorreo(copiaAlumno.getCorreo());
		setCurso(copiaAlumno.getCurso());
	}

	public static Alumno getAlumnoFicticio (String correo) throws NullPointerException, IllegalArgumentException {
		return new Alumno("Marta García", correo, Curso.CUARTO);
	}
	
	/*********GETTERS Y SETTERS**********/
	
	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if(!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

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
	
	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if(!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	private String getIniciales() {
		String[] palabras = nombre.trim().split("\\s+");
		StringBuilder iniciales = new StringBuilder();
		for (String palabra : palabras) {
			iniciales.append(palabra.charAt(0));
		}
		return iniciales.toString().toUpperCase();
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		if(curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		this.curso = curso;
	}

	
	/*********OTROS MÉTODOS**********/
	
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

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
	
	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", nombre, getIniciales(), correo, curso);
	}

	
}
