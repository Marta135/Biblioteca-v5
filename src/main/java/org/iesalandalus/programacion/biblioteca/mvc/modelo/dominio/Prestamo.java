package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 
 * @author Marta García
 * versión: 3v
 *
 */

public class Prestamo implements Serializable {

	/*********ATRIBUTOS*********/
	
	private static final long serialVersionUID = 1L;
	private static final int MAX_DIAS_PRESTAMO = 20;
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;
	private Alumno alumno;
	private Libro libro;
	
	
	/*******CONSTRUCTORES*******/
	
	public Prestamo (Alumno alumno, Libro libro, LocalDate fechaPrestamo) throws NullPointerException, IllegalArgumentException {
		setAlumno(alumno);
		setLibro(libro);
		setFechaPrestamo(fechaPrestamo);
	}
	
	public Prestamo (Prestamo copiaPrestamo) throws NullPointerException, IllegalArgumentException {
		if(copiaPrestamo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un préstamo nulo.");
		}
		alumno = copiaPrestamo.getAlumno();
		libro = copiaPrestamo.getLibro();
		fechaPrestamo = copiaPrestamo.getFechaPrestamo();
		if (copiaPrestamo.getFechaDevolucion() != null) {
			fechaDevolucion = copiaPrestamo.getFechaDevolucion();
		}
	}
	
	
	/**********MÉTODOS************/
	
	public static Prestamo getPrestamoFicticio (Alumno alumno, Libro libro) throws NullPointerException, IllegalArgumentException {
		return new Prestamo(alumno, libro, LocalDate.now());
	}
	
	public void devolver (LocalDate fechaDevolucion) throws NullPointerException, IllegalArgumentException {
		if (getFechaDevolucion()!=null && getFechaDevolucion().equals(fechaDevolucion)) {
			throw new IllegalArgumentException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);
	}
	
	public int getPuntos() {
		if(getFechaDevolucion() == null) {
			return 0;
		} else {
			long diasIntermedios = ChronoUnit.DAYS.between(getFechaPrestamo(), getFechaDevolucion());
			if (diasIntermedios > MAX_DIAS_PRESTAMO) {
				return 0;
			} else {
				return Math.round(libro.getPuntos()/diasIntermedios);
			}
		}
	}
	
	
	/*********GETTERS Y SETTERS**********/
	
	public Alumno getAlumno() {
		return alumno;
	}
	
	private void setAlumno(Alumno alumno) throws NullPointerException, IllegalArgumentException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		this.alumno = new Alumno(alumno);
	}
	
	public Libro getLibro() {
		Libro libro = null;
		if (this.libro instanceof LibroEscrito) {
			libro = new LibroEscrito((LibroEscrito) this.libro);
		} else if (this.libro instanceof AudioLibro) {
			libro = new AudioLibro((AudioLibro) this.libro);
		}
		return libro;
	}
	
	private void setLibro(Libro libro) throws NullPointerException, IllegalArgumentException {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		if (libro instanceof LibroEscrito) {
			this.libro = new LibroEscrito((LibroEscrito)libro);
		} else if (libro instanceof AudioLibro) {
			this.libro = new AudioLibro((AudioLibro)libro);
		}
	}
	
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	
	private void setFechaPrestamo(LocalDate fechaPrestamo) {
		if(fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha de préstamo no puede ser nula.");
		}
		if (fechaPrestamo.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de préstamo no puede ser futura.");
		}
		this.fechaPrestamo = fechaPrestamo;
	}
	
	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}
	
	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if(fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if(fechaDevolucion.isBefore(getFechaPrestamo()) || fechaDevolucion.isEqual(getFechaPrestamo())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de préstamo.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}


	/********OTROS MÉTODOS********/
	
	@Override
	public int hashCode() {
		return Objects.hash(alumno, libro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Prestamo)) {
			return false;
		}
		Prestamo other = (Prestamo) obj;
		return Objects.equals(alumno, other.alumno) && Objects.equals(libro, other.libro);
	}

	@Override
	public String toString() {
		if(fechaDevolucion == null) {
			return String.format("alumno=(%s), libro=(%s), fecha de préstamo=%s, puntos=%s", 
					alumno, libro, fechaPrestamo.format(Prestamo.FORMATO_FECHA), getPuntos());
		} else {
			return String.format("alumno=(%s), libro=(%s), fecha de préstamo=%s, fecha de devolución=%s, puntos=%s", 
					alumno, libro, fechaPrestamo.format(Prestamo.FORMATO_FECHA), fechaDevolucion.format(Prestamo.FORMATO_FECHA), getPuntos());
		}
	}

}
